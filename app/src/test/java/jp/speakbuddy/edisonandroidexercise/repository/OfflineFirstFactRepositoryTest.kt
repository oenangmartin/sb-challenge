package jp.speakbuddy.edisonandroidexercise.repository

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import jp.speakbuddy.edisonandroidexercise.common.DispatcherProvider
import jp.speakbuddy.edisonandroidexercise.local.data.FactEntity
import jp.speakbuddy.edisonandroidexercise.local.datasource.FactLocalDataSource
import jp.speakbuddy.edisonandroidexercise.network.data.FactResponse
import jp.speakbuddy.edisonandroidexercise.network.datasource.FactNetworkDataSource
import jp.speakbuddy.edisonandroidexercise.repository.model.FactModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class OfflineFirstFactRepositoryTest {

    private val factNetworkDataSource: FactNetworkDataSource = mockk()
    private val factLocalDataSource: FactLocalDataSource = mockk()
    private val dispatcherProvider: DispatcherProvider = mockk {
        coEvery { io } returns Dispatchers.Unconfined
    }
    private val repository = OfflineFirstFactRepository(
        factNetworkDataSource,
        factLocalDataSource,
        dispatcherProvider
    )

    @Test
    fun testGetFact_noForceFetch_returnsLocalData() = runTest {
        // Arrange
        val localFact = FactEntity("Local fact", 10)
        coEvery { factLocalDataSource.getFact() } returns localFact

        // Act
        val result = repository.getFact(forceFetch = false)

        // Assert
        assertEquals(Result.success(FactModel("Local fact", 10)), result)
        coVerify(exactly = 0) { factNetworkDataSource.getFact() } // Verify network wasn't called
    }

    @Test
    fun testGetFact_forceFetch_fetchesFromRemote() = runTest {
        // Arrange
        val remoteFact = FactResponse("Remote fact", 12)
        coEvery { factNetworkDataSource.getFact() } returns Result.success(remoteFact)
        coEvery { factLocalDataSource.updateFact(any()) } returns FactEntity("Remote fact", 12)

        // Act
        val result = repository.getFact(forceFetch = true)

        // Assert
        assertEquals(Result.success(FactModel("Remote fact", 12)), result)
        coVerify { factNetworkDataSource.getFact() } // Verify network was called
    }

    @Test
    fun testGetFact_noLocalData_fetchesFromRemote() = runTest {
        // Arrange
        val remoteFact = FactResponse("Remote fact", 12)
        coEvery { factLocalDataSource.getFact() } returns null
        coEvery { factNetworkDataSource.getFact() } returns Result.success(remoteFact)
        coEvery { factLocalDataSource.updateFact(any()) } returns FactEntity("Remote fact", 12)

        // Act
        val result = repository.getFact(forceFetch = false)

        // Assert
        assertEquals(Result.success(FactModel("Remote fact", 12)), result)
        coVerify { factNetworkDataSource.getFact() } // Verify network was called
    }

    @Test
    fun testGetFact_networkError_returnsFailure() = runTest {
        // Arrange
        coEvery { factLocalDataSource.getFact() } returns null
        coEvery { factNetworkDataSource.getFact() } returns Result.failure(Exception("Network error"))

        // Act
        val result = repository.getFact(forceFetch = false)

        // Assert
        assert(result.isFailure) // Verify result is a failure
        coVerify { factNetworkDataSource.getFact() } // Verify network was called
    }
}