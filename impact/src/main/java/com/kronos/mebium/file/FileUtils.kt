package com.kronos.mebium.file

import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

/**
 *
 *  @Author LiABao
 *  @Since 2022/10/21
 *
 */

fun File.findSettings(): File? {
    val settings = File(this.absolutePath, "settings.gradle")
    val build = File(this.absolutePath, "build.gradle")
    return if (settings.exists() && build.exists()) {
        settings
    } else {
        if (parentFile == null) {
            return null
        } else {
            parentFile.findSettings()
        }
    }
}

fun File.getRootProjectDir(): File? {
    var f: File? = this
    var root: File? = null
    while (f != null) {
        if (File(f, "settings.gradle").exists()) {
            root = f
        }
        f = f.parentFile
    }
    return root
}

fun InputStream.moveTo(file: File) {
    file.parentFile.run {
        if (!exists()) {
            mkdirs()
        }
    }
    if (file.exists()) {
        file.delete()
    }
    val outputStream = FileOutputStream(file)
    val bytesIn = ByteArray(UnzipUtils.BUFFER_SIZE)
    var read: Int
    outputStream.use { it ->
        while (read(bytesIn).also { read = it } != -1) {
            it.write(bytesIn, 0, read)
        }
    }
}


fun File.getCanonicalPath(file: File): String {
    val parent = parent
    var compare = file.path
    val name = file.name
    val prefix = prefix(parent, compare)
    compare = compare.removePrefix(prefix + File.separator)
    val compareArray = compare.split(File.separator)
    val canonicalPath = StringBuilder()
    compareArray.forEach {
        if (it != name) {
            canonicalPath.append("..").append(File.separator)
        } else {
            canonicalPath.append(it)
        }
    }
    return canonicalPath.toString()
}

fun File.canonicalPathOnly(file: File): String {
    val parent = parent
    var compare = file.path
    val name = file.name
    val prefix = prefix(parent, compare)
    compare = compare.removePrefix(prefix + File.separator)
    val compareArray = compare.split(File.separator)
    val canonicalPath = StringBuilder()
    compareArray.forEach {
        if (it != name) {
            canonicalPath.append("..").append(File.separator)
        }
    }
    return canonicalPath.toString()
}

fun prefix(string1: String, string2: String): String {
    val length = if (string1.length > string2.length) string1.length else string2.length
    val text = if (string1.length > string2.length) string1 to string2 else string2 to string1
    for (index in length downTo 0) {
        val longText = text.first
        val shortText = text.second
        val prefix = longText.substring(0, index)
        if (shortText.startsWith(prefix)) {
            return prefix
        }
    }
    return ""
}
