package jp.speakbuddy.edisonandroidexercise.repository

import jp.speakbuddy.edisonandroidexercise.repository.model.FactModel

interface FactRepository {
    suspend fun getFact(forceFetch: Boolean): Result<FactModel>
}