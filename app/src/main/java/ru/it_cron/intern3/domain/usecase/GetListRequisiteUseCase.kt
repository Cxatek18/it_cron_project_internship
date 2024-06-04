package ru.it_cron.intern3.domain.usecase

import ru.it_cron.intern3.domain.models.Requisite
import ru.it_cron.intern3.domain.repository.RequisiteRepository
import kotlinx.coroutines.flow.Flow

class GetListRequisiteUseCase(private val repository: RequisiteRepository) {

    fun getListRequisite(): Flow<List<Requisite>> {
        return repository.getListRequisite()
    }
}