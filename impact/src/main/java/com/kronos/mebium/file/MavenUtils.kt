package com.kronos.mebium.file

import org.yaml.snakeyaml.DumperOptions
import org.yaml.snakeyaml.Yaml
import java.io.File


/**
 *
 *  @Author LiABao
 *  @Since 2022/10/25
 *
 */

fun createMaven(name: String, group: String): HashMap<String, HashMap<String, Any?>> {
    val info = hashMapOf<String, Any?>()
    info["group"] = group
    info["name"] = name
    info["version"] = 1
    val infoMap = hashMapOf<String, HashMap<String, Any?>>()
    infoMap["info"] = info
    return infoMap
}


fun File.createFile(map: HashMap<*, *>) {
    if (this.exists()) {
        this.delete()
    }
    val options = DumperOptions()
    options.indent = 2
    options.isPrettyFlow = true
    // Fix below - additional configuration
    options.defaultFlowStyle = DumperOptions.FlowStyle.BLOCK
    val yaml = Yaml(options)
    val text = yaml.dump(map)
    writeText(text)
}