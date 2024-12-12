package jp.speakbuddy.edisonandroidexercise.ui.fact

import androidx.compose.runtime.Stable

@Stable
data class FactDisplayData(
    val fact: String,
    val length: String?,
    val showMultipleCats: Boolean,
)