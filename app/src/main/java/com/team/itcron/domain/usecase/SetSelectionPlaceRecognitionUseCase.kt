package com.team.itcron.domain.usecase

import com.team.itcron.domain.models.PlaceRecognitionInForm
import com.team.itcron.domain.repository.PlaceRecognitionInFormRepository

class SetSelectionPlaceRecognitionUseCase(
    private val repository: PlaceRecognitionInFormRepository
) {

    fun setSelectionPlaceRecognition(placeRecognitionInForm: PlaceRecognitionInForm) {
        repository.setSelectionPlaceRecognition(placeRecognitionInForm)
    }
}