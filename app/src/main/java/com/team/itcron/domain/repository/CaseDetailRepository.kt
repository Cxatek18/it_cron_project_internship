package com.team.itcron.domain.repository

import com.team.itcron.domain.models.CaseDetail
import kotlinx.coroutines.flow.Flow

interface CaseDetailRepository {
    suspend fun getCaseDetail(caseId: String): Flow<CaseDetail?>
}