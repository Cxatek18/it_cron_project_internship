package ru.it_cron.intern3.domain.repository

import ru.it_cron.intern3.domain.models.PlaceRecognitionInForm
import kotlinx.coroutines.flow.Flow

interface PlaceRecognitionInFormRepository {

    fun createListPlaceRecognition(): Flow<List<PlaceRecognitionInForm>>

    fun setSelectionPlaceRecognition(placeRecognitionInForm: PlaceRecognitionInForm)

    fun getActivePlaceRecognition(): Flow<PlaceRecognitionInForm?>
}