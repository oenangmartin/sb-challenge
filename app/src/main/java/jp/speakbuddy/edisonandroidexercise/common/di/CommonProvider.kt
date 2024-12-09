package jp.speakbuddy.edisonandroidexercise.common.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.speakbuddy.edisonandroidexercise.common.DefaultDispatcherProvider
import jp.speakbuddy.edisonandroidexercise.common.DispatcherProvider

@Module
@InstallIn(SingletonComponent::class)
object CommonProvider {
    @Provides
    fun provideDispatcherProvider(defaultDispatcherProvider: DefaultDispatcherProvider) : DispatcherProvider {
        return defaultDispatcherProvider
    }
}