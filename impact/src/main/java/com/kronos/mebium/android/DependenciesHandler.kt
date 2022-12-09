package com.kronos.mebium.android

import com.beust.jcommander.JCommander
import com.kronos.mebium.action.Handler
import com.kronos.mebium.entity.CommandEntity
import com.kronos.mebium.file.getRootProjectDir
import java.io.File
import java.util.Scanner

/**
 *
 *  @Author LiABao
 *  @Since 2022/12/8
 *
 */
class DependenciesHandler : Handler {


    val scanner = Scanner(System.`in`)

    override fun handle(args: Array<String>) {
        val commandEntity = CommandEntity()
        JCommander.newBuilder().addObject(commandEntity).build().parse(*args)
        val first = commandEntity.file
        val name = commandEntity.name
        val pwd = first
        val root = pwd.getRootProjectDir() ?: return
        val files = root.walkTopDown().filter {
            it.isFile && it.name.contains(".gradle")
        }
        val overrideList = mutableListOf<Pair<File, File>>()
        files.forEach {
            onGradleCheck(it)?.apply {
                overrideList.add(it to this)
            }
        }
        confirm(overrideList)
    }

    private fun confirm(overrideList: MutableList<Pair<File, File>>) {
        println("if you want overwrite all this file ? input y to confirm \r\n")
        val input = scanner.next()
        if (input == "y") {
            overrideList.forEach {
                it.first.delete()
                it.second.renameTo(it.first)
            }
            print("replace success \r\n ")
        } else {
            print("skip\r\n ")
        }
    }

    private val pattern =
        "(\\D\\S*)(implementation|Implementation|compileOnly|CompileOnly|test|Test|api|Api)([ (])(\\D\\S*)".toPattern()

    private fun onGradleCheck(file: File): File? {
        var override = false
        val lines = file.readLines()
        val newLines = mutableListOf<String>()
        lines.forEach { line ->
            val matcher = pattern.matcher(line)
            if (matcher.find()) {
                val libs = matcher.group(4)
                if (!libs.contains(":")) {
                    val newLibs = libs.replace("\'", "").replace("\"", "").replace("-", ".")
                    print("fileName: ${file.name} dependencies :  $libs do you want replace to $newLibs  ? input  y to replace  \r\n ")
                    while (true) {
                        val input = scanner.next()
                        if (input == "y") {
                            print("replace success\r\n")
                            override = true
                            newLines.add(line.replace(libs, newLibs))
                            return@forEach
                        } else {
                            print("skip\r\n ")
                            break
                        }
                    }
                }
            }
            newLines.add(line)
        }
        if (override) {
            val newFile = File(file.parent, file.name.removeSuffix(".gradle") + ".temp")
            newLines.forEach {
                newFile.appendText(it + "\r\n")
            }
            return newFile
        }
        return null
    }
}