package jp.speakbuddy.edisonandroidexercise.network.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.speakbuddy.edisonandroidexercise.network.FactService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Converter
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideFactService(retrofit: Retrofit): FactService {
        return retrofit.create(FactService::class.java)
    }

    @Provides
    fun provideRetrofitBuilder(jsonConverterFactory: Converter.Factory): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://catfact.ninja/")
            .addConverterFactory(jsonConverterFactory)
            .build()
    }

    @Provides
    fun provideJsonConverterFactory(): Converter.Factory {
        return Json.asConverterFactory("application/json".toMediaType())
    }
}