package com.lear7.showcase.utils;

import android.content.Context;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class FileUtilsJ {

    public static File getFileFromAsset(Context context, String fileName) {
        return writeFile(context, fileName, readStream(context, fileName));
    }

    public static byte[] readStream(Context context, String fileName) {
        try {
            InputStream inStream = context.getResources().getAssets().open(fileName);
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            outStream.close();
            inStream.close();
            return outStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static File writeFile(Context context,
                                 String fileName, byte[] buffer) {
        File file = new File(context.getFilesDir(), fileName);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file, true);
            out.write(buffer);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return file;
    }
}