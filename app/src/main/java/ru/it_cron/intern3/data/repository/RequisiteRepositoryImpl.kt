package ru.it_cron.intern3.data.repository

import android.content.Context
import ru.it_cron.intern3.R
import ru.it_cron.intern3.domain.models.Requisite
import ru.it_cron.intern3.domain.repository.RequisiteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow

class RequisiteRepositoryImpl(val context: Context) : RequisiteRepository {

    private val listRequisite = MutableStateFlow<List<Requisite>>(emptyList())

    override fun getListRequisite(): Flow<List<Requisite>> =
        flow<List<Requisite>> {
            listRequisite.value = arrayListOf(
                Requisite(
                    getStringRequisite(R.string.text_full_name),
                    getStringRequisite(R.string.text_OOO)
                ),
                Requisite(
                    getStringRequisite(R.string.text_abbreviated_name),
                    getStringRequisite(R.string.text_OOO_value)
                ),
                Requisite(
                    getStringRequisite(R.string.text_en_name),
                    getStringRequisite(R.string.text_en_name_value)
                ),
                Requisite(
                    getStringRequisite(R.string.text_legal_address),
                    getStringRequisite(R.string.text_legal_address_value)
                ),
                Requisite(
                    getStringRequisite(R.string.text_postal_address),
                    getStringRequisite(R.string.text_postal_address_value)
                ),
                Requisite(
                    getStringRequisite(R.string.text_INN),
                    getStringRequisite(R.string.text_INN_value)
                ),
                Requisite(
                    getStringRequisite(R.string.text_KPP),
                    getStringRequisite(R.string.text_KPP_value)
                ),
                Requisite(
                    getStringRequisite(R.string.text_OGRN),
                    getStringRequisite(R.string.text_OGRN_value)
                ),
                Requisite(
                    getStringRequisite(R.string.text_OKPO),
                    getStringRequisite(R.string.text_OKPO_value)
                ),
                Requisite(
                    getStringRequisite(R.string.text_R_C),
                    getStringRequisite(R.string.text_R_C_value)
                ),
                Requisite(
                    getStringRequisite(R.string.text_bank),
                    getStringRequisite(R.string.text_bank_value)
                ),
                Requisite(
                    getStringRequisite(R.string.text_BIK),
                    getStringRequisite(R.string.text_BIK_value)
                ),
                Requisite(
                    getStringRequisite(R.string.text_K_S),
                    getStringRequisite(R.string.text_K_S_value)
                ),
                Requisite(
                    getStringRequisite(R.string.text_general_manager),
                    getStringRequisite(R.string.text_name_director)
                ),
            )
            listRequisite.collect { emit(it) }
        }

    private fun getStringRequisite(idString: Int): String {
        return context.resources.getString(idString)
    }
}