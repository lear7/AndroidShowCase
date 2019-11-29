package com.lear7.showcase.activity

import android.content.Context
import android.print.PrintManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.lear7.showcase.R
import com.lear7.showcase.constants.Routers.Act_Printer
import com.lear7.showcase.utils.FileUtils
import com.lear7.showcase.utils.PdfDocumentAdapter
import com.lear7.showcase.utils.PdfUtil
import kotlinx.android.synthetic.main.fragment_printer.*
import java.io.File
import java.io.IOException

@Route(path = Act_Printer)
class PrinterActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_printer
    }

    override fun initView() {
        super.initView()
        btn_print.setOnClickListener {
            var file = FileUtils.getFileFromAsset(this, "guide.pdf")
            usbPrintNew(file?.absolutePath);
        }
    }

    private fun usbPrintNew(pdfPath: String?) {
        val tempPdf = File(getFilesDir(), "temp.pdf")
        if (tempPdf.exists()) {
            tempPdf.delete()
        }
        PdfUtil.write(pdfPath, tempPdf)
        PdfUtil.print(tempPdf.absolutePath)
    }

    private fun printAsset() {
        this?.also { context ->
            // Get a PrintManager instance
            val printManager = context.getSystemService(Context.PRINT_SERVICE) as PrintManager
            // Set job name, which will be displayed in the print queue
            val jobName = "${context.getString(R.string.app_name)} PDF Document"
            // Start a print job, passing in a PrintDocumentAdapter implementation
            // to handle the generation of a print document
            var pdfPath = "file:///android_asset/guide.pdf"
            printManager.print(jobName, PdfDocumentAdapter(context, pdfPath), null)
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