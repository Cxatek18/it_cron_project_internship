package ru.it_cron.intern3.presentation.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.it_cron.intern3.domain.models.Case
import ru.it_cron.intern3.domain.models.Filter
import ru.it_cron.intern3.domain.usecase.AddFiltersToListActiveFilterUseCase
import ru.it_cron.intern3.domain.usecase.FilteringCasesUseCase
import ru.it_cron.intern3.domain.usecase.GetActiveFilterUseCase
import ru.it_cron.intern3.domain.usecase.GetCaseToListUseCase
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
    val filteringCasesUseCase: FilteringCasesUseCase,
    val getActiveFilterUseCase: GetActiveFilterUseCase,
    val addFiltersToListActiveFilterUseCase: AddFiltersToListActiveFilterUseCase
) : ViewModel() {

    private val _caseToList = MutableStateFlow<List<Case>>(emptyList())
    val caseToList: StateFlow<List<Case>> = _caseToList.asStateFlow()

    private val _isOnline = Channel<Boolean>(Channel.CONFLATED)
    val isOnline = _isOnline.receiveAsFlow()

    private val _dataLoadIsError = MutableStateFlow<Boolean>(true)
    val dataLoadIsError: StateFlow<Boolean> = _dataLoadIsError.asStateFlow()

    private val _filteringCaseToList = MutableStateFlow<List<Case>>(emptyList())
    val filteringCaseToList: StateFlow<List<Case>> = _filteringCaseToList.asStateFlow()

    private val _listActiveFilter = MutableStateFlow<List<Filter>>(emptyList())
    val listActiveFilter: StateFlow<List<Filter>> = _listActiveFilter.asStateFlow()

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

    fun filteringCases(filters: List<Filter>) {
        viewModelScope.launch {
            filteringCasesUseCase.filteringCases(filters)
                .collect { listCase ->
                    _filteringCaseToList.value = listCase
                }
        }
    }

    fun getActiveFilter() {
        viewModelScope.launch {
            getActiveFilterUseCase.getActiveFilter()
                .collect { listActiveFilter ->
                    _listActiveFilter.value = listActiveFilter
                }
        }
    }

    fun addFiltersToListActiveFilter(filters: List<Filter>) {
        viewModelScope.launch {
            addFiltersToListActiveFilterUseCase.addFiltersToListActiveFilter(filters)
            _listActiveFilter.value = filters
        }
    }
}