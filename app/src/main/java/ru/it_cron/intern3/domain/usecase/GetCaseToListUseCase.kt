package ru.it_cron.intern3.domain.usecase

import ru.it_cron.intern3.domain.models.Case
import ru.it_cron.intern3.domain.repository.CaseRepository
import kotlinx.coroutines.flow.Flow

class GetCaseToListUseCase(private val repository: CaseRepository) {

    fun getCaseToList(): Flow<List<Case>> {
        return repository.getCaseToList()
    }
}