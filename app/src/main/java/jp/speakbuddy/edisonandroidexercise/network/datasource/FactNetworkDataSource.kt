package jp.speakbuddy.edisonandroidexercise.network.datasource

import jp.speakbuddy.edisonandroidexercise.data.FactResponse

interface FactNetworkDataSource {
    suspend fun getFact(): Result<FactResponse>
}