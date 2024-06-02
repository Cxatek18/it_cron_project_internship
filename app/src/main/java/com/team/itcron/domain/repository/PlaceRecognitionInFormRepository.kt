package com.team.itcron.domain.repository

import com.team.itcron.domain.models.PlaceRecognitionInForm
import kotlinx.coroutines.flow.Flow

interface PlaceRecognitionInFormRepository {

    fun createListPlaceRecognition(): Flow<List<PlaceRecognitionInForm>>

    fun setSelectionPlaceRecognition(placeRecognitionInForm: PlaceRecognitionInForm)

    fun getActivePlaceRecognition(): Flow<PlaceRecognitionInForm?>
}