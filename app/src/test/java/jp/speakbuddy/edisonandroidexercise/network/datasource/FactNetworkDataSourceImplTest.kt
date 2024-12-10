package jp.speakbuddy.edisonandroidexercise.network.datasource

import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import jp.speakbuddy.edisonandroidexercise.data.FactResponse
import jp.speakbuddy.edisonandroidexercise.network.FactService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.IOException

@ExperimentalCoroutinesApi
class FactNetworkDataSourceImplTest {
    private val factService: FactService = mockk()

    private lateinit var factNetworkDataSourceImpl: FactNetworkDataSourceImpl

    @Before
    fun setup() {
        factNetworkDataSourceImpl = FactNetworkDataSourceImpl(factService)
    }
    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `when getFact is successful should return Result success correctly`() = runTest {
        val expectedFact = FactResponse(fact = "Facts", length = 5)
        coEvery{ factService.getFact() } returns expectedFact

        val actualFact = factNetworkDataSourceImpl.getFact()

        assertEquals(Result.success(expectedFact), actualFact)
        coVerify { factService.getFact() }
    }

    @Test
    fun `when getFact is failed should return Result failure`() = runTest {
        val expectedThrowable = IOException()
        coEvery{ factService.getFact() } throws expectedThrowable

        val actualFact = factNetworkDataSourceImpl.getFact()

        assertEquals(Result.failure<FactResponse>(expectedThrowable), actualFact)
        coVerify { factService.getFact() }
    }

}