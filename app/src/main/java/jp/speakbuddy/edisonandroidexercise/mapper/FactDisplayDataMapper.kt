package jp.speakbuddy.edisonandroidexercise.mapper

import jp.speakbuddy.edisonandroidexercise.repository.model.FactModel
import jp.speakbuddy.edisonandroidexercise.ui.common.image.ImageSource
import jp.speakbuddy.edisonandroidexercise.ui.fact.FactDisplayData
import javax.inject.Inject

internal const val FACT_LENGTH_VISIBILITY_THRESHOLD = 100
internal const val MULTIPLE_CATS = "cats"

class FactDisplayDataMapper @Inject constructor() {
    fun map(factModel: FactModel): FactDisplayData {
        return FactDisplayData(
            headerImage = ImageSource.Url("https://png.pngtree.com/png-clipart/20220626/original/pngtree-pink-cute-cat-icon-animal-png-yuri-png-image_8188672.png"),
            title = if (factModel.length > FACT_LENGTH_VISIBILITY_THRESHOLD) {
                "A Very Long Fact"
            } else {
                "Fact"
            },
            fact = factModel.fact,
            showMultipleCats = factModel.fact.contains(MULTIPLE_CATS, ignoreCase = true)
        )
    }
}