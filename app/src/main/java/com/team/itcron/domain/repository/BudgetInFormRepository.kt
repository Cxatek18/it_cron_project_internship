package com.team.itcron.domain.repository

import com.team.itcron.domain.models.BudgetInForm
import kotlinx.coroutines.flow.Flow

interface BudgetInFormRepository {

    fun createListBudgets(): Flow<List<BudgetInForm>>

    fun setSelectionBudget(budgetInForm: BudgetInForm)

    fun getActiveBudget(): Flow<BudgetInForm?>
}