package com.team.itcron.data.repository

import com.team.itcron.domain.models.BudgetInForm
import com.team.itcron.domain.repository.BudgetInFormRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update

class BudgetInFormRepositoryImpl : BudgetInFormRepository {

    private val budgetListInForm = MutableStateFlow<List<BudgetInForm>>(emptyList())

    private val activeBudget = MutableStateFlow<BudgetInForm?>(null)

    override fun createListBudgets(): Flow<List<BudgetInForm>> =
        flow<List<BudgetInForm>> {
            budgetListInForm.value = arrayListOf(
                BudgetInForm("< 500 тыс.р.", false),
                BudgetInForm("0.5 - 1 млн.р.", false),
                BudgetInForm("1 - 3 млн.р.", false),
                BudgetInForm("3 - 5 млн.р.", false),
                BudgetInForm("5 - 10 млн.р.", false),
                BudgetInForm("> 10 млн.р.", false),
            )
            budgetListInForm.collect { emit(it) }
        }

    override fun setSelectionBudget(budgetInForm: BudgetInForm) {
        budgetListInForm.update { listBudgetInForm ->
            val listBudgetsNoActive = mutableListOf<BudgetInForm>()
            listBudgetInForm.forEach {
                listBudgetsNoActive.add(
                    it.copy(title = it.title, isActive = false)
                )
            }
            var listNew = listOf<BudgetInForm>()
            val idx = listBudgetsNoActive.indexOfFirst {
                it.title == budgetInForm.title
            }
            if (idx >= 0) {
                listBudgetsNoActive[idx] = listBudgetsNoActive[idx]
                    .copy(isActive = !budgetInForm.isActive)
                listNew = listBudgetsNoActive.toList()
            }
            listNew
        }
    }

    override fun getActiveBudget(): Flow<BudgetInForm?> =
        flow<BudgetInForm?> {
            activeBudget.value = budgetListInForm.value.find {
                it.isActive
            }
            activeBudget.collect { emit(it) }
        }
}