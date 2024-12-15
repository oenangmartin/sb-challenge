package jp.speakbuddy.edisonandroidexercise.mapper.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import jp.speakbuddy.edisonandroidexercise.mapper.FactDisplayDataMapper
import jp.speakbuddy.edisonandroidexercise.mapper.FactErrorMapper

@Module
@InstallIn(ViewModelComponent::class)
object MapperModule {
    @Provides
    fun provideFactDisplayDataMapper(@ApplicationContext context: Context): FactDisplayDataMapper {
        return FactDisplayDataMapper(context)
    }

    @Provides
    fun provideErrorMapper(@ApplicationContext context: Context): FactErrorMapper {
        return FactErrorMapper(context)
    }
}