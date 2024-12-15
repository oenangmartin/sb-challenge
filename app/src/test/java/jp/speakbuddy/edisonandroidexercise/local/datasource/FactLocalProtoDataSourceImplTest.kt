package jp.speakbuddy.edisonandroidexercise.local.datasource
import androidx.datastore.core.DataStore
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import jp.speakbuddy.edisonandroidexercise.local.data.FactEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test

class FactLocalProtoDataSourceImplTest {

    private val factDataStore: DataStore<FactEntity> = mockk()
    private val factLocalProtoDataSourceImpl = FactLocalProtoDataSourceImpl(factDataStore)

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `when get fact and fact is available, should return correct fact`() = runTest {
        // Arrange
        val expectedFact = FactEntity(fact = "Test fact")
        coEvery { factDataStore.data } returns flowOf(expectedFact)

        // Act
        val actualFact = factLocalProtoDataSourceImpl.getFact()

        // Assert
        assertEquals(expectedFact, actualFact)
    }

    @Test
    fun `when flow return null should return null`() = runTest {
        // Arrange
        val mockFlow = mockk<Flow<FactEntity>>()
        coEvery { mockFlow.firstOrNull() } returns null
        coEvery { mockFlow.collect(any()) } just Runs
        coEvery { factDataStore.data } returns mockFlow

        // Act
        val actualFact = factLocalProtoDataSourceImpl.getFact()

        // Assert
        assertEquals(null, actualFact)
    }

    @Test
    fun `when flow return item with length -1 should return null`() = runTest {
        // Arrange
        val expectedFact = FactEntity(length = -1)
        coEvery { factDataStore.data } returns flowOf(expectedFact)

        // Act
        val actualFact = factLocalProtoDataSourceImpl.getFact()

        // Assert
        assertEquals(null, actualFact)
    }

    @Test
    fun `when updateFact should trigger update data to factDataStore correctly`() = runTest {
        // Arrange
        val factToUpdate = FactEntity(fact = "Updated fact")
        coEvery { factDataStore.updateData(any()) } returns FactEntity()

        // Act
        factLocalProtoDataSourceImpl.updateFact(factToUpdate)

        // Assert
        coVerify { factDataStore.updateData(any()) }
    }
}