package com.team.itcron.presentation.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team.itcron.domain.models.CaseToList
import com.team.itcron.domain.models.FilterToCategoryList
import com.team.itcron.domain.usecase.GetFilterToCategoryListUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ListFilterViewModel(
    val getFilterToCategoryListUseCase: GetFilterToCategoryListUseCase
): ViewModel() {

    private val _filterToCategoryList = MutableStateFlow<FilterToCategoryList?>(null)
    val filterToCategoryList: StateFlow<FilterToCategoryList?> = _filterToCategoryList.asStateFlow()

    private val _isOnline = Channel<Boolean>(Channel.CONFLATED)
    val isOnline = _isOnline.consumeAsFlow()

    private val _dataLoadIsError = MutableStateFlow<Boolean>(true)
    val dataLoadIsError: StateFlow<Boolean> = _dataLoadIsError.asStateFlow()

    fun getFilterToCategoryList() {
        viewModelScope.launch {
            try {
                val requestFilterToCategoryList = getFilterToCategoryListUseCase
                    .getFilterToCategoryList()
                if (requestFilterToCategoryList.serverError == null) {
                    _dataLoadIsError.value = false
                    _filterToCategoryList.value = requestFilterToCategoryList
                } else {
                    _dataLoadIsError.value = true
                }
            } catch (e: UnknownHostException) {
                _isOnline.send(false)
            } catch (e: SocketTimeoutException) {
                _isOnline.send(false)
            }
        }
    }
}