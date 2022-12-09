package com.kronos.mebium

import java.io.File

/**
 *
 *  @Author LiABao
 *  @Since 2022/12/9
 *
 */
object ScannerTest {

    @JvmStatic
    fun main(args: Array<String>) {
        val file = File("../app/")
        val projectName = "freedom"
        Next.main(
            arrayOf(
                "dependencies", "-name", projectName, "-file", file.absolutePath
            )
        )
    }
}