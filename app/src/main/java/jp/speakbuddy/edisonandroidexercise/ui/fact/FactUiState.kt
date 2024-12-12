package jp.speakbuddy.edisonandroidexercise.ui.fact

sealed interface FactUiState {
    companion object {
        val INITIAL = Loading
    }
    data object Loading: FactUiState
    data class Content(
        val factDisplayData: FactDisplayData,
    ) : FactUiState

    data class Error(
        val errorMessage: String
    ) : FactUiState
}