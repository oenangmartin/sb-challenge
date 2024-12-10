package jp.speakbuddy.edisonandroidexercise.local.datasource

import androidx.datastore.core.DataStore
import jp.speakbuddy.edisonandroidexercise.local.data.FactEntity
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class FactLocalProtoDataSourceImpl @Inject constructor(
    private val factDataStore: DataStore<FactEntity>,
): FactLocalDataSource {
    override suspend fun getFact(): FactEntity = factDataStore.data.firstOrNull() ?: FactEntity()

    override suspend fun updateFact(factEntity: FactEntity) {
        factDataStore.updateData { factEntity }
    }
}