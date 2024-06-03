package ru.it_cron.intern3.domain.usecase

import ru.it_cron.intern3.domain.models.FormInfo
import ru.it_cron.intern3.domain.models.FormResponse
import ru.it_cron.intern3.domain.repository.FormInfoRepository
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