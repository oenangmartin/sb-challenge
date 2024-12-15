package jp.speakbuddy.edisonandroidexercise.mapper

import android.content.Context
import coil3.network.HttpException
import jp.speakbuddy.edisonandroidexercise.R
import java.net.UnknownHostException
import javax.inject.Inject

class FactErrorMapper @Inject constructor(
    private val context: Context
) {
    private val noInternetErrorMessage by lazy { context.getString(R.string.no_internet) }
    private val serverError by lazy { context.getString(R.string.server_error) }
    private val unknownError by lazy { context.getString(R.string.unknown_error) }

    fun map(throwable: Throwable): String {
        return when (throwable) {
            is UnknownHostException -> noInternetErrorMessage
            is HttpException -> serverError
            else -> unknownError
        }
    }
}