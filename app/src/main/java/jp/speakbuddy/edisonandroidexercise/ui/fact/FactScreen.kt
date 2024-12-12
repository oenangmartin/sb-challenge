package jp.speakbuddy.edisonandroidexercise.ui.fact

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
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
    when (uiState) {
        is FactUiState.Content -> {
            FactContent(factContent = uiState, onRefreshClicked = onRefreshClicked)
        }
        is FactUiState.Error -> {
            // TODO add Error screen later
        }
        FactUiState.Loading -> {
            // TODO add loading mechanism
        }
    }
}

@Composable
fun FactContent(
    modifier: Modifier = Modifier,
    factContent: FactUiState.Content,
    onRefreshClicked: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
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

        Text(
            text = factContent.fact,
            style = MaterialTheme.typography.bodyLarge
        )

        Button(onClick = onRefreshClicked) {
            Text(text = "Update fact")
        }
    }
}

@Preview
@Composable
private fun FactScreenPreview() {
    EdisonAndroidExerciseTheme {
        FactScreen(uiState = FactUiState.Content("This is sample facts"), onRefreshClicked = { })
    }
}
