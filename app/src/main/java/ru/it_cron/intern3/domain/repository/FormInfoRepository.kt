package ru.it_cron.intern3.domain.repository

import ru.it_cron.intern3.domain.models.FormInfo
import ru.it_cron.intern3.domain.models.FormResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface FormInfoRepository {
    suspend fun postForm(formInfo: FormInfo): Flow<FormResponse?>

    suspend fun postFormWithFiles(
        formInfo: FormInfo,
        listFiles: List<MultipartBody.Part>
    ): Flow<FormResponse?>
}