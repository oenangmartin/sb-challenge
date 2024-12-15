package jp.speakbuddy.edisonandroidexercise.mapper

import android.content.Context
import jp.speakbuddy.edisonandroidexercise.R
import jp.speakbuddy.edisonandroidexercise.repository.model.FactModel
import jp.speakbuddy.edisonandroidexercise.ui.common.image.ImageSource
import jp.speakbuddy.edisonandroidexercise.ui.fact.FactDisplayData
import javax.inject.Inject

internal const val FACT_LENGTH_VISIBILITY_THRESHOLD = 100

class FactDisplayDataMapper @Inject constructor(
    private val context: Context
) {
    private val multipleCatIdentifier by lazy { context.getString(R.string.multiple_cat_identifier) }
    private val longFactTitle by lazy { context.getString(R.string.long_fact_title) }
    private val factTitle by lazy { context.getString(R.string.fact_title) }

    fun map(factModel: FactModel): FactDisplayData {
        return FactDisplayData(
            headerImage = ImageSource.Url("https://png.pngtree.com/png-clipart/20220626/original/pngtree-pink-cute-cat-icon-animal-png-yuri-png-image_8188672.png"),
            title = if (factModel.length > FACT_LENGTH_VISIBILITY_THRESHOLD) {
                String.format(longFactTitle, factModel.length)
            } else {
                factTitle
            },
            fact = factModel.fact,
            showMultipleCats = factModel.fact.contains(multipleCatIdentifier, ignoreCase = true)
        )
    }
}