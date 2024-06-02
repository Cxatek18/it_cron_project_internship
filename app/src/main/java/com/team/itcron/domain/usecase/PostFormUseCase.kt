package com.team.itcron.domain.usecase

import com.team.itcron.domain.models.FormInfo
import com.team.itcron.domain.models.FormResponse
import com.team.itcron.domain.repository.FormInfoRepository
import kotlinx.coroutines.flow.Flow

class PostFormUseCase(private val repository: FormInfoRepository) {

    suspend fun postForm(formInfo: FormInfo): Flow<FormResponse?> {
        return repository.postForm(formInfo)
    }
}