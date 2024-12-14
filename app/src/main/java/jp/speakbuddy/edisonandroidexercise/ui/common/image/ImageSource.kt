package jp.speakbuddy.edisonandroidexercise.ui.common.image

import androidx.compose.runtime.Immutable

@Immutable
sealed interface ImageSource {
    fun getSource(): Any?
    @Immutable
    data class Url(val url: String): ImageSource {
        override fun getSource(): String {
            return url
        }
    }
    @Immutable
    data class Resource(val resId: Int): ImageSource {
        override fun getSource(): Int {
            return resId
        }
    }
}