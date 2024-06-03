package ru.it_cron.intern3.domain.usecase

import ru.it_cron.intern3.domain.models.FormInfo
import ru.it_cron.intern3.domain.models.FormResponse
import ru.it_cron.intern3.domain.repository.FormInfoRepository
import kotlinx.coroutines.flow.Flow

class PostFormUseCase(private val repository: FormInfoRepository) {

    suspend fun postForm(formInfo: FormInfo): Flow<FormResponse?> {
        return repository.postForm(formInfo)
    }
}