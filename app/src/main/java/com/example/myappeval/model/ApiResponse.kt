package com.example.myappeval.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T>(
    val info: Info,
    val results: List<T>
)

@Serializable
data class Info(
    val count: Int,
    val pages: Int,
    @SerialName("next_page")
    val nextPage: String? = null,
    @SerialName("prev_page")
    val prevPage: String? = null
)