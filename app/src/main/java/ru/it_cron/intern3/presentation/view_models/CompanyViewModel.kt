package ru.it_cron.intern3.presentation.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.it_cron.intern3.domain.models.ReviewInfo
import ru.it_cron.intern3.domain.usecase.GetListReviewUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class CompanyViewModel(
    val getListReviewUseCase: GetListReviewUseCase
) : ViewModel() {

    private val _reviewInfo = MutableStateFlow<ReviewInfo?>(null)
    val reviewInfo: StateFlow<ReviewInfo?> = _reviewInfo.asStateFlow()

    private val _reviewInfoLoadIsError = MutableStateFlow<Boolean>(false)
    val reviewInfoLoadIsError: StateFlow<Boolean> = _reviewInfoLoadIsError.asStateFlow()

    private val _isOnline = Channel<Boolean>(Channel.CONFLATED)
    val isOnline = _isOnline.receiveAsFlow()

    private val _reviewHttpException = MutableStateFlow<Boolean>(false)
    val reviewHttpException: StateFlow<Boolean> = _reviewHttpException.asStateFlow()

    private val _dataLoadIsError = MutableStateFlow<Boolean>(true)
    val dataLoadIsError: StateFlow<Boolean> = _dataLoadIsError.asStateFlow()

    fun getReviewInfo() {
        viewModelScope.launch {
            getListReviewUseCase.getListReview()
                .catch {
                    when (it) {
                        is UnknownHostException,
                        is SocketTimeoutException -> _isOnline.send(false)

                        is HttpException -> _reviewHttpException.value = true
                        else -> _dataLoadIsError.value = true
                    }
                }
                .collect { reviewInfo ->
                    if (reviewInfo?.serverError == null) {
                        _reviewInfo.value = reviewInfo
                    } else {
                        _reviewInfoLoadIsError.value = true
                    }
                }
        }
    }
}