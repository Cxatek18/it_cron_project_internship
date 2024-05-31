package com.team.itcron.domain.usecase

import com.team.itcron.domain.models.PlaceRecognitionInForm
import com.team.itcron.domain.repository.PlaceRecognitionInFormRepository
import kotlinx.coroutines.flow.Flow

class CreateListPlaceRecognitionUseCase(private val repository: PlaceRecognitionInFormRepository) {

    fun createListPlaceRecognition(): Flow<List<PlaceRecognitionInForm>> {
        return repository.createListPlaceRecognition()
    }
}