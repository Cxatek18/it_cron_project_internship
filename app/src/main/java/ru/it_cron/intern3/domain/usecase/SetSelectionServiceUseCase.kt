package ru.it_cron.intern3.domain.usecase

import ru.it_cron.intern3.domain.models.ServiceInForm
import ru.it_cron.intern3.domain.repository.ServiceInFormRepository

class SetSelectionServiceUseCase(private val repository: ServiceInFormRepository) {

    fun setSelectionService(serviceInForm: ServiceInForm) {
        repository.setSelectionService(serviceInForm)
    }
}