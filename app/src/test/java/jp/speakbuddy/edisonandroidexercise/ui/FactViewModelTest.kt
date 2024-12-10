package jp.speakbuddy.edisonandroidexercise.ui

import app.cash.turbine.test
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import jp.speakbuddy.edisonandroidexercise.common.DispatcherProvider
import jp.speakbuddy.edisonandroidexercise.network.data.FactResponse
import jp.speakbuddy.edisonandroidexercise.network.datasource.FactNetworkDataSource
import jp.speakbuddy.edisonandroidexercise.ui.common.DefaultTestDispatcherProvider
import jp.speakbuddy.edisonandroidexercise.ui.fact.FactUiState
import jp.speakbuddy.edisonandroidexercise.ui.fact.FactViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Test
import java.io.IOException

class FactViewModelTest {
    private val factNetworkDataSource: FactNetworkDataSource = mockk()
    private val dispatcherProvider: DispatcherProvider = DefaultTestDispatcherProvider()
    private val viewModel = FactViewModel(factNetworkDataSource, dispatcherProvider)

    @After
    fun afterEach() {
        clearAllMocks()
    }

    @Test
    fun `when updateFact is triggered and fetchNetwork Success, should return ui state with facts correctly`() = runTest {
        val newFact = "New Facts"
        coEvery { factNetworkDataSource.getFact() } returns Result.success(
            FactResponse(
                length = newFact.length,
                fact = newFact
            )
        )

        viewModel.uiState.test {
            assertEquals(FactUiState.INITIAL, awaitItem())
            viewModel.updateFact()
            assertEquals(FactUiState.Content(newFact), awaitItem())
            ensureAllEventsConsumed()
        }
    }

    @Test
    fun `when updateFact is triggered and fetchNetwork Failure, should return ui state with facts correctly`() = runTest {
        coEvery { factNetworkDataSource.getFact() } returns Result.failure(IOException())

        viewModel.uiState.test {
            assertEquals(FactUiState.INITIAL, awaitItem())
            viewModel.updateFact()
            assertEquals(FactUiState.Error("Dummy Error Message now"), awaitItem())
            ensureAllEventsConsumed()
        }
    }
}
