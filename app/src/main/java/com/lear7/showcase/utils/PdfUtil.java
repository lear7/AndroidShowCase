package com.lear7.showcase.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.pdf.PdfDocument;
import android.graphics.pdf.PdfRenderer;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.solver.widgets.Rectangle;

import com.github.barteksc.pdfviewer.PDFView;
import com.orhanobut.logger.Logger;
import com.shockwave.pdfium.PdfiumCore;

import org.w3c.dom.Document;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by wyl on 2018/12/29.
 */
public class PdfUtil {
    public static final String FONT = "assets/NotoSansCJKsc-Regular.otf";
    static String tail = "\u001B%-12345X@PJL EOJ\n" +
            "\u001B%-12345X";
    static String header1 = "\u001B%-12345X@PJL SET STRINGCODESET=UTF8\n";
    static String header2 = "@PJL JOB NAME=";
    static String header3 = "@PJL COMMENT \"87660z (0.3.1584.23029); Windows 10 Enterprise 10.0.15063.1; Unidrv 0.3.15063.138\"\n" +
            "@PJL COMMENT \"Username: QIJUN; App Filename: %1$s; 12-28-2018\"\n" +
            "@PJL COMMENT \"NUP = NUP_1\"\n" +
            "@PJL SET JOBATTR=\"OS=Android\"\n" +
            "@PJL SET JOBATTR=\"OS Version=%2$s\"\n" +
            "@PJL SET JOBATTR=\"Render Type=Discrete V3\"\n" +
            "@PJL SET JOBATTR=\"Render Name=HP Color LaserJet Pro M452 PCL 6\"\n" +
            "@PJL SET JOBATTR=\"Render Version=13.26\"\n" +
            "@PJL SET JOBATTR=\"JobAcct1=QIJUN\"\n" +
            "@PJL SET JOBATTR=\"JobAcct2=QIJUN16\"\n" +
            "@PJL SET JOBATTR=\"JobAcct3=AUTH\"\n" +
            "@PJL SET JOBATTR=\"JobAcct4=20181228141929\"\n" +
            "@PJL SET JOBATTR=\"JobAcct5=09c91034-c700-4fc8-a290-39aef130b6a2\"\n" +
            "@PJL SET JOBATTR=\"JobAcct6=Adobe Acrobat Reader DC \"\n" +
            "@PJL SET JOBATTR=\"JobAcct7=AcroRd32.exe\"\n" +
            "@PJL SET JOBATTR=\"JobAcct8=QIJUN\"\n" +
            "@PJL SET JOBATTR=\"JobAcct9=\"\n" +
            "@PJL DMINFO ASCIIHEX=\"0400040101020D101001153230313831323238303631393239\"\n" +
            "@PJL SET GRAYSCALE=ON\n" +
            "@PJL SET PLANESINUSE=3\n" +
            "@PJL SET COPIES=1\n" +
            "@PJL SET QTY=1\n" +
            "@PJL SET PAPER=A5\n" +
            "@PJL SET REFILLTHRESHOLD=80\n" +
            "@PJL SET REFILLDURATION=QUICK\n" +
            "@PJL SET REFILLTYPE=SHARP\n" +
            "@PJL SET PRINTAREA=FULLSIZE\n" +
            "@PJL SET HOLD=OFF\n" +
            "@PJL SET USERNAME=\"QIJUN\"\n" +
            "@PJL SET SEPARATORPAGE=OFF\n" +
            "@PJL SET FOLD=\"\"\n" +
            "@PJL SET PUNCH=OFF\n" +
            "@PJL SET OUTBIN=AUTO\n" +
            "@PJL SET EDGETOEDGE=NO\n" +
            "@PJL SET PROCESSINGACTION=APPEND\n" +
            "@PJL SET PROCESSINGTYPE=\"STAPLING\"\n" +
            "@PJL SET PROCESSINGOPTION=\"NONE\"\n" +
            "@PJL SET PROCESSINGBOUNDARY=MOPY\n" +
            "@PJL SET PRINTQUALITY=NORMAL\n" +
            "@PJL ENTER LANGUAGE=PDF\n";

    public static void write(String path, File pdf) {
        try {
            FileInputStream fis = new FileInputStream(path);
            FileOutputStream fos = new FileOutputStream(pdf);
            fos.write(header1.getBytes());
            fos.write((header2 + "\"" + pdf.getName() + "\"" + "\r\n").getBytes());
            fos.write(String.format(header3, pdf.getName(), Build.MODEL).getBytes());

            int len = 0;
            byte[] b = new byte[1024];
            while ((len = fis.read(b)) > 0) {
                fos.write(b, 0, len);
            }

            //文件尾信息
            String tail = "\u001B%-12345X@PJL EOJ\n" + "\u001B%-12345X";
            fos.write(tail.getBytes());//添加文件尾
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
            PdfUtil.write(sourcePath, tempPdf);

            try {
                String execStr = "cp " + tempPdf + " /dev/usblp0";
                new ExeCommand(true).run(execStr, 10000);
            } catch (Exception e) {
                Logger.e(e.getMessage());
                e.printStackTrace();
            }

        } else if (mode == 3 || mode == 4) {
            File imageFile = PdfUtil.getBitmapFile(context, new File(sourcePath), false);
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
