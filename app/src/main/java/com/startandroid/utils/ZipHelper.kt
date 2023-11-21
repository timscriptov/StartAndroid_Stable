package com.startandroid.utils

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

object ZipHelper {
    @JvmStatic
    fun unpack(inputFile: File, outPutDir: File) {
        try {
            FileInputStream(inputFile).use { inputStream ->
                ZipInputStream(inputStream).use { zipInputStream ->
                    var zipEntry: ZipEntry
                    while (zipInputStream.getNextEntry().also { zipEntry = it } != null) {
                        val entryName = zipEntry.name
                        val entryFile = File(outPutDir, entryName)
                        if (zipEntry.isDirectory) {
                            entryFile.mkdirs()
                        } else {
                            FileOutputStream(entryFile).use { fileOutputStream ->
                                val buffer = ByteArray(1024)
                                var length: Int
                                while (zipInputStream.read(buffer).also { length = it } > 0) {
                                    fileOutputStream.write(buffer, 0, length)
                                }
                            }
                            zipInputStream.closeEntry()
                        }
                    }
                }
            }
            inputFile.delete()
        } catch (_: Exception) {
        }
    }
}
