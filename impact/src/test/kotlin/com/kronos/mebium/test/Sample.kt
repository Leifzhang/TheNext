package com.kronos.mebium.test

import com.kronos.mebium.Next
import org.junit.Test
import java.io.File

/**
 *
 *  @Author LiABao
 *  @Since 2022/10/24
 *
 */
class Sample {

    @Test
    fun help() {
        Next.main(
            arrayOf(
                "--help"
            )
        )
    }

    @Test
    fun testAndroidModule() {
        val file = File("")
        val moduleName = "strike-freedom"
        val groupName = "com.kronos.common"
        Next.main(
            arrayOf(
                "module",
                "-file",
                file.absolutePath,
                "-name",
                moduleName,
                "-group",
                groupName
            )
        )
    }

    @Test
    fun testAndroidApplication() {
        val file = File("../app/")
        val projectName = "freedom"
        Next.main(
            arrayOf(
                "project", "-name", projectName, "-file", file.absolutePath
            )
        )
    }


    @Test
    fun testGradleDepend() {
        val file = File("../app/")
        val projectName = "freedom"
        Next.main(
            arrayOf(
                "dependencies", "-name", projectName, "-file", file.absolutePath
            )
        )
    }

}