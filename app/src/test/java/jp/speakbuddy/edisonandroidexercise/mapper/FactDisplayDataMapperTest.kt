package jp.speakbuddy.edisonandroidexercise.mapper

import android.content.Context
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import jp.speakbuddy.edisonandroidexercise.R
import jp.speakbuddy.edisonandroidexercise.repository.model.FactModel
import jp.speakbuddy.edisonandroidexercise.ui.common.image.ImageSource
import jp.speakbuddy.edisonandroidexercise.ui.fact.FactDisplayData
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FactDisplayDataMapperTest {
    private val context: Context = mockk()
    private val mapper = FactDisplayDataMapper(context)
    private val expectedImageSource =
        ImageSource.Url("https://png.pngtree.com/png-clipart/20220626/original/pngtree-pink-cute-cat-icon-animal-png-yuri-png-image_8188672.png")

    @Before
    fun setup() {
        every { context.getString(R.string.multiple_cat_identifier) } returns "cats"
        every { context.getString(R.string.long_fact_title) } returns "Long Fact %d"
        every { context.getString(R.string.fact_title) } returns "Fact"
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `when map is triggered with fact length less than 100, it should set length to null`() {
        // Arrange
        val factModel = FactModel("Short fact", length = FACT_LENGTH_VISIBILITY_THRESHOLD - 1)

        // Act
        val result = mapper.map(factModel)

        // Assert
        assertEquals(
            FactDisplayData(
                headerImage = expectedImageSource,
                title = "Fact",
                fact = "Short fact",
                showMultipleCats = false
            ),
            result
        )
    }

    @Test
    fun `when map is triggered with fact length bigger 100, it should set length correctly`() {
        // Arrange
        val factModel = FactModel("Long fact", length = FACT_LENGTH_VISIBILITY_THRESHOLD + 1)

        // Act
        val result = mapper.map(factModel)

        // Assert
        assertEquals(
            FactDisplayData(
                headerImage = expectedImageSource,
                title = "Long Fact ${FACT_LENGTH_VISIBILITY_THRESHOLD + 1}",
                fact = "Long fact",
                showMultipleCats = false
            ),
            result
        )
    }

    @Test
    fun `when map is triggered with multiple cats, it should show multiple cats`() {
        // Arrange
        val factModel = FactModel("Fact about cats", length = 50)

        // Act
        val result = mapper.map(factModel)

        // Assert
        assertEquals(
            FactDisplayData(
                headerImage = expectedImageSource,
                fact = "Fact about cats",
                title = "Fact",
                showMultipleCats = true
            ),
            result
        )
    }
}