package jp.speakbuddy.edisonandroidexercise.local.data

import kotlinx.serialization.Serializable

@Serializable
data class FactEntity(
    val fact: String = "",
    val length: Int = 0,
)