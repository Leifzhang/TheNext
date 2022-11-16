package com.kronos.mebium.android

import com.beust.jcommander.JCommander
import com.kronos.mebium.action.Handler
import com.kronos.mebium.constants.Constants
import com.kronos.mebium.entity.CommandEntity
import com.kronos.mebium.file.*
import java.io.File

/**
 *
 *  @Author LiABao
 *  @Since 2022/10/26
 *
 */
class AndroidProjectHandler : Handler {

    override fun handle(args: Array<String>) {

        val commandEntity = CommandEntity()
        JCommander.newBuilder().addObject(commandEntity).build().parse(*args)
        val first = commandEntity.file
        val name = commandEntity.name
        val pwd = first
        val root = pwd.getRootProjectDir() ?: return
        val path = root.canonicalPathOnly(pwd)
        val zipFile = File(pwd, "$name.zip")
        ResourceUtils.readInputSteam("zip/android/project.zip")?.moveTo(zipFile)
        val freedom = File(pwd, name)
        try {
            UnzipUtils.unzip(zipFile, freedom)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            zipFile.delete()
        }
        val buildGradle = File(freedom, "build.gradle")
        // 建立软连接  因为本身查一个文件夹路径 所以补充
        buildGradle.appendText(String.format(Constants.BUILD_GRADLE, name, "../$path"))
        CmdUtil.execute("ln -s ${path}../gradle", freedom)
        CmdUtil.execute("ln -s ${path}../gradle.properties", freedom)
        CmdUtil.execute("ln -s ${path}../gradlew", freedom)
        CmdUtil.execute("ln -s ${path}../gradlew.bat", freedom)
        CmdUtil.execute("ln -s ${path}../dependencies.gradle", freedom)
        val rootSettings = File(root, "settings.gradle")
        val includePath =
            freedom.path.removePrefix(prefix(freedom.path, root.path) + File.separator)
        rootSettings.appendText("\rincludeBuild(\"$includePath\")")
        //  rootSettings.appendText()
    }


}