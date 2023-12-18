package com.keyword.keyword_miner.domain.usecase

import android.util.Log
import com.keyword.keyword_miner.domain.Model.monthRadioData.MonthRatioDataModel
import com.keyword.keyword_miner.domain.repository.KeywordRepository
import javax.inject.Inject

class GetMonthRatioUsecase @Inject constructor(
    private val keywordRepository: KeywordRepository
) {
    operator suspend fun invoke(searchTerm : String) : List<MonthRatioDataModel>? =keywordRepository.getMonthRatio(searchTerm)
}