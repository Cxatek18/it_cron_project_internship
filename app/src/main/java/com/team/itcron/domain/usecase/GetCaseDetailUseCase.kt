package com.team.itcron.domain.usecase

import com.team.itcron.domain.models.CaseDetail
import com.team.itcron.domain.repository.CaseDetailRepository
import kotlinx.coroutines.flow.Flow

class GetCaseDetailUseCase(private val repository: CaseDetailRepository) {

    suspend fun getCaseDetail(caseId: String): Flow<CaseDetail?> {
        return repository.getCaseDetail(caseId)
    }
}