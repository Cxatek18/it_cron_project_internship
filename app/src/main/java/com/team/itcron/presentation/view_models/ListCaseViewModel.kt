package com.team.itcron.presentation.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team.itcron.domain.models.CaseToList
import com.team.itcron.domain.usecase.GetCaseToListUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ListCaseViewModel(
    val getCaseToListUseCase: GetCaseToListUseCase
) : ViewModel() {

    private val _caseToList = MutableStateFlow<CaseToList?>(null)
    val caseToList: StateFlow<CaseToList?> = _caseToList.asStateFlow()

    private val _isOnline = Channel<Boolean>(Channel.CONFLATED)
    val isOnline = _isOnline.consumeAsFlow()

    private val _dataLoadIsError = MutableStateFlow<Boolean>(true)
    val dataLoadIsError: StateFlow<Boolean> = _dataLoadIsError.asStateFlow()

    fun getCaseToList() {
        viewModelScope.launch {
            try {
                val requestCaseToList = getCaseToListUseCase.getCaseToList()
                if (requestCaseToList.serverError == null) {
                    _dataLoadIsError.value = false
                    _caseToList.value = requestCaseToList
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