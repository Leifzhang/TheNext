package com.kronos.mebium.factory

import com.beust.jcommander.JCommander
import com.kronos.mebium.action.Handler
import com.kronos.mebium.android.AndroidApplicationHandler
import com.kronos.mebium.android.AndroidModuleHandler
import com.kronos.mebium.android.AndroidProjectHandler
import com.kronos.mebium.entity.CommandEntity

/**
 *
 *  @Author LiABao
 *  @Since 2022/10/24
 *
 */
object HandlerFactory {

    fun chooseHandler(name: String): Handler? {
        when (name) {
            "module" -> {
                return AndroidModuleHandler()
            }
            "project" -> {
                return AndroidProjectHandler()
            }
            "application" -> {
                return AndroidApplicationHandler()
            }
        }
        if (name == "--help") {
            //  val input = Scanner(System.`in`)
            println(HElP_INFO)
            println("以下为公共参数部分")
            val commandEntity = CommandEntity()
            JCommander.newBuilder().addObject(commandEntity).build().usage()
            return null
        }
        println("name : $name  当前指令不存在")
        return null
    }

    private const val HElP_INFO = "1.Mebium module\r\n" +
            "创建安卓 module \r\n\r\n" +
            "2.Mebium project\r\n" + "创建安卓 Project \r\n\r\n"
}