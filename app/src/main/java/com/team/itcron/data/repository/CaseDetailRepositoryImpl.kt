package com.team.itcron.data.repository

import com.team.itcron.data.network.ApiService
import com.team.itcron.domain.models.CaseDetail
import com.team.itcron.domain.repository.CaseDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow

class CaseDetailRepositoryImpl(val apiService: ApiService) : CaseDetailRepository {

    private val caseDetailInfo = MutableStateFlow<CaseDetail?>(null)

    override suspend fun getCaseDetail(caseId: String): Flow<CaseDetail?> =
        flow<CaseDetail?> {
            if (caseDetailInfo.value == null) {
                caseDetailInfo.value = apiService.getCaseDetail(caseId)
            }
            if (caseDetailInfo.value?.data?.id != caseId) {
                caseDetailInfo.value = apiService.getCaseDetail(caseId)
            }
            caseDetailInfo.collect { emit(it) }
        }
}