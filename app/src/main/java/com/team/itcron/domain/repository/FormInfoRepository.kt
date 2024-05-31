package com.team.itcron.domain.repository

import com.team.itcron.domain.models.FormInfo
import com.team.itcron.domain.models.FormResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface FormInfoRepository {
    suspend fun postForm(formInfo: FormInfo): Flow<FormResponse?>

    suspend fun postFormWithFiles(
        formInfo: FormInfo,
        listFiles: List<MultipartBody.Part>
    ): Flow<FormResponse?>
}