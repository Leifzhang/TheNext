package com.kronos.mebium.utils


/**
 *
 *  @Author LiABao
 *  @Since 2022/12/9
 *
 */

const val ANSI_RESET = "\u001B[0m"
const val ANSI_BLACK = "\u001B[30m"
const val ANSI_RED = "\u001B[31m"
const val ANSI_GREEN = "\u001B[32m"
const val ANSI_YELLOW = "\u001B[33m"
const val ANSI_BLUE = "\u001B[34m"
const val ANSI_PURPLE = "\u001B[35m"
const val ANSI_CYAN = "\u001B[36m"
const val ANSI_WHITE = "\u001B[37m"


fun CharSequence.red(): String {
    return ANSI_RED + this + ANSI_RESET
}

fun CharSequence.green(): String {
    return ANSI_GREEN + this + ANSI_RESET
}

fun CharSequence.yellow(): String {
    return ANSI_YELLOW + this + ANSI_RESET
}