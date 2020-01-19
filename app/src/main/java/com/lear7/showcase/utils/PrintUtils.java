package com.lear7.showcase.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.pdf.PdfRenderer;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.print.PrintAttributes;

import androidx.appcompat.app.AppCompatActivity;

import com.hp.mss.hpprint.model.PDFPrintItem;
import com.hp.mss.hpprint.model.PrintItem;
import com.hp.mss.hpprint.model.PrintJobData;
import com.hp.mss.hpprint.model.asset.PDFAsset;
import com.hp.mss.hpprint.util.PrintUtil;
import com.shockwave.pdfium.PdfiumCore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import timber.log.Timber;

/**
 * Created by wyl on 2018/12/29.
 */
public class PrintUtils {
    static String UEL = "\u001B%-12345X";
    static String JOBStart = UEL + "@PJL \r\n";
    static String JOBEnd = "@PJL RESET\r\n" + "@PJL EOJ\r\n";

    static String PJLCommand1 = "@PJL JOB NAME=";

    static String PJLCommand2 = "@PJL SET COPIES=1\r\n" +
            "@PJL SET HOLD=OFF\r\n" +
            "@PJL SET ORIENTATION=PORTRAIT\r\n" +
            "@PJL SET IMAGEADAPT=ON\r\n" +
            "@PJL SET MEDIASOURCE=TRAY1\r\n" +
            "@PJL SET LANG=CHINESE\r\n" +
            "@PJL SET PRINTAREA=FULLSIZE\r\n" +
            "@PJL SET PRINTAREA=INKEDAREA\r\n" +
            "@PJL SET PAGEPROTECT=OFF\r\n" +
            "@PJL SET PAPER=A5\r\n" +
            "@PJL SET PAPERLENGTH=4400\r\n" +
            "@PJL SET PAPERWIDTH=3200\r\n" +
            UEL;

    static String PCLCommand = "@PJL COMMENT PCL Start\r\n" +
            "@PJL ENTER LANGUAGE = PCL\r\n" +
            "\u001BE\u001B%0BIN;SP1;PA1010,1010;PW2.2;PD5310,1010,5310,5310,1010,5310,1010,1010;PU;PA2280,3040;SD1,277,2,1,4,20,5,0,6,0,7,4148;DT*;SS;LBPCL Print Job*;\u001B%0A<FF>\u001BE\u001B%-12345X@PJL \r\n";

    static String INFOCommand = "@PJL INFO VARIABLES\r\n";
    static String PDFCommand = "@PJL ENTER LANGUAGE=PDF\r\n";

    public static void write(String path, File pdf) {
        try {
            FileInputStream fis = new FileInputStream(path);
            FileOutputStream fos = new FileOutputStream(pdf);
            fos.write(JOBStart.getBytes());
            fos.write((PJLCommand1 + "\"" + pdf.getName() + "\"" + "\r\n").getBytes());
            fos.write((PJLCommand2).getBytes());
//            fos.write((PCLCommand).getBytes());

            // writing pdf content
            fos.write((PDFCommand).getBytes());
            int len = 0;
            byte[] b = new byte[1024];
            while ((len = fis.read(b)) > 0) {
                fos.write(b, 0, len);
            }
            fos.write((INFOCommand).getBytes());
            fos.write(UEL.getBytes());
            // writing pdf end
            fos.write(JOBEnd.getBytes());
            fos.flush();
            fos.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Timber.e(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            Timber.e(e.getMessage());
        }

    }

    public static void print(Context context, String sourcePath, int mode) {
        if (mode == 1 || mode == 2) {
            File printFile = new File(context.getFilesDir(), "temp.pdf");
            if (printFile.exists()) {
                printFile.delete();
                printFile = new File(context.getFilesDir(), "temp.pdf");
            }
            PrintUtils.write(sourcePath, printFile);

            try {
                String execStr = "cp " + printFile + " /dev/usblp0";
                new ExeCommand(true).run(execStr, 10000);
            } catch (Exception e) {
                Timber.e(e.getMessage());
                e.printStackTrace();
            }

        } else if (mode == 3 || mode == 4) {
            File imageFile = PrintUtils.getBitmapFile(context, new File(sourcePath), true);
//            File printFile = new File(context.getFilesDir(), "printfile.png");
//            if (printFile.exists()) {
//                printFile.delete();
//                printFile = new File(context.getFilesDir(), "printpng.png");
//            }
//            PrintUtils.write(imageFile.getAbsolutePath(), printFile);

            try {
                String execStr = "cp " + imageFile + " /dev/usblp0";
                new ExeCommand(true).run(execStr, 10000);
            } catch (Exception e) {
                Timber.e(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<Bitmap> pdfToBitmap(File pdfFile) {
        ArrayList<Bitmap> bitmaps = new ArrayList<>();
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ParcelFileDescriptor pfd = ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY);
                PdfRenderer renderer = new PdfRenderer(pfd);
                Bitmap bitmap;
                final int pageCount = renderer.getPageCount();
                for (int i = 0; i < pageCount; i++) {
                    PdfRenderer.Page page = renderer.openPage(i);
                    int width = 210;
                    int height = 140;
                    bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                    //todo 以下三行处理图片存储到本地出现黑屏的问题，这个涉及到背景问题
                    Canvas canvas = new Canvas(bitmap);
                    canvas.drawColor(Color.WHITE);
                    canvas.drawBitmap(bitmap, 0, 0, null);
                    Rect r = new Rect(0, 0, width, height);
                    page.render(bitmap, r, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
                    bitmaps.add(bitmap);
                    // close the page
                    page.close();
                }
                // close the renderer
                renderer.close();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return bitmaps;
    }

    public static File getBitmapFile(Context context, File file, boolean isRotate) {
        File outputFile = null;
        try {
            ParcelFileDescriptor fd = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);
            PdfiumCore pdfiumCore = new PdfiumCore(context);
            com.shockwave.pdfium.PdfDocument pdfDocument = pdfiumCore.newDocument(fd);

            pdfiumCore.openPage(pdfDocument, 0);
            int width = pdfiumCore.getPageWidthPoint(pdfDocument, 0) * 3;
            int height = pdfiumCore.getPageHeightPoint(pdfDocument, 0) * 3;

            // ARGB_8888 - best quality, high memory usage, higher possibility of OutOfMemoryError
            // RGB_565 - little worse quality, twice less memory usage
            Bitmap bitmap = Bitmap.createBitmap(width, height,
                    Bitmap.Config.ARGB_8888);
            pdfiumCore.renderPageBitmap(pdfDocument, bitmap, 0, 0, 0,
                    width, height);
            pdfiumCore.closeDocument(pdfDocument);

            outputFile = new File(context.getFilesDir(), "temp.png");
            if (outputFile.exists()) {
                outputFile.delete();
            }
            FileOutputStream outputStream = new FileOutputStream(outputFile);
            if (isRotate) {
                Bitmap newBitmap = rotateBitmap(bitmap, 90);
                newBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            } else {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            }
            outputStream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return outputFile;
    }

    public static Bitmap rotateBitmap(Bitmap origin, float alpha) {
        if (origin == null) {
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();
        Matrix matrix = new Matrix();
        matrix.setRotate(alpha);
        // 围绕原地进行旋转
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (newBM.equals(origin)) {
            return newBM;
        }
        origin.recycle();
        return newBM;
    }

    public static void newHpPrint(AppCompatActivity activity) {
        PDFAsset invoice = new PDFAsset("invoice.pdf", true);
        PrintItem.ScaleType scaleType = PrintItem.ScaleType.FIT;
        PrintAttributes.Margins margins = new PrintAttributes.Margins(0, 0, 0, 0);
        PrintItem printItem = new PDFPrintItem(PrintAttributes.MediaSize.UNKNOWN_LANDSCAPE, margins, scaleType, invoice);
        PrintJobData printJobData = new PrintJobData(activity, printItem);
        PrintUtil.setPrintJobData(printJobData);
        PrintUtil.print(activity);
    }
}
