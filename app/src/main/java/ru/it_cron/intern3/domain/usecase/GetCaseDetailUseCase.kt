package ru.it_cron.intern3.domain.usecase

import ru.it_cron.intern3.domain.models.CaseDetail
import ru.it_cron.intern3.domain.repository.CaseDetailRepository
import kotlinx.coroutines.flow.Flow

class GetCaseDetailUseCase(private val repository: CaseDetailRepository) {

    suspend fun getCaseDetail(caseId: String): Flow<CaseDetail?> {
        return repository.getCaseDetail(caseId)
    }
}