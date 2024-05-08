package com.team.itcron.presentation.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team.itcron.domain.models.Case
import com.team.itcron.domain.models.Filter
import com.team.itcron.domain.usecase.FilteringCasesUseCase
import com.team.itcron.domain.usecase.GetCaseToListUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ListCaseViewModel(
    val getCaseToListUseCase: GetCaseToListUseCase,
    val filteringCasesUseCase: FilteringCasesUseCase
) : ViewModel() {

    private val _caseToList = MutableStateFlow<List<Case>>(emptyList())
    val caseToList: StateFlow<List<Case>> = _caseToList.asStateFlow()

    private val _isOnline = Channel<Boolean>(Channel.CONFLATED)
    val isOnline = _isOnline.receiveAsFlow()

    private val _dataLoadIsError = MutableStateFlow<Boolean>(true)
    val dataLoadIsError: StateFlow<Boolean> = _dataLoadIsError.asStateFlow()

    private val _filteringCaseToList = MutableStateFlow<List<Case>>(emptyList())
    val filteringCaseToList: StateFlow<List<Case>> = _filteringCaseToList.asStateFlow()

    fun getCaseToList() {
        viewModelScope.launch {
            getCaseToListUseCase.getCaseToList()
                .catch {
                    when (it) {
                        is UnknownHostException,
                        is SocketTimeoutException -> _isOnline.send(false)

                        else -> _dataLoadIsError.value = true
                    }
                }
                .collect {
                    _dataLoadIsError.value = false
                    _caseToList.value = it
                }
        }
    }

    fun filteringCasesUseCase(filters: List<Filter>) {
        viewModelScope.launch {
            filteringCasesUseCase.filteringCases(filters)
                .collect { listCase ->
                    _filteringCaseToList.value = listCase
                }
        }
    }
}