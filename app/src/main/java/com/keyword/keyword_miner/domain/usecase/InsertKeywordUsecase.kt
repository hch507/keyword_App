package com.keyword.keyword_miner.domain.usecase

import com.keyword.keyword_miner.data.dto.KeywordSaveModel
import com.keyword.keyword_miner.domain.repository.RoomRepository
import javax.inject.Inject

class InsertKeywordUsecase @Inject constructor(
    private var roomRepository: RoomRepository
){
    operator suspend fun invoke(saveData: KeywordSaveModel){
        roomRepository.InsertData(saveData)
    }
}