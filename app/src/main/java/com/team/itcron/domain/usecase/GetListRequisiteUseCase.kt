package com.team.itcron.domain.usecase

import com.team.itcron.domain.models.Requisite
import com.team.itcron.domain.repository.RequisiteRepository
import kotlinx.coroutines.flow.Flow

class GetListRequisiteUseCase(private val repository: RequisiteRepository) {

    fun getListRequisite(): Flow<List<Requisite>> {
        return repository.getListRequisite()
    }
}