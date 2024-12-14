package jp.speakbuddy.edisonandroidexercise.ui.fact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.speakbuddy.edisonandroidexercise.common.DispatcherProvider
import jp.speakbuddy.edisonandroidexercise.mapper.FactDisplayDataMapper
import jp.speakbuddy.edisonandroidexercise.repository.FactRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FactViewModel @Inject constructor(
    private val factRepository: FactRepository,
    private val factDisplayDataMapper: FactDisplayDataMapper,
    private val dispatcherProvider: DispatcherProvider,
) : ViewModel() {
    private val _uiState: MutableStateFlow<FactUiState> = MutableStateFlow(FactUiState.INITIAL)
    val uiState = _uiState.asStateFlow()

    fun updateFact(forceFetch: Boolean) {
        viewModelScope.launch(dispatcherProvider.main) {
            factRepository.getFact(forceFetch)
                .map {
                    factDisplayDataMapper.map(it)
                }
                .onSuccess { factDisplayData ->
                    _uiState.update {
                        FactUiState.Content(factDisplayData)
                    }

                    if (factDisplayData.showMultipleCats) {
                        delay(2000L)
                        _uiState.update {
                            FactUiState.Content(factDisplayData.copy(showMultipleCats = false))
                        }
                    }
                }
                .onFailure { throwable ->
                    // TODO update error message later
                    _uiState.update {
                        FactUiState.Error("Dummy Error Message now")
                    }
                }
        }
    }
}