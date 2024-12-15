package jp.speakbuddy.edisonandroidexercise.repository.model

/**
 * Data class that represent FactModel
 * This class will be the representation of Fact that's fetched either from remote or local
 */
data class FactModel(
    val fact: String,
    val length: Int,
)

