package com.lear7.showcase.activity

import android.content.Context
import android.print.PrintManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.lear7.showcase.R
import com.lear7.showcase.constants.Routers.Act_Printer
import com.lear7.showcase.fragment.BaseFragment
import com.lear7.showcase.utils.PdfDocumentAdapter
import kotlinx.android.synthetic.main.fragment_printer.*

@Route(path = Act_Printer)
class PrinterFragment : BaseFragment() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_printer
    }

    override fun initView() {
        super.initView()
        btn_print.setOnClickListener { doPrint() }
    }

    private fun doPrint() {
        activity?.also { context ->
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

}