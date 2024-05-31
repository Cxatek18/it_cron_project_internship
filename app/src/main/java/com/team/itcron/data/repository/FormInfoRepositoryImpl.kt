package com.team.itcron.data.repository

import com.team.itcron.data.network.ApiService
import com.team.itcron.domain.models.FormInfo
import com.team.itcron.domain.models.FormResponse
import com.team.itcron.domain.repository.FormInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

class FormInfoRepositoryImpl(val apiService: ApiService) : FormInfoRepository {

    private val formResponse = MutableStateFlow<FormResponse?>(null)
    override suspend fun postForm(formInfo: FormInfo): Flow<FormResponse?> =
        flow<FormResponse?> {
            val services = formInfo.services
                .toRequestBody("text/plain;charset=utf-8".toMediaType())
            val budget = formInfo.budget
                .toRequestBody("text/plain;charset=utf-8".toMediaType())
            val description = formInfo.description
                .toRequestBody("text/plain;charset=utf-8".toMediaType())
            val contactName = formInfo.contactName
                .toRequestBody("text/plain;charset=utf-8".toMediaType())
            val contactCompany = formInfo.contactCompany
                .toRequestBody("text/plain;charset=utf-8".toMediaType())
            val contactEmail = formInfo.contactEmail
                .toRequestBody("text/plain;charset=utf-8".toMediaType())
            val contactPhone = formInfo.contactPhone
                .toRequestBody("text/plain;charset=utf-8".toMediaType())
            val requestFrom = formInfo.requestFrom
                .toRequestBody("text/plain;charset=utf-8".toMediaType())
            formResponse.value = apiService.postForm(
                services,
                budget,
                description,
                contactName,
                contactCompany,
                contactEmail,
                contactPhone,
                requestFrom,
            ).body()
            formResponse.collect { emit(it) }
        }

    override suspend fun postFormWithFiles(
        formInfo: FormInfo,
        listFiles: List<MultipartBody.Part>
    ): Flow<FormResponse?> =
        flow<FormResponse?> {
            val services = formInfo.services
                .toRequestBody("text/plain;charset=utf-8".toMediaType())
            val budget = formInfo.budget
                .toRequestBody("text/plain;charset=utf-8".toMediaType())
            val description = formInfo.description
                .toRequestBody("text/plain;charset=utf-8".toMediaType())
            val contactName = formInfo.contactName
                .toRequestBody("text/plain;charset=utf-8".toMediaType())
            val contactCompany = formInfo.contactCompany
                .toRequestBody("text/plain;charset=utf-8".toMediaType())
            val contactEmail = formInfo.contactEmail
                .toRequestBody("text/plain;charset=utf-8".toMediaType())
            val contactPhone = formInfo.contactPhone
                .toRequestBody("text/plain;charset=utf-8".toMediaType())
            val requestFrom = formInfo.requestFrom
                .toRequestBody("text/plain;charset=utf-8".toMediaType())
            formResponse.value = apiService.postFormWithFiles(
                services,
                budget,
                description,
                contactName,
                contactCompany,
                contactEmail,
                contactPhone,
                requestFrom,
                listFiles
            ).body()
            formResponse.collect { emit(it) }
        }
}