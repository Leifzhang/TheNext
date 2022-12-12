package com.kronos.mebium.android

import com.beust.jcommander.JCommander
import com.kronos.mebium.action.Handler
import com.kronos.mebium.entity.CommandEntity
import com.kronos.mebium.file.getRootProjectDir
import com.kronos.mebium.utils.green
import com.kronos.mebium.utils.red
import com.kronos.mebium.utils.yellow
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
    var isSkip = false

    override fun handle(args: Array<String>) {
        isSkip = args.contains(skip)
        val realArgs = if (isSkip) {
            arrayListOf<String>().apply {
                args.forEach {
                    if (it != skip) {
                        add(it)
                    }
                }
            }.toTypedArray()
        } else {
            args
        }
        val commandEntity = CommandEntity()
        JCommander.newBuilder().addObject(commandEntity).build().parse(*realArgs)
        val first = commandEntity.file
        val name = commandEntity.name
        val root = first
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
        if (overrideList.isEmpty()) {
            return
        }
        println("if you want overwrite all this file ? input y to confirm \r\n".red())
        val input = scanner.next()
        if (input == "y") {
            overrideList.forEach {
                it.first.delete()
                it.second.renameTo(it.first)
            }
            print("replace success \r\n ".green())
        } else {
            print("skip\r\n ".yellow())
        }
    }

    private val pattern =
        "(\\D\\S*)(implementation|Implementation|compileOnly|CompileOnly|test|Test|api|Api|kapt|Kapt|Processor)([ (])(\\D\\S*)".toPattern()

    private fun onGradleCheck(file: File): File? {
        var override = false
        val lines = file.readLines()
        val newLines = mutableListOf<String>()
        lines.forEach { line ->
            val matcher = pattern.matcher(line)
            if (matcher.find()) {
                val libs = matcher.group(4)
                if (!libs.contains(":") && !libs.contains("files(")) {
                    val newLibs =
                        libs.replace("\'", "").replace("\"", "").replace("-", ".").replace("_", ".")
                            .replace("kotlin.libs", "kotlinlibs").replace("[", ".").replace("]", "")
                    if (newLibs == libs) {
                        newLines.add(line)
                        return@forEach
                    }
                    print("fileName: ${file.name} dependencies : $line \r\n")
                    if (isSkip) {
                        override = true
                        newLines.add(line.replace(libs, newLibs))
                        print("$libs do you want replace to $newLibs    \r\n ".green())
                        return@forEach
                    }
                    print("$libs do you want replace to $newLibs  ? input  y to replace  \r\n ".red())
                    while (true) {
                        val input = scanner.next()
                        if (input == "y") {
                            print("replace success\r\n".green())
                            override = true
                            newLines.add(line.replace(libs, newLibs))
                            return@forEach
                        } else {
                            print("skip\r\n ".yellow())
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

const val skip = "--skip"