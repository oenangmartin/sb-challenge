package jp.speakbuddy.edisonandroidexercise.mapper
import jp.speakbuddy.edisonandroidexercise.repository.model.FactModel
import jp.speakbuddy.edisonandroidexercise.ui.fact.FactDisplayData
import org.junit.Assert.assertEquals
import org.junit.Test

class FactDisplayDataMapperTest {

    private val mapper = FactDisplayDataMapper()

    @Test
    fun `when map is triggered with fact length less than 100, it should set length to null`() {
        // Arrange
        val factModel = FactModel("Short fact", length = FACT_LENGTH_VISIBILITY_THRESHOLD - 1)

        // Act
        val result = mapper.map(factModel)

        // Assert
        assertEquals(FactDisplayData("Short fact", null, false), result)
    }

    @Test
    fun `when map is triggered with fact length bigger 100, it should set length correctly`() {
        // Arrange
        val factModel = FactModel("Long fact", length = FACT_LENGTH_VISIBILITY_THRESHOLD + 1)

        // Act
        val result = mapper.map(factModel)

        // Assert
        assertEquals(FactDisplayData("Long fact", "Length > 100", false), result)
    }

    @Test
    fun `when map is triggered with multiple cats, it should show multiple cats`() {
        // Arrange
        val factModel = FactModel("Fact about $MULTIPLE_CATS", length = 50)

        // Act
        val result = mapper.map(factModel)

        // Assert
        assertEquals(FactDisplayData("Fact about $MULTIPLE_CATS", null, true), result)
    }

    @Test
    fun `when map is triggered with no multiple cats, it should not show multiple cats`() {
        // Arrange
        val factModel = FactModel("Fact about dogs", length = 50)

        // Act
        val result = mapper.map(factModel)

        // Assert
        assertEquals(FactDisplayData("Fact about dogs", null, false), result)
    }
}