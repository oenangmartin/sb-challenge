package jp.speakbuddy.edisonandroidexercise.common.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.speakbuddy.edisonandroidexercise.common.DefaultDispatcherProvider
import jp.speakbuddy.edisonandroidexercise.common.DispatcherProvider

@Module
@InstallIn(SingletonComponent::class)
abstract class CommonProvider {
    @Binds
    abstract fun provideDispatcherProvider(defaultDispatcherProvider: DefaultDispatcherProvider) : DispatcherProvider
}