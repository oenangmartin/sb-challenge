package jp.speakbuddy.edisonandroidexercise.mapper

import android.content.Context
import coil3.network.HttpException
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import jp.speakbuddy.edisonandroidexercise.R
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Test
import java.io.IOException
import java.net.UnknownHostException


class FactErrorMapperTest {
    private val context: Context = mockk()
    private val mapper = FactErrorMapper(context)
    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `when mapper triggered with error UnknownHostException should return no internet message`() {
        // Assign
        every { context.getString(R.string.no_internet) } returns "No Internet"
        val throwable = mockk<UnknownHostException>()

        // Act
        val message = mapper.map(throwable)

        // Assert
        assertEquals("No Internet", message)
    }

    @Test
    fun `when mapper triggered with error HttpException should return no internet message`() {
        // Assign
        every { context.getString(R.string.server_error) } returns "Server Error"
        val throwable = mockk<HttpException>()

        // Act
        val message = mapper.map(throwable)

        // Assert
        assertEquals("Server Error", message)
    }

    @Test
    fun `when mapper triggered with other error should return no internet message`() {
        // Assign
        every { context.getString(R.string.unknown_error) } returns "Unknown Error"
        val throwable = mockk<IOException>()

        // Act
        val message = mapper.map(throwable)

        // Assert
        assertEquals("Unknown Error", message)
    }

}