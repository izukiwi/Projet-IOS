package com.example.myappeval.navigation

object Routes {
    const val LIST = "list"
    const val DETAIL = "detail/{characterId}"

    fun detail(id: Int) = "detail/$id"
}