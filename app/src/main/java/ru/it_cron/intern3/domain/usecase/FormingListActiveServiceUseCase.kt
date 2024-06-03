package ru.it_cron.intern3.domain.usecase

import ru.it_cron.intern3.domain.models.ServiceInForm
import ru.it_cron.intern3.domain.repository.ServiceInFormRepository
import kotlinx.coroutines.flow.Flow

class FormingListActiveServiceUseCase(private val repository: ServiceInFormRepository) {

    fun formingListActiveService(): Flow<List<ServiceInForm>> {
        return repository.formingListActiveService()
    }
}