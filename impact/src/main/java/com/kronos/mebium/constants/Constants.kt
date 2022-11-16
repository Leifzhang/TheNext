package com.kronos.mebium.constants

/**
 *
 *  @Author LiABao
 *  @Since 2022/10/26
 *
 */
object Constants {
    const val BUILD_GRADLE = "logger.lifecycle 'configure :%s as root project'\n" +
            "\n" +
            "def root = file('%sbuild.gradle')\n" +
            "if (!root.exists()) throw new GradleException('unexpected behavior!')\n" +
            "apply from: root\n"
}