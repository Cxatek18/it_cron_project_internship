package com.team.itcron.presentation.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team.itcron.domain.models.Requisite
import com.team.itcron.domain.usecase.GetListRequisiteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ContactViewModel(
    val getListRequisiteUseCase: GetListRequisiteUseCase
) : ViewModel() {

    private val _listRequisite = MutableStateFlow<List<Requisite>>(emptyList())
    val listRequisite: StateFlow<List<Requisite>> = _listRequisite.asStateFlow()

    fun getListRequisite() {
        viewModelScope.launch {
            getListRequisiteUseCase.getListRequisite()
                .collect {
                    _listRequisite.value = it
                }
        }
    }
}