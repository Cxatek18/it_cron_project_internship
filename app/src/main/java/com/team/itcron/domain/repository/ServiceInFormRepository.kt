package com.team.itcron.domain.repository

import com.team.itcron.domain.models.ServiceInForm
import kotlinx.coroutines.flow.Flow

interface ServiceInFormRepository {

    fun createListServices(): Flow<List<ServiceInForm>>

    fun setSelectionService(serviceInForm: ServiceInForm)

    fun formingListActiveService(): Flow<List<ServiceInForm>>
}