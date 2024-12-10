package jp.speakbuddy.edisonandroidexercise.network.datasource

import jp.speakbuddy.edisonandroidexercise.network.FactService
import jp.speakbuddy.edisonandroidexercise.network.data.FactResponse
import javax.inject.Inject

class FactNetworkDataSourceImpl @Inject constructor(
    private val factService: FactService,
): FactNetworkDataSource {
    override suspend fun getFact(): Result<FactResponse> {
        return runCatching { factService.getFact() }
    }
}