package ru.it_cron.intern3.presentation.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.it_cron.intern3.domain.models.Filter
import ru.it_cron.intern3.domain.models.FilterItem
import ru.it_cron.intern3.domain.usecase.ClearFilterListUseCase
import ru.it_cron.intern3.domain.usecase.FormingListActiveFiltersUseCase
import ru.it_cron.intern3.domain.usecase.GetFilterToCategoryListUseCase
import ru.it_cron.intern3.domain.usecase.SetSelectionFilterUseCase
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

    private val _filterToCategoryList = MutableStateFlow<List<FilterItem>>(emptyList())
    val filterToCategoryList = _filterToCategoryList.asStateFlow()

    private val _isOnline = Channel<Boolean>(Channel.CONFLATED)
    val isOnline = _isOnline.receiveAsFlow()

    private val _dataLoadIsError = MutableStateFlow<Boolean>(true)
    val dataLoadIsError: StateFlow<Boolean> = _dataLoadIsError.asStateFlow()

    private val _canClear = MutableStateFlow<Boolean>(false)
    val canClear: StateFlow<Boolean> = _canClear.asStateFlow()

    private val _listActiveFilter = Channel<List<Filter>>(Channel.CONFLATED)
    val listActiveFilter = _listActiveFilter.receiveAsFlow()

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
                .collect { listCategoryFilter ->
                    _dataLoadIsError.value = false
                    val listFilterItem = mutableListOf<FilterItem>()
                    listCategoryFilter.forEach { categoryFilter ->
                        listFilterItem.add(
                            FilterItem.Category(
                                id = categoryFilter.id,
                                name = categoryFilter.name
                            )
                        )
                        categoryFilter.filters.forEach { filter ->
                            listFilterItem.add(
                                FilterItem.Filter(
                                    id = filter.id,
                                    name = filter.name,
                                    isActive = filter.isActive
                                )
                            )
                        }
                    }
                    _filterToCategoryList.value = listFilterItem
                }
        }
    }

    fun setSelectionFilter(filter: FilterItem.Filter) {
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
                    _listActiveFilter.send(it)
                }
        }
    }

    fun determiningPossibilityCleaning() {
        var countActiveFilters = 0
        _filterToCategoryList.value.forEach { filterItem ->
            if (filterItem is FilterItem.Filter) {
                if (filterItem.isActive) {
                    countActiveFilters += 1
                }
            }
        }
        _canClear.value = countActiveFilters > 0
    }
}