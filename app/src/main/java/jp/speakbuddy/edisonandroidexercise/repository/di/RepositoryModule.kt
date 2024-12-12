package jp.speakbuddy.edisonandroidexercise.repository.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import jp.speakbuddy.edisonandroidexercise.repository.FactRepository
import jp.speakbuddy.edisonandroidexercise.repository.OfflineFirstFactRepository

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideFactRepository(offlineFirstFactRepository: OfflineFirstFactRepository): FactRepository
}