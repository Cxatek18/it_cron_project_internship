package ru.it_cron.intern3.domain.repository

import ru.it_cron.intern3.domain.models.ServiceInForm
import kotlinx.coroutines.flow.Flow

interface ServiceInFormRepository {

    fun createListServices(): Flow<List<ServiceInForm>>

    fun setSelectionService(serviceInForm: ServiceInForm)

    fun formingListActiveService(): Flow<List<ServiceInForm>>
}