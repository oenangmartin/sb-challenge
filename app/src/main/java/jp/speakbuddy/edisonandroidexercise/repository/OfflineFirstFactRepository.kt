package jp.speakbuddy.edisonandroidexercise.repository

import jp.speakbuddy.edisonandroidexercise.common.DispatcherProvider
import jp.speakbuddy.edisonandroidexercise.local.data.FactEntity
import jp.speakbuddy.edisonandroidexercise.local.datasource.FactLocalDataSource
import jp.speakbuddy.edisonandroidexercise.network.data.FactResponse
import jp.speakbuddy.edisonandroidexercise.network.datasource.FactNetworkDataSource
import jp.speakbuddy.edisonandroidexercise.repository.model.FactModel
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OfflineFirstFactRepository @Inject constructor(
    private val factNetworkDataSource: FactNetworkDataSource,
    private val factLocalDataSource: FactLocalDataSource,
    private val dispatcherProvider: DispatcherProvider,
) : FactRepository {
    override suspend fun getFact(forceFetch: Boolean): Result<FactModel> {
        return withContext(dispatcherProvider.io) {
            if (forceFetch) {
                fetchFromRemote()
            } else {
                val localFact = factLocalDataSource.getFact()
                localFact?.let {
                    Result.success(it.toFactModel())
                } ?: fetchFromRemote()
            }
        }
    }

    private suspend fun fetchFromRemote(): Result<FactModel> {
        factNetworkDataSource.getFact().fold({
            val factEntity = factLocalDataSource.updateFact(factEntity = it.toFactEntity())
            return Result.success(factEntity.toFactModel())
        }, {
            it.printStackTrace()
            return Result.failure(it)
        })
    }
}

private fun FactEntity.toFactModel() = FactModel(
    fact = fact,
    length = length,
)

private fun FactResponse.toFactEntity(): FactEntity = FactEntity(
    fact = fact,
    length = length,
)
