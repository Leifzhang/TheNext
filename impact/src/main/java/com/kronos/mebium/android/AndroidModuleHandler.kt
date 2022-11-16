package com.kronos.mebium.android

import com.beust.jcommander.JCommander
import com.kronos.mebium.action.Handler
import com.kronos.mebium.entity.CommandEntity
import com.kronos.mebium.file.*
import java.io.File

/**
 *
 *  @Author LiABao
 *  @Since 2022/10/24
 *
 */
class AndroidModuleHandler : Handler {

    override fun handle(args: Array<String>) {
        val commandEntity = CommandEntity()
        JCommander.newBuilder().addObject(commandEntity).build().parse(*args)
        val name = commandEntity.name
        val group = commandEntity.group
        val pwd = commandEntity.file
        val settings = pwd.findSettings() ?: return
        val settingsText = settings.readText()
        if (settingsText.contains(name)) {
            println("当前工程${name}已经存在Settings中")
            return
        }
        val zipFile = File(settings.parentFile, "$name.zip")
        ResourceUtils.readInputSteam("zip/android/android.zip")?.moveTo(zipFile)
        val strike = File(settings.parentFile, name)
        try {
            UnzipUtils.unzip(zipFile, strike)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            zipFile.delete()
        }
        val maven = File(strike, "maven.yaml")
        maven.createFile(createMaven(name, group ?: ""))
        val buildGradle = File(strike, "build.gradle")
        val lines = buildGradle.readLines()
        buildGradle.delete()
        lines.onEach {
            if (it.contains("namespace")) {
                buildGradle.appendText(
                    it.replace(
                        "ManifestPlaceHolder",
                        "${group}.${name.replace("-", ".")}"
                    )
                )
            } else {
                buildGradle.appendText(it + "\r")
            }
        }
        val groupFile = group?.replace(".", "/")
        val srcFile = File(strike, "src/main/java/$groupFile")
        srcFile.mkdirs()
        settings.appendText("\r\ninclude ':$name'")
    }
}