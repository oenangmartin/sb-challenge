package jp.speakbuddy.edisonandroidexercise.ui

import app.cash.turbine.test
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import jp.speakbuddy.edisonandroidexercise.mapper.FactDisplayDataMapper
import jp.speakbuddy.edisonandroidexercise.mapper.FactErrorMapper
import jp.speakbuddy.edisonandroidexercise.repository.FactRepository
import jp.speakbuddy.edisonandroidexercise.repository.model.FactModel
import jp.speakbuddy.edisonandroidexercise.ui.common.DefaultTestDispatcherProvider
import jp.speakbuddy.edisonandroidexercise.ui.common.image.ImageSource
import jp.speakbuddy.edisonandroidexercise.ui.fact.FactDisplayData
import jp.speakbuddy.edisonandroidexercise.ui.fact.FactUiState
import jp.speakbuddy.edisonandroidexercise.ui.fact.FactViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Test
import java.io.IOException

class FactViewModelTest {
    private val factRepository: FactRepository = mockk()
    private val factDisplayDataMapper: FactDisplayDataMapper = mockk()
    private val errorMapper: FactErrorMapper = mockk()
    private val dispatcherProvider: DefaultTestDispatcherProvider = DefaultTestDispatcherProvider()
    private val viewModel =
        FactViewModel(factRepository, factDisplayDataMapper, errorMapper, dispatcherProvider)

    private val expectedImageSource =
        ImageSource.Url("https://png.pngtree.com/png-clipart/20220626/original/pngtree-pink-cute-cat-icon-animal-png-yuri-png-image_8188672.png")

    @After
    fun afterEach() {
        clearAllMocks()
    }

    @Test
    fun `when updateFact is triggered and repository return success, should return ui state with facts correctly`() =
        runTest {
            val newFact = "New Facts"
            val factModel = FactModel(
                length = newFact.length,
                fact = newFact
            )
            val expectedFactDisplayData = FactDisplayData(
                headerImage = expectedImageSource,
                title = "A Very Long Fact",
                fact = "Long fact",
                showMultipleCats = false
            )
            coEvery { factRepository.getFact(false) } returns Result.success(factModel)
            every { factDisplayDataMapper.map(factModel) } returns expectedFactDisplayData

            viewModel.uiState.test {
                assertEquals(FactUiState.INITIAL, awaitItem())
                viewModel.updateFact(false)
                assertEquals(FactUiState.Content(expectedFactDisplayData), awaitItem())
                ensureAllEventsConsumed()
            }
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when updateFact is triggered and fetch success and show multiple cats true should update the UI correctly`() =
        runTest {
            val newFact = "New Facts"
            val factModel = FactModel(
                length = newFact.length,
                fact = newFact
            )
            val expectedFactDisplayData = FactDisplayData(
                headerImage = expectedImageSource,
                title = "A Very Long Fact",
                fact = "Long fact",
                showMultipleCats = true
            )
            val updatedFactDisplayData = FactDisplayData(
                headerImage = expectedImageSource,
                title = "A Very Long Fact",
                fact = "Long fact",
                showMultipleCats = false
            )
            coEvery { factRepository.getFact(false) } returns Result.success(factModel)
            every { factDisplayDataMapper.map(factModel) } returns expectedFactDisplayData
            viewModel.uiState.test {
                assertEquals(FactUiState.INITIAL, awaitItem())
                viewModel.updateFact(false)
                assertEquals(FactUiState.Content(expectedFactDisplayData), awaitItem())
                dispatcherProvider.getScheduler().advanceTimeBy(3000L)
                assertEquals(FactUiState.Content(updatedFactDisplayData), awaitItem())
                ensureAllEventsConsumed()
            }
        }

    @Test
    fun `when updateFact is triggered and repository return failure and there's no existing data, should return ui state with facts correctly`() =
        runTest {
            val exception = IOException()
            coEvery { factRepository.getFact(false) } returns Result.failure(exception)
            every { errorMapper.map(exception) } returns "Dummy Error Message now"
            viewModel.uiState.test {
                assertEquals(FactUiState.INITIAL, awaitItem())
                viewModel.updateFact(false)
                assertEquals(FactUiState.Error("Dummy Error Message now"), awaitItem())
                ensureAllEventsConsumed()
            }
        }

    @Test
    fun `when updateFact is triggered and repository return failure and there's existing data, should return ui state with facts correctly`() =
        runTest {
            val exception = IOException()
            val newFact = "New Facts"
            val factModel = FactModel(
                length = newFact.length,
                fact = newFact
            )
            val expectedFactDisplayData = FactDisplayData(
                headerImage = expectedImageSource,
                title = "A Very Long Fact",
                fact = "Long fact",
                showMultipleCats = false
            )
            val expectedErrorMessage = "Dummy Error Message now"
            coEvery { factRepository.getFact(false) } returns Result.success(factModel) andThen Result.failure(exception)
            every { factDisplayDataMapper.map(factModel) } returns expectedFactDisplayData
            every { errorMapper.map(exception) } returns expectedErrorMessage
            viewModel.uiState.test {
                assertEquals(FactUiState.INITIAL, awaitItem())
                viewModel.updateFact(false)
                assertEquals(FactUiState.Content(expectedFactDisplayData), awaitItem())
                viewModel.updateFact(false)
                assertEquals(FactUiState.Content(expectedFactDisplayData.copy(toastMessage = expectedErrorMessage)), awaitItem())
                ensureAllEventsConsumed()
            }
        }
}
