package jp.speakbuddy.edisonandroidexercise.local.datasource

import jp.speakbuddy.edisonandroidexercise.local.data.FactEntity

interface FactLocalDataSource {
    suspend fun getFact(): FactEntity
    suspend fun updateFact(factEntity: FactEntity)
}