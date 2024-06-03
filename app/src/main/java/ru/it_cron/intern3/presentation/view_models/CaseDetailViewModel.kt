package ru.it_cron.intern3.presentation.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.it_cron.intern3.domain.models.Case
import ru.it_cron.intern3.domain.models.CaseDetail
import ru.it_cron.intern3.domain.usecase.GetCaseDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class CaseDetailViewModel(
    val getCaseDetailUseCase: GetCaseDetailUseCase
) : ViewModel() {

    private val _caseDetail = MutableStateFlow<CaseDetail?>(null)
    val caseDetail: StateFlow<CaseDetail?> = _caseDetail.asStateFlow()

    private val _caseDetailLoadIsError = MutableStateFlow<Boolean>(false)
    val caseDetailLoadIsError: StateFlow<Boolean> = _caseDetailLoadIsError.asStateFlow()

    private val _isOnline = MutableStateFlow<Boolean>(true)
    val isOnline: StateFlow<Boolean> = _isOnline.asStateFlow()

    private val _caseHttpException = MutableStateFlow<Boolean>(false)
    val caseHttpException: StateFlow<Boolean> = _caseHttpException.asStateFlow()

    private val _nextCase = MutableStateFlow<Case?>(null)
    val nextCase: StateFlow<Case?> = _nextCase.asStateFlow()

    fun getCaseDetail(caseId: String) {
        viewModelScope.launch {
            getCaseDetailUseCase.getCaseDetail(caseId)
                .catch {
                    when (it) {
                        is UnknownHostException,
                        is SocketTimeoutException -> _isOnline.value = false

                        is HttpException -> _caseHttpException.value = true
                        else -> {
                            _isOnline.value = true
                            _caseHttpException.value = false
                        }
                    }
                }
                .collect { caseDetail ->
                    if (caseDetail?.serverError == null) {
                        _caseDetail.value = caseDetail
                    } else {
                        _caseDetailLoadIsError.value = true
                    }
                }
        }
    }

    fun getNextCase(listCase: List<Case>, caseId: String) {
        viewModelScope.launch {
            val indexCase = listCase.indexOfFirst {
                it.id == caseId
            }
            if (indexCase == listCase.count() - 1) {
                _nextCase.value = listCase[0]
            } else {
                _nextCase.value = listCase[indexCase + 1]
            }
        }
    }
}