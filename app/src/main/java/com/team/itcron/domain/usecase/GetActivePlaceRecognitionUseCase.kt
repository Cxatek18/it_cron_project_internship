package com.team.itcron.domain.usecase

import com.team.itcron.domain.models.PlaceRecognitionInForm
import com.team.itcron.domain.repository.PlaceRecognitionInFormRepository
import kotlinx.coroutines.flow.Flow

class GetActivePlaceRecognitionUseCase(
    private val repository: PlaceRecognitionInFormRepository
) {

    fun getActivePlaceRecognition(): Flow<PlaceRecognitionInForm?> {
        return repository.getActivePlaceRecognition()
    }
}