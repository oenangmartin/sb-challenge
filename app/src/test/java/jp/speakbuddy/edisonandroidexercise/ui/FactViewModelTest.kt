package jp.speakbuddy.edisonandroidexercise.ui

import io.mockk.mockk
import jp.speakbuddy.edisonandroidexercise.network.FactService
import jp.speakbuddy.edisonandroidexercise.ui.fact.FactViewModel
import org.junit.Test

class FactViewModelTest {
    private val factService: FactService = mockk(relaxed = true)
    private val viewModel = FactViewModel(factService)

    @Test
    fun updateFact() {
        var loading = true
        val initialFact = "initial"
        var fact = initialFact

        fact = viewModel.updateFact { loading = false }

        assert(!loading)
        assert(fact != initialFact)
    }
}
