package ru.it_cron.intern3.data.repository

import android.content.Context
import ru.it_cron.intern3.R
import ru.it_cron.intern3.domain.models.BudgetInForm
import ru.it_cron.intern3.domain.repository.BudgetInFormRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class BudgetInFormRepositoryImpl(val context: Context) : BudgetInFormRepository {

    private val budgetListInForm = MutableStateFlow<List<BudgetInForm>>(emptyList())

    override fun createListBudgets(): Flow<List<BudgetInForm>> =
        flow<List<BudgetInForm>> {
            budgetListInForm.value = arrayListOf(
                BudgetInForm(
                    "< ${getStringBudget(R.string.text_less_than_500_thousand)}",
                    false
                ),
                BudgetInForm(
                    getStringBudget(R.string.text_from_500_thousand_to_1_million),
                    false
                ),
                BudgetInForm(
                    getStringBudget(R.string.text_from_1_million_to_3_million),
                    false
                ),
                BudgetInForm(
                    getStringBudget(R.string.text_from_3_million_to_5_million),
                    false
                ),
                BudgetInForm(
                    getStringBudget(R.string.text_from_5_million_to_10_million),
                    false
                ),
                BudgetInForm(
                    "> ${getStringBudget(R.string.text_more_than_10_million)}",
                    false
                ),
            )
            budgetListInForm.collect { emit(it) }
        }

    override fun setSelectionBudget(budgetInForm: BudgetInForm) {
        budgetListInForm.update { listBudgetInForm ->
            listBudgetInForm.map {
                val isActive = if (
                    it.title == budgetInForm.title
                ) !budgetInForm.isActive else false
                it.copy(isActive = isActive)
            }
        }
    }

    override fun getActiveBudget(): Flow<BudgetInForm?> {
        return budgetListInForm.map { list -> list.find { it.isActive } }
    }

    private fun getStringBudget(idString: Int): String {
        return context.resources.getString(idString)
    }
}