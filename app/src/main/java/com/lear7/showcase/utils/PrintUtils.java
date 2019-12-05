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

import com.orhanobut.logger.Logger;
import com.shockwave.pdfium.PdfiumCore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by wyl on 2018/12/29.
 */
public class PrintUtils {
    static String UEL = "\u001B%-12345X";
    static String JOBStart = UEL + "@PJL \r\n";
    static String JOBEnd = "@PJL RESET\r\n" + "@PJL EOJ\r\n";

    static String PJLCommand1 = "@PJL COMMENT PJL Start\r\n" +
            "@PJL JOB NAME=";

    static String PJLCommand2 = "@PJL SET COPIES=1\r\n" +
            "@PJL SET PAPER=A5\r\n" +
            UEL;

    static String PCLCommand = "@PJL COMMENT PCL Start\r\n" +
            "@PJL ENTER LANGUAGE = PCL\r\n" +
            "@PJL SET LPARM : PCL SYMSET = DESKTOP\r\n" +
            "\u001BE . . . . PCL job . . . .\u001BE" +
            UEL;

    static String PDFCommand = "@PJL ENTER LANGUAGE=PDF\r\n";

    public static void write(String path, File pdf) {
        Logger.i(JOBStart + PJLCommand1 + "\"PDF Printing Job\"\r\n" + PJLCommand2 + PCLCommand + PDFCommand + JOBEnd + UEL);

        try {
            FileInputStream fis = new FileInputStream(path);
            FileOutputStream fos = new FileOutputStream(pdf);
            fos.write(JOBStart.getBytes());
            fos.write((PJLCommand1 + "\"" + pdf.getName() + "\"" + "\r\n").getBytes());
            fos.write((PJLCommand2).getBytes());
            fos.write((PCLCommand).getBytes());

            // writing pdf content
            fos.write((PDFCommand).getBytes());
            int len = 0;
            byte[] b = new byte[1024];
            while ((len = fis.read(b)) > 0) {
                fos.write(b, 0, len);
            }
            fos.write(UEL.getBytes());
            // writing pdf end

            fos.write(JOBEnd.getBytes());
            fos.flush();
            fos.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void print(Context context, String sourcePath, int mode) {
        if (mode == 1 || mode == 2) {
            File tempPdf = new File(context.getFilesDir(), "temp.pdf");
            if (tempPdf.exists()) {
                tempPdf.delete();
            }
            PrintUtils.write(sourcePath, tempPdf);

            try {
                String execStr = "cp " + tempPdf + " /dev/usblp0";
                new ExeCommand(true).run(execStr, 10000);
            } catch (Exception e) {
                Logger.e(e.getMessage());
                e.printStackTrace();
            }

        } else if (mode == 3 || mode == 4) {
            File imageFile = PrintUtils.getBitmapFile(context, new File(sourcePath), false);
            try {
                String execStr = "cp " + imageFile + " /dev/usblp0";
                new ExeCommand(true).run(execStr, 10000);
            } catch (Exception e) {
                Logger.e(e.getMessage());
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
}
