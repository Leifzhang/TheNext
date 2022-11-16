package com.kronos.mebium.file

import java.io.InputStream


/**
 *
 *  @Author LiABao
 *  @Since 2022/10/24
 *
 */
object ResourceUtils {

    fun readInputSteam(name: String): InputStream? {
        Thread.currentThread().contextClassLoader
        val steam = Thread.currentThread().contextClassLoader.getResourceAsStream(name)
        //val text = steam.reader().readText()
        val url = javaClass.classLoader.getResource(name)
        println("classloader url$url")
        return javaClass.classLoader.getResourceAsStream(name)
    }
}