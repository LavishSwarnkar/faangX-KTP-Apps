package com.faangx.ktp

object MiniAppFunctionalityHelper {

    val map = mutableMapOf<String, Any>()

    fun saveFunctionality(name: String, any: Any) {
        map[name] = any
    }

    fun getFunctionality(name: String): Any {
        return map.getOrElse(name) {
            error("No functionality found for $name")
        }
    }

}