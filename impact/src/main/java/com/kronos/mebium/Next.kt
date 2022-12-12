package com.kronos.mebium

import com.kronos.mebium.factory.HandlerFactory

object Next {

    @JvmStatic
    fun main(args: Array<String>) {
        if (args.isEmpty()) {
            println("所有参数为空")
            return
        }
        val first = args.first()
        HandlerFactory.chooseHandler(first)?.handle(args.copyOfRange(1, args.size))
    }


}