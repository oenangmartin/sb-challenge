package jp.speakbuddy.edisonandroidexercise.ui.fact

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import jp.speakbuddy.edisonandroidexercise.ui.common.TypeWriterText
import jp.speakbuddy.edisonandroidexercise.ui.common.lottie.AnimatorComponent
import jp.speakbuddy.edisonandroidexercise.ui.common.lottie.LottieSource
import jp.speakbuddy.edisonandroidexercise.ui.theme.EdisonAndroidExerciseTheme

@Composable
fun FactRoute(
    modifier: Modifier = Modifier,
    factViewModel: FactViewModel = viewModel(),
) {
    val uiState by factViewModel.uiState.collectAsStateWithLifecycle()

    // initialize data
    LaunchedEffect(Unit) {
        factViewModel.updateFact(false)
    }

    FactScreen(
        modifier,
        uiState,
        {
            factViewModel.updateFact(true)
        }
    )
}

@Composable
fun FactScreen(
    modifier: Modifier = Modifier,
    uiState: FactUiState,
    onRefreshClicked: () -> Unit,
) {
    Column(modifier = modifier.fillMaxSize()) {
        when (uiState) {
            is FactUiState.Content -> {
                FactContent(
                    factDisplayData = uiState.factDisplayData,
                    onRefreshClicked = onRefreshClicked
                )
            }

            is FactUiState.Error -> {
                // TODO add Error screen later
            }

            FactUiState.Loading -> {
                // TODO add loading mechanism
            }
        }
    }
}

@Composable
fun FactContent(
    modifier: Modifier = Modifier,
    factDisplayData: FactDisplayData,
    onRefreshClicked: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.CenterVertically
            )
        ) {
            Text(
                text = "Fact",
                style = MaterialTheme.typography.titleLarge
            )

            TypeWriterText(
                text = factDisplayData.fact,
                style = MaterialTheme.typography.bodyLarge
            )

            factDisplayData.length?.let {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.End,
                    text = it,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Button(onClick = onRefreshClicked) {
                Text(text = "Update fact")
            }
        }

        MultipleCatsPopUp(isShown = factDisplayData.showMultipleCats)
    }
}

@Composable
fun MultipleCatsPopUp(
    modifier: Modifier = Modifier,
    isShown: Boolean,
) {
    if (isShown) {
        // currently this is an hardcoded url, so it's still acceptable to put this here with remember
        val lottieSource =
            remember { LottieSource.Url("https://lottie.host/9e613ad4-f91d-4375-9c3f-01e678335f48/9QyHhlOWia.lottie") }
        Box(
            modifier = modifier
                .fillMaxSize()
                .zIndex(10f),
        ) {
            Popup(
                alignment = Alignment.TopCenter,
                properties = PopupProperties(
                    dismissOnClickOutside = false,
                    usePlatformDefaultWidth = true
                ),
            ) {
                AnimatorComponent(lottieSource)
            }
        }
    }
}

@Preview
@Composable
private fun FactScreenPreview() {
    EdisonAndroidExerciseTheme {
        FactScreen(uiState = FactUiState.Content(
            FactDisplayData(
                fact = "This is sample fact",
                length = "Sample length",
                showMultipleCats = true,
            )
        ), onRefreshClicked = { })
    }
}
