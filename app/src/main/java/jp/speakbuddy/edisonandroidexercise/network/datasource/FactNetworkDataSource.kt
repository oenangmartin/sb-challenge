package jp.speakbuddy.edisonandroidexercise.network.datasource

import jp.speakbuddy.edisonandroidexercise.network.data.FactResponse

interface FactNetworkDataSource {
    suspend fun getFact(): Result<FactResponse>
}