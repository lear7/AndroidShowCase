package com.lear7.showcase.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.os.Looper
import android.os.Process
import android.util.Log
import android.widget.Toast
import com.lear7.showcase.App
import com.lear7.showcase.utils.shell.Shell
import java.io.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


/**
 * UncaughtException处理类,当程序发生Uncaught异常的时候,有该类来接管程序,并记录发送错误报告.
 *
 * @author user
 */
class CrashHandlerKT private constructor(private val mContext: Context) :
        Thread.UncaughtExceptionHandler {
    private val mDefaultHandler: Thread.UncaughtExceptionHandler?
    private val infos: MutableMap<String, String> = HashMap()

    inner class CustomException(message: String?) : Exception(message)

    init {
        //获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        //设置该CrashHandlerKT为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this)
        // 清理30天前的旧log，且每隔10天执行一次，以防APP永远不关闭
        addTimerTask(10 * 24 * 60 * 60 * 1000, { cleanHistoryLog(30) })
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    override fun uncaughtException(thread: Thread, ex: Throwable) {
        if (!handleException(ex) && mDefaultHandler != null) { // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex)
            ex.printStackTrace()
        } else {
            try {
                Thread.sleep(1000)
            } catch (e: InterruptedException) {
                Log.e(TAG, "error : ", e)
            }
            //退出程序
            System.exit(1)
            Process.killProcess(Process.myPid())
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private fun handleException(ex: Throwable?): Boolean {
        if (ex == null) {
            return false
        }
        //使用Toast来显示异常信息
        object : Thread() {
            override fun run() {
                Looper.prepare()
                Toast.makeText(mContext, "很抱歉，程序出现异常，即将退出", Toast.LENGTH_LONG).show()
                Looper.loop()
            }
        }.start()
        //收集设备参数信息
        collectDeviceInfo(mContext)
        //保存日志文件
        saveCrashInfo2File(ex)
        return true
    }

    /**
     * 收集设备参数信息
     *
     * @param ctx
     */
    fun collectDeviceInfo(ctx: Context) {
        try {
            val pm = ctx.packageManager
            val pi =
                    pm.getPackageInfo(ctx.packageName, PackageManager.GET_ACTIVITIES)
            if (pi != null) {
                val versionName = if (pi.versionName == null) "null" else pi.versionName
                val versionCode = pi.versionCode.toString() + ""
                infos["versionName"] = versionName
                infos["versionCode"] = versionCode
            }
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e(TAG, "an error occured when collect package info", e)
        }
        val fields = Build::class.java.declaredFields
        for (field in fields) {
            try {
                field.isAccessible = true
                infos[field.name] = field[null].toString()
                Log.d(TAG, field.name + " : " + field[null])
            } catch (e: Exception) {
                Log.e(TAG, "an error occured when collect crash info", e)
            }
        }
    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    private fun saveCrashInfo2File(ex: Throwable): String {
        val sb = StringBuffer()
        for ((key, value) in infos) {
            sb.append("$key=$value\n")
        }
        val writer: Writer = StringWriter()
        val printWriter = PrintWriter(writer)
        ex.printStackTrace(printWriter)
        var cause = ex.cause
        while (cause != null) {
            cause.printStackTrace(printWriter)
            cause = cause.cause
        }
        printWriter.close()
        val result = writer.toString()
        sb.append(result)
        Log.e(TAG, result)
        // 保存文件
        return saveFileOld(result)
    }

    companion object {
        val TAG = "AdPlayer"
        private var instance: CrashHandlerKT? = null
        private val FORMATTER: DateFormat = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")

        /**
         * 获取CrashHandlerKT实例 ,单例模式
         */
        @Synchronized
        fun getInstance(context: Context): CrashHandlerKT? {
            if (instance == null) {
                synchronized(CrashHandlerKT::class.java) {
                    instance = CrashHandlerKT(context)
                }
            }
            return instance
        }

        // 重启APP
        fun restartApp(context: Context, clazz: Class<*>?, time: Long) {
            addTimerTask(time, { restartApplication(context, clazz) })
        }

        private fun restartApplication(context: Context, clazz: Class<*>?) {
            val mStartActivity = Intent(context.applicationContext, clazz)
            val mPendingIntentId = 123456
            val mPendingIntent = PendingIntent.getActivity(
                    context.applicationContext,
                    mPendingIntentId,
                    mStartActivity,
                    PendingIntent.FLAG_CANCEL_CURRENT
            )
            val amr = context.applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            amr[AlarmManager.RTC, System.currentTimeMillis() + 500] = mPendingIntent
            System.exit(0)
        }

        // 定期执行任务
        fun addTimerTask(time: Long, process: () -> Unit) {
            val mScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
            mScheduledExecutorService.scheduleAtFixedRate({
                process()
            }, 1, time, TimeUnit.MILLISECONDS)
        }


        /**
         * 手动保存一条Log到文件
         *
         * @param context
         * @param content
         */
        fun logToFile(content: String) {
            saveFileOld(content + "\n")
        }

        private fun saveFileOld(content: String): String {
            val timeStamp = System.currentTimeMillis()
            val time = FORMATTER.format(Date())
            val fileName = "crash-$time-$timeStamp.log"
            if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
                try { // 保存到SD卡根目录或内部存储的qj_crash目录， /storage/emulated/0/qj_crash
                    val dir =
                            File(Environment.getExternalStorageDirectory().absolutePath + File.separator + "qj_crash")
                    Log.i("CrashHandlerKT", dir.toString())
                    if (!dir.exists()) {
                        dir.mkdir()
                    }
                    val fos = FileOutputStream(File(dir, fileName))
                    fos.write(content.toByteArray())
                    fos.close()
                    return fileName
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            return fileName
        }
    }

    // 删除一个月前的log

    fun cleanHistoryLog(day: Int) {
        Thread(Runnable {

            val cmd =
                    "find " + Environment.getExternalStorageDirectory().absolutePath + File.separator + "qj_crash" + " -mtime +" + day.toString() + " -type f -name \"*.log\" | xargs rm -rf"
            Log.d(TAG, cmd)

            val result2 = Shell.SH.run(cmd)
            if (result2.isSuccessful) {
                Log.d(TAG, day.toString() + "天前的旧日志清理成功")
            }

        }).start()
    }

}