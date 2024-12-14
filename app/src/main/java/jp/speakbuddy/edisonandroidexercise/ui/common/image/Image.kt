package jp.speakbuddy.edisonandroidexercise.ui.common.image

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil3.compose.AsyncImage

@Composable
fun Image(
    modifier: Modifier = Modifier,
    imageSource: ImageSource,
    contentDescription: String? = null,
) {
    AsyncImage(
        imageSource.getSource(),
        contentDescription = contentDescription
    )
}