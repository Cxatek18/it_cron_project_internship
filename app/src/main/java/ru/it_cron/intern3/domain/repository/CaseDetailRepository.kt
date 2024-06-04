package ru.it_cron.intern3.domain.repository

import ru.it_cron.intern3.domain.models.CaseDetail
import kotlinx.coroutines.flow.Flow

interface CaseDetailRepository {
    suspend fun getCaseDetail(caseId: String): Flow<CaseDetail?>
}