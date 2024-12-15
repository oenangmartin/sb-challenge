package jp.speakbuddy.edisonandroidexercise.ui.fact

sealed interface FactUiState {
    companion object {
        val INITIAL = None
    }
    data object None: FactUiState
    data class Content(
        val factDisplayData: FactDisplayData,
    ) : FactUiState

    data class Error(
        val errorMessage: String
    ) : FactUiState
}