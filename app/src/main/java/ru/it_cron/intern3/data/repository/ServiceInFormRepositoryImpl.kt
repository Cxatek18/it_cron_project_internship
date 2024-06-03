package ru.it_cron.intern3.data.repository

import android.content.Context
import ru.it_cron.intern3.R
import ru.it_cron.intern3.domain.models.ServiceInForm
import ru.it_cron.intern3.domain.repository.ServiceInFormRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update

class ServiceInFormRepositoryImpl(val context: Context) : ServiceInFormRepository {

    private val servicesListInForm = MutableStateFlow<List<ServiceInForm>>(emptyList())

    private val servicesActiveListInForm = MutableStateFlow<List<ServiceInForm>>(emptyList())

    override fun createListServices(): Flow<List<ServiceInForm>> =
        flow<List<ServiceInForm>> {
            servicesListInForm.value = arrayListOf(
                ServiceInForm(
                    getStringService(R.string.text_ux_testing),
                    false
                ),
                ServiceInForm(
                    getStringService(R.string.text_mobile_application_design),
                    false
                ),
                ServiceInForm(
                    getStringService(R.string.text_web_interface_design),
                    false
                ),
                ServiceInForm(
                    getStringService(R.string.text_web_development_and_integration),
                    false
                ),
                ServiceInForm(
                    getStringService(R.string.text_mobile_application_development),
                    false
                ),
                ServiceInForm(
                    getStringService(R.string.text_strategy),
                    false
                ),
                ServiceInForm(
                    getStringService(R.string.text_creative),
                    false
                ),
                ServiceInForm(
                    getStringService(R.string.text_analytics),
                    false
                ),
                ServiceInForm(
                    getStringService(R.string.text_testing),
                    false
                ),
                ServiceInForm(
                    getStringService(R.string.text_other),
                    false
                ),
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

    private fun getStringService(idString: Int): String {
        return context.resources.getString(idString)
    }
}