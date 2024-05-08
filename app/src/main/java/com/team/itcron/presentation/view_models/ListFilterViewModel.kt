package com.team.itcron.presentation.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team.itcron.domain.models.CategoryFilter
import com.team.itcron.domain.models.Filter
import com.team.itcron.domain.usecase.ClearFilterListUseCase
import com.team.itcron.domain.usecase.FormingListActiveFiltersUseCase
import com.team.itcron.domain.usecase.GetFilterToCategoryListUseCase
import com.team.itcron.domain.usecase.SetSelectionFilterUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ListFilterViewModel(
    val getFilterToCategoryListUseCase: GetFilterToCategoryListUseCase,
    val setSelectionFilterUseCase: SetSelectionFilterUseCase,
    val clearFilterListUseCase: ClearFilterListUseCase,
    val formingListActiveFiltersUseCase: FormingListActiveFiltersUseCase
) : ViewModel() {

    private val _filterToCategoryList = MutableStateFlow<List<CategoryFilter>>(emptyList())
    val filterToCategoryList = _filterToCategoryList.asStateFlow()

    private val _isOnline = Channel<Boolean>(Channel.CONFLATED)
    val isOnline = _isOnline.receiveAsFlow()

    private val _dataLoadIsError = MutableStateFlow<Boolean>(true)
    val dataLoadIsError: StateFlow<Boolean> = _dataLoadIsError.asStateFlow()

    private val _listActiveFilter = MutableStateFlow<List<Filter>>(emptyList())
    val listActiveFilter = _listActiveFilter.asStateFlow()

    fun getFilterToCategoryList() {
        viewModelScope.launch {
            getFilterToCategoryListUseCase.getFilterToCategoryList()
                .catch {
                    when (it) {
                        is UnknownHostException,
                        is SocketTimeoutException -> _isOnline.send(false)

                        else -> _dataLoadIsError.value = true
                    }
                }
                .collect {
                    _dataLoadIsError.value = false
                    _filterToCategoryList.value = it
                }
        }
    }

    fun setSelectionFilter(filter: Filter) {
        viewModelScope.launch {
            setSelectionFilterUseCase.setSelectionFilter(filter)
        }
    }

    fun clearFilterList() {
        viewModelScope.launch {
            clearFilterListUseCase.clearFilterList()
        }
    }

    fun formingListActiveFilters() {
        viewModelScope.launch {
            formingListActiveFiltersUseCase.formingListActiveFilters()
                .collect {
                    _listActiveFilter.value = it
                }
        }
    }
}