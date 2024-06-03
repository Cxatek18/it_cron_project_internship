package ru.it_cron.intern3.domain.usecase

import ru.it_cron.intern3.domain.models.PlaceRecognitionInForm
import ru.it_cron.intern3.domain.repository.PlaceRecognitionInFormRepository

class SetSelectionPlaceRecognitionUseCase(
    private val repository: PlaceRecognitionInFormRepository
) {

    fun setSelectionPlaceRecognition(placeRecognitionInForm: PlaceRecognitionInForm) {
        repository.setSelectionPlaceRecognition(placeRecognitionInForm)
    }
}