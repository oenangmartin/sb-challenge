package jp.speakbuddy.edisonandroidexercise.ui.fact

sealed interface FactUiState {
    companion object {
        val INITIAL = Loading
    }
    data object Loading: FactUiState
    data class Content(
        val fact: String,
    ) : FactUiState

    data class Error(
        val errorMessage: String
    ) : FactUiState
}