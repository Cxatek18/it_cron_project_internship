package ru.it_cron.intern3.domain.usecase

import ru.it_cron.intern3.domain.models.PlaceRecognitionInForm
import ru.it_cron.intern3.domain.repository.PlaceRecognitionInFormRepository
import kotlinx.coroutines.flow.Flow

class CreateListPlaceRecognitionUseCase(private val repository: PlaceRecognitionInFormRepository) {

    fun createListPlaceRecognition(): Flow<List<PlaceRecognitionInForm>> {
        return repository.createListPlaceRecognition()
    }
}