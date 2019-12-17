package com.lear7.showcase.activity

import android.content.Context
import android.graphics.Bitmap
import android.print.PrintAttributes
import android.print.PrintManager
import androidx.print.PrintHelper
import com.alibaba.android.arouter.facade.annotation.Route
import com.lear7.showcase.R
import com.lear7.showcase.constants.Routers.Act_Printer
import com.lear7.showcase.utils.FileUtils
import com.lear7.showcase.utils.PdfDocumentAdapter
import com.lear7.showcase.utils.PrintUtils
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_printer.*
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException


@Route(path = Act_Printer)
class PrinterActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_printer
    }

    var pdfFile: File? = null

    override fun initView() {
        super.initView()
        pdfFile = FileUtils.getFileFromAsset(this, "invoice.pdf")
        btn_print0.setOnClickListener {
            val imageFile = PrintUtils.getBitmapFile(this, pdfFile, true)
//            printImage(BitmapFactory.decodeFile(imageFile.absolutePath))
//            printPdf(pdfFile!!.absolutePath)
            PrintUtils.newHpPrint(this)
        }

        btn_print1.setOnClickListener {
            PrintUtils.print(this, pdfFile!!.absolutePath, 1)
        }
    }

    private fun printFileContent(filePath: String) {
        var reader: BufferedReader? = null
        try {
            Logger.i("以行为单位读取文件内容，一次读一行")
            reader = BufferedReader(FileReader(filePath))
            var tempString: String? = null
            var line = 1
            //一次读一行，读入null时文件结束
            while (reader.readLine().also { tempString = it } != null) { //把当前行号显示出来
                Logger.i("line $line: $tempString")
                line++
            }
            reader.close()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (reader != null) {
                try {
                    reader.close()
                } catch (e1: IOException) {
                    e1.printStackTrace()
                }
            }
        }
    }

    private fun printPdf(filePath: String) {
        this?.also { context ->
            val printManager = context.getSystemService(Context.PRINT_SERVICE) as PrintManager
            val jobName = "${context.getString(R.string.app_name)} PDF Document"

//            var printFile = File(context.filesDir, "temp.pdf")
//            if (printFile.exists()) {
//                printFile.delete()
//                printFile = File(context.filesDir, "temp.pdf")
//            }
//            PrintUtils.write(filePath, printFile)

            var attributes = PrintAttributes.Builder()
                    .setMediaSize(PrintAttributes.MediaSize.UNKNOWN_LANDSCAPE)
                    .setResolution(PrintAttributes.Resolution("1", "print", 2150, 1390))
                    .setMinMargins(PrintAttributes.Margins(0, 0, 0, 0))
                    .setColorMode(PrintAttributes.COLOR_MODE_MONOCHROME)
                    //.setDuplexMode(PrintAttributes.DUPLEX_MODE_NONE)  // API小于23会报错，可以不传
                    .build();

            var pdfFile = FileUtils.getFileFromAsset(this, "invoice.pdf")
            printManager.print(jobName, PdfDocumentAdapter(context, pdfFile!!.absolutePath), attributes)
        }
    }

    private fun printImage(bitmap: Bitmap) {
        this?.also { context ->
            PrintHelper(context).apply {
                scaleMode = PrintHelper.SCALE_MODE_FIT
            }.also { printHelper ->
                printHelper.printBitmap("Print Invoice", bitmap)
            }
        }
    }

    private fun hpPrint(filePath: String?): Int {
        var r = 0
        try {
            val process = Runtime.getRuntime().exec(arrayOf("su", "-c", "cat $filePath > /dev/usblp0"))
            r = process.waitFor()
            println("r:$r")
        } catch (e: IOException) {
            e.printStackTrace()
            r = -1
        } catch (e: InterruptedException) {
            e.printStackTrace()
            r = -1
        }
        return r
    }

}