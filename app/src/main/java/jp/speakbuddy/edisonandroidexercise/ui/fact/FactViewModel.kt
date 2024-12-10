package jp.speakbuddy.edisonandroidexercise.ui.fact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.speakbuddy.edisonandroidexercise.common.DispatcherProvider
import jp.speakbuddy.edisonandroidexercise.local.data.FactEntity
import jp.speakbuddy.edisonandroidexercise.local.datasource.FactLocalDataSource
import jp.speakbuddy.edisonandroidexercise.network.data.FactResponse
import jp.speakbuddy.edisonandroidexercise.network.datasource.FactNetworkDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FactViewModel @Inject constructor(
    private val factNetworkDataSource: FactNetworkDataSource,
    private val factLocalDataSource: FactLocalDataSource,
    private val dispatcherProvider: DispatcherProvider,
) : ViewModel() {
    private val _uiState: MutableStateFlow<FactUiState> = MutableStateFlow(FactUiState.INITIAL)
    val uiState = _uiState.asStateFlow()

    fun updateFact() {
        viewModelScope.launch(dispatcherProvider.main) {
            factNetworkDataSource.getFact()
                .onSuccess { factResponse ->
                    factLocalDataSource.updateFact(factResponse.toEntity())
                    _uiState.update {
                        FactUiState.Content(factResponse.fact)
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

private fun FactResponse.toEntity() = FactEntity(fact, length)
