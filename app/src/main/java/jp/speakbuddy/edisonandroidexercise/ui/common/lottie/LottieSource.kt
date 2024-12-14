package jp.speakbuddy.edisonandroidexercise.ui.common.lottie

import androidx.compose.runtime.Immutable
import com.airbnb.lottie.compose.LottieCompositionSpec

@Immutable
sealed interface LottieSource {
    fun getSource(): LottieCompositionSpec

    @Immutable
    data class Url(val url: String) : LottieSource {
        override fun getSource(): LottieCompositionSpec {
            return LottieCompositionSpec.Url(url)
        }
    }

    @Immutable
    data class Resource(val resId: Int) : LottieSource {
        override fun getSource(): LottieCompositionSpec {
            return LottieCompositionSpec.RawRes(resId)
        }
    }
}