package com.lear7.showcase.utils;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import static android.os.Environment.DIRECTORY_PICTURES;

public class Utils {

    public static void saveBitmap(Context context, Bitmap bm) throws IOException {
        //判断文件夹是否存在，不存在则创建
        File filePath = context.getExternalFilesDir(DIRECTORY_PICTURES);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }

        //随机生成不同的名字
        String fileName = UUID.randomUUID().toString() + ".jpg";
        File file = new File(filePath + "/" + fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
    }
}
