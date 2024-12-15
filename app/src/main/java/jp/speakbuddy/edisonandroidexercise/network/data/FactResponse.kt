package jp.speakbuddy.edisonandroidexercise.network.data

import kotlinx.serialization.Serializable

@Serializable
data class FactResponse(
    val fact: String,
    val length: Int
)