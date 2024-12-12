package jp.speakbuddy.edisonandroidexercise.mapper

import jp.speakbuddy.edisonandroidexercise.repository.model.FactModel
import jp.speakbuddy.edisonandroidexercise.ui.fact.FactDisplayData
import javax.inject.Inject

internal const val FACT_LENGTH_VISIBILITY_THRESHOLD = 100
internal const val MULTIPLE_CATS = "cats"

class FactDisplayDataMapper @Inject constructor() {
    fun map(factModel: FactModel): FactDisplayData {
        return FactDisplayData(
            fact = factModel.fact,
            length = if (factModel.length > FACT_LENGTH_VISIBILITY_THRESHOLD) {
                "Length > 100"
            } else {
                null
            },
            showMultipleCats = factModel.fact.contains(MULTIPLE_CATS, ignoreCase = true)
        )
    }
}