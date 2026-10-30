package com.example.myappeval.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonPrimitive

@Serializable
data class Character(
    val id: Int,
    val name: String? = null,
    val img: String? = null,
    val alias: List<String>? = null,
    val species: List<String>? = null,
    val gender: String? = null,
    val age: JsonPrimitive? = null,
    val height: String? = null,
    val birthplace: String? = null,
    val residence: String? = null,
    val status: String? = null,
    val occupation: String? = null,
    val groups: List<Group>? = null
)

@Serializable
data class Group(
    val name: String? = null,
    @SerialName("sub_groups")
    val subGroups: List<String>? = null
)