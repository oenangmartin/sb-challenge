package jp.speakbuddy.edisonandroidexercise.ui.common.lottie

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun AnimatorComponent(
    source: LottieSource,
) {
    val composition by rememberLottieComposition(source.getSource())
    LottieAnimation(
        modifier = Modifier.size(320.dp),
        composition = composition,
    )
}