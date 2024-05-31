package com.team.itcron.data.repository

import com.team.itcron.domain.models.ServiceInForm
import com.team.itcron.domain.repository.ServiceInFormRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update

class ServiceInFormRepositoryImpl : ServiceInFormRepository {

    private val servicesListInForm = MutableStateFlow<List<ServiceInForm>>(emptyList())

    private val servicesActiveListInForm = MutableStateFlow<List<ServiceInForm>>(emptyList())

    override fun createListServices(): Flow<List<ServiceInForm>> =
        flow<List<ServiceInForm>> {
            servicesListInForm.value = arrayListOf(
                ServiceInForm("UX-тестирование", false),
                ServiceInForm("Дизайн моб. приложения", false),
                ServiceInForm("Дизайн веб-интерфейса", false),
                ServiceInForm("Веб-разработка и интеграции", false),
                ServiceInForm("Разработка моб. приложений", false),
                ServiceInForm("Стратегия", false),
                ServiceInForm("Креатив", false),
                ServiceInForm("Аналитика", false),
                ServiceInForm("Тестирование", false),
                ServiceInForm("Другое", false),
            )
            servicesListInForm.collect { emit(it) }
        }

    override fun setSelectionService(serviceInForm: ServiceInForm) {
        servicesListInForm.update { listServicesInForm ->
            val list = listServicesInForm.toMutableList()
            var listNew = listOf<ServiceInForm>()
            val idx = list.indexOfFirst {
                it.title == serviceInForm.title
            }
            if (idx >= 0) {
                list[idx] = list[idx].copy(isActive = !serviceInForm.isActive)
                listNew = list.toList()
            }
            listNew
        }
    }

    override fun formingListActiveService(): Flow<List<ServiceInForm>> =
        flow<List<ServiceInForm>> {
            val listActiveServices = mutableListOf<ServiceInForm>()
            listActiveServices.addAll(
                servicesListInForm.value.filter {
                    it.isActive
                }
            )
            servicesActiveListInForm.value = listActiveServices.toList()
            servicesActiveListInForm.collect { emit(it) }
        }
}