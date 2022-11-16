package com.kronos.mebium.entity

import com.beust.jcommander.Parameter
import com.beust.jcommander.Parameters
import com.beust.jcommander.converters.FileConverter
import java.io.File

/**
 * @Author LiABao
 * @Since 2022/11/8
 */

@Parameters(commandDescription = "args 参数")
class CommandEntity {

    @Parameter(
        names = ["-file", "-f"],
        required = true,
        converter = FileConverter::class,
        description = "生成目标文件路径"
    )
    lateinit var file: File

    @Parameter(
        names = ["-name"], required = true,
        description = "文件名"
    )
    lateinit var name: String

    @Parameter(names = ["-group", "-bundle", "-g", "-b"], description = "唯一标识符")
    var group: String? = null

}