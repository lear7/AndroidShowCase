package com.lear7.showcase.utils

import android.content.Context
import android.util.Log
import com.lear7.showcase.App.TAG
import java.io.*

class FileUtils() {

    companion object {
        // 从Asset目录拷贝文件到内部存储File
        fun getFileFromAsset(context: Context, filename: String): File? {
            Log.e(TAG, "Begin to save")
            val assetManager = context.assets
            var inputStream: InputStream? = null
            var out: OutputStream? = null
            var outFile: File? = File(context.getFilesDir(), filename)
            try {
                inputStream = assetManager.open(filename)
                out = FileOutputStream(outFile)
                copyFile(inputStream, out)
            } catch (e: IOException) {
                Log.e(TAG, "Failed to copy asset file: $filename", e)
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close()
                    } catch (e: IOException) { // NOOP
                    }
                }
                if (out != null) {
                    try {
                        out.close()
                    } catch (e: IOException) { // NOOP
                    }
                }
            }
            Log.e(TAG, "Save succeed!")
            return outFile
        }

        private fun copyFile(inputStream: InputStream, out: OutputStream) {
            val buffer = ByteArray(1024)
            var read = 0
            while (inputStream.read(buffer).also({ read = it }) != -1) {
                out.write(buffer, 0, read)
            }
        }
    }

}