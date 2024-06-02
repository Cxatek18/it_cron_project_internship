package com.team.itcron.domain.usecase

import com.team.itcron.domain.models.FormInfo
import com.team.itcron.domain.models.FormResponse
import com.team.itcron.domain.repository.FormInfoRepository
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

class PostFormWithFilesUseCase(private val repository: FormInfoRepository) {

    suspend fun postFormWithFiles(
        formInfo: FormInfo,
        listFiles: List<MultipartBody.Part>
    ): Flow<FormResponse?> {
        return repository.postFormWithFiles(formInfo, listFiles)
    }
}