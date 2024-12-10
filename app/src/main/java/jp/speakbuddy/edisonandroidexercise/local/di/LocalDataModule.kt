package jp.speakbuddy.edisonandroidexercise.local.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jp.speakbuddy.edisonandroidexercise.common.DispatcherProvider
import jp.speakbuddy.edisonandroidexercise.local.data.FactEntity
import jp.speakbuddy.edisonandroidexercise.local.data.FactEntitySerializer
import jp.speakbuddy.edisonandroidexercise.local.datasource.FactLocalDataSource
import jp.speakbuddy.edisonandroidexercise.local.datasource.FactLocalProtoDataSourceImpl
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {
    @Provides
    @Singleton
    fun provideFactDataStore(
        @ApplicationContext context: Context,
        dispatcherProvider: DispatcherProvider,
        factEntitySerializer: FactEntitySerializer,
    ): DataStore<FactEntity> {
        return DataStoreFactory.create(
            serializer = factEntitySerializer,
            scope = CoroutineScope(dispatcherProvider.io),
            migrations = listOf(),
        ) {
            context.dataStoreFile("fact_data_store.json")
        }
    }

    @Provides
    @Singleton
    fun provideFactLocalDataSource(factLocalProtoDataSourceImpl: FactLocalProtoDataSourceImpl): FactLocalDataSource {
        return factLocalProtoDataSourceImpl
    }
}