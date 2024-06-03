package ru.it_cron.intern3.presentation.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.it_cron.intern3.domain.models.Requisite
import ru.it_cron.intern3.domain.usecase.GetListRequisiteUseCase
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