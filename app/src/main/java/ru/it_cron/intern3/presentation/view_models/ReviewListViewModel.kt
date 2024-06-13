package ru.it_cron.intern3.presentation.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.it_cron.intern3.domain.models.Review
import ru.it_cron.intern3.domain.models.ReviewInfo
import ru.it_cron.intern3.domain.usecase.GetFirstReviewsUseCase
import ru.it_cron.intern3.domain.usecase.GetListReviewUseCase
import ru.it_cron.intern3.domain.usecase.GetMoreReviewUseCase
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ReviewListViewModel(
    val getListReviewUseCase: GetListReviewUseCase,
    val getFirstReviewsUseCase: GetFirstReviewsUseCase,
    val getMoreReviewUseCase: GetMoreReviewUseCase
) : ViewModel() {

    private val _reviewInfo = MutableStateFlow<ReviewInfo?>(null)
    val reviewInfo: StateFlow<ReviewInfo?> = _reviewInfo.asStateFlow()

    private val _isOnline = Channel<Boolean>(Channel.CONFLATED)
    val isOnline = _isOnline.receiveAsFlow()

    private val _reviewList = MutableStateFlow<List<Review>>(emptyList())
    val reviewList: StateFlow<List<Review>> = _reviewList.asStateFlow()

    private val _isNoReviews = MutableStateFlow<Boolean>(true)
    val isNoReviews: StateFlow<Boolean> = _isNoReviews.asStateFlow()

    fun getReviewInfo() {
        viewModelScope.launch {
            getListReviewUseCase.getListReview()
                .catch {
                    when (it) {
                        is UnknownHostException,
                        is SocketTimeoutException -> _isOnline.send(false)
                    }
                }
                .collect { reviewInfo ->
                    if (reviewInfo?.serverError == null) {
                        _reviewInfo.value = reviewInfo
                    }
                }
        }
    }

    fun getFirstReviews() {
        viewModelScope.launch {
            getFirstReviewsUseCase.getFirstReviews(
                _reviewInfo.value?.data ?: emptyList()
            )
                .collect { reviewList ->
                    _reviewList.value = reviewList
                }
        }
    }

    fun getMoreReview() {
        getMoreReviewUseCase.getMoreReview()
    }

    fun determiningIfThereAreReviews() {
        _isNoReviews.value = reviewInfo.value?.data != _reviewList.value
    }
}