package jp.speakbuddy.edisonandroidexercise.mapper.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import jp.speakbuddy.edisonandroidexercise.mapper.FactDisplayDataMapper

@Module
@InstallIn(ViewModelComponent::class)
object MapperModule {
    @Provides
    fun provideFactDisplayDataMapper(@ApplicationContext context: Context): FactDisplayDataMapper {
        return FactDisplayDataMapper(context)
    }
}