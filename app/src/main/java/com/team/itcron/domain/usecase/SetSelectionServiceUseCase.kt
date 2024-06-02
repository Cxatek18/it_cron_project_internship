package com.team.itcron.domain.usecase

import com.team.itcron.domain.models.ServiceInForm
import com.team.itcron.domain.repository.ServiceInFormRepository

class SetSelectionServiceUseCase(private val repository: ServiceInFormRepository) {

    fun setSelectionService(serviceInForm: ServiceInForm) {
        repository.setSelectionService(serviceInForm)
    }
}