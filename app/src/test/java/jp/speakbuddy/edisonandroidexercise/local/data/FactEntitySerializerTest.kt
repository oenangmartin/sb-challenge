package jp.speakbuddy.edisonandroidexercise.local.data

import io.mockk.coEvery
import io.mockk.mockkObject
import jp.speakbuddy.edisonandroidexercise.common.DispatcherProvider
import jp.speakbuddy.edisonandroidexercise.ui.common.DefaultTestDispatcherProvider
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream


class FactEntitySerializerTest {

    private val dispatcherProvider: DispatcherProvider = DefaultTestDispatcherProvider()
    private lateinit var serializer: FactEntitySerializer

    @Before
    fun setup() {
        serializer = FactEntitySerializer(dispatcherProvider)
    }

    @Test
    fun `when serializer read executed with correct data, should return correct data`() = runTest {
        // Arrange
        val input = ByteArrayInputStream("""{"fact":"Test fact", "length": 20}""".toByteArray())

        // Act
        val result = serializer.readFrom(input)

        // Assert
        assertEquals(FactEntity("Test fact", 20), result)
    }

    @Test
    fun `when serializer read from invalid data, should return data with length -1`() = runTest {
        // Arrange
        val input = ByteArrayInputStream("invalid json".toByteArray())

        // Act
        val result = serializer.readFrom(input)

        // Assert
        assertEquals(FactEntity(length = -1), result)
    }

    @Test
    fun `when serializer write function is executed, should set the output stream correctly`() = runTest {
        // Arrange
        val output = ByteArrayOutputStream()
        val factEntity = FactEntity("Test fact", 20)
        // not ideal, maybe consider wrapping json to another class, but for now should be good enough
        mockkObject(Json) {
            coEvery { Json.encodeToString(FactEntity.serializer(), factEntity) } returns """{"fact":"Test fact", "length": 20}"""
        }

        // Act
        serializer.writeTo(factEntity, output)

        // Assert
        assertEquals("""{"fact":"Test fact","length":20}""", output.toString())
    }
}