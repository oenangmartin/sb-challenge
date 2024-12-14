package jp.speakbuddy.edisonandroidexercise.ui.common

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import jp.speakbuddy.edisonandroidexercise.ui.theme.EdisonAndroidExerciseTheme
import kotlinx.coroutines.delay

/**
 * Composable for Text with TypeWriting behavior,
 * updating text in this component will make the text being set character by character, mimicking normal typing
 */
@Composable
fun TypeWriterText(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle = LocalTextStyle.current,
    typingSpeed: Long = 25L,
) {
    var textToDisplay by remember { mutableStateOf("") }

    LaunchedEffect(text) {
        textToDisplay = ""
        text.forEach { c ->
            textToDisplay += c
            delay(typingSpeed)
        }
    }

    Text(
        modifier = modifier,
        text = textToDisplay,
        style = style,
    )
}


@Preview
@Composable
private fun TypeWriterTextPreview() {
    EdisonAndroidExerciseTheme {
        TypeWriterText(text = "Test")
    }
}