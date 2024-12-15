package jp.speakbuddy.edisonandroidexercise.ui.fact

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import jp.speakbuddy.edisonandroidexercise.R
import jp.speakbuddy.edisonandroidexercise.ui.common.TypeWriterText
import jp.speakbuddy.edisonandroidexercise.ui.common.image.Image
import jp.speakbuddy.edisonandroidexercise.ui.common.image.ImageSource
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
                FactError(errorMessage = uiState.errorMessage, onRefreshClicked = onRefreshClicked)
            }

            // Initial state, doesn't need any implementation
            FactUiState.None -> Unit
        }
    }
}

@Composable
fun FactError(
    modifier: Modifier = Modifier,
    errorMessage: String,
    onRefreshClicked: () -> Unit,
) {
    Column(
        modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.CenterVertically
        )
    ) {
        Text(
            text = stringResource(R.string.fact_error_title),
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = errorMessage,
            style = MaterialTheme.typography.bodyLarge
        )

        Button(onClick = onRefreshClicked) {
            Text(
                text = stringResource(R.string.refresh_button)
            )
        }
    }
}

@Composable
fun FactContent(
    modifier: Modifier = Modifier,
    factDisplayData: FactDisplayData,
    onRefreshClicked: () -> Unit,
) {
    factDisplayData.toastMessage?.let {
        val localContext = LocalContext.current
        Toast.makeText(localContext, it, Toast.LENGTH_SHORT).show()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.Bottom
            )
        ) {
            Card(
                modifier = Modifier
                    .padding(8.dp),
                colors = CardDefaults.cardColors().copy(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(space = 16.dp)
                ) {
                    Text(
                        text = factDisplayData.title,
                        style = MaterialTheme.typography.titleMedium
                    )

                    TypeWriterText(
                        text = factDisplayData.fact,
                        style = MaterialTheme.typography.bodyLarge,
                        onTypingComplete = {

                        }
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = stringResource(R.string.click_me_description))

                Image(
                    modifier = Modifier
                        .size(120.dp)
                        .clickable { onRefreshClicked.invoke() },
                    imageSource = factDisplayData.headerImage
                )
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
                .fillMaxWidth()
                .height(300.dp)
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
                ImageSource.Url("https://png.pngtree.com/png-clipart/20220626/original/pngtree-pink-cute-cat-icon-animal-png-yuri-png-image_8188672.png"),
                title = "Sample length",
                fact = "This is sample fact",
                showMultipleCats = true,
            )
        ), onRefreshClicked = { })
    }
}
