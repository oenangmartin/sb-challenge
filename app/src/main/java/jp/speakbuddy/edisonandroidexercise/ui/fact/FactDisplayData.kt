package jp.speakbuddy.edisonandroidexercise.ui.fact

import androidx.compose.runtime.Stable
import jp.speakbuddy.edisonandroidexercise.ui.common.image.ImageSource

@Stable
data class FactDisplayData(
    val headerImage: ImageSource,
    val title: String,
    val fact: String,
    val showMultipleCats: Boolean,
)