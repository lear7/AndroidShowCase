package com.lear7.showcase.activity

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.print.PrintManager
import androidx.print.PrintHelper
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.lear7.showcase.R
import com.lear7.showcase.constants.Routers.Act_Printer
import com.lear7.showcase.utils.FileUtils
import com.lear7.showcase.utils.PdfDocumentAdapter
import com.lear7.showcase.utils.PdfUtil
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
        pdfFile = FileUtils.getFileFromAsset(this, "guide.pdf")
        btn_print0.setOnClickListener {
            var pdfFile = FileUtils.getFileFromAsset(this, "guide.pdf")
            val imageFile = PdfUtil.getBitmapFile(this, pdfFile, true)
            Glide.with(this).load(imageFile).into(image_pdf)
            printImage(BitmapFactory.decodeFile(imageFile.absolutePath))
        }

        btn_print1.setOnClickListener {
            PdfUtil.print(this, pdfFile!!.absolutePath, 1)
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
            printManager.print(jobName, PdfDocumentAdapter(context, filePath), null)
        }
    }

    private fun printImage(bitmap: Bitmap) {
        this?.also { context ->
            PrintHelper(context).apply {
                scaleMode = PrintHelper.SCALE_MODE_FIT
            }.also { printHelper ->
                printHelper.printBitmap("droids.jpg - test print", bitmap)
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