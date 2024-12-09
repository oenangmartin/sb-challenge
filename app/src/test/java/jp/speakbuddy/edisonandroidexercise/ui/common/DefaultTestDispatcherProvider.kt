package jp.speakbuddy.edisonandroidexercise.ui.common

import jp.speakbuddy.edisonandroidexercise.common.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher

class DefaultTestDispatcherProvider @OptIn(ExperimentalCoroutinesApi::class) constructor(
    override val io: CoroutineDispatcher = UnconfinedTestDispatcher(),
    override val main: CoroutineDispatcher = UnconfinedTestDispatcher(),
    override val default: CoroutineDispatcher = UnconfinedTestDispatcher(),
) : DispatcherProvider