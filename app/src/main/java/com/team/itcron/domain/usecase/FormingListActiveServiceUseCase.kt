package com.team.itcron.domain.usecase

import com.team.itcron.domain.models.ServiceInForm
import com.team.itcron.domain.repository.ServiceInFormRepository
import kotlinx.coroutines.flow.Flow

class FormingListActiveServiceUseCase(private val repository: ServiceInFormRepository) {

    fun formingListActiveService(): Flow<List<ServiceInForm>> {
        return repository.formingListActiveService()
    }
}