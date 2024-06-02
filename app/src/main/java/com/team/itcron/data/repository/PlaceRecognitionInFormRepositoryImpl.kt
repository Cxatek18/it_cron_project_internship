package com.team.itcron.data.repository

import android.content.Context
import com.team.itcron.R
import com.team.itcron.domain.models.PlaceRecognitionInForm
import com.team.itcron.domain.repository.PlaceRecognitionInFormRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class PlaceRecognitionInFormRepositoryImpl(val context: Context) :
    PlaceRecognitionInFormRepository {

    private val placeRecognitionListInForm = MutableStateFlow<List<PlaceRecognitionInForm>>(
        emptyList()
    )

    override fun createListPlaceRecognition(): Flow<List<PlaceRecognitionInForm>> =
        flow<List<PlaceRecognitionInForm>> {
            placeRecognitionListInForm.value = arrayListOf(
                PlaceRecognitionInForm(
                    getStringPlaceRecognition(R.string.text_social_network), false
                ),
                PlaceRecognitionInForm(
                    getStringPlaceRecognition(R.string.text_recommendations), false
                ),
                PlaceRecognitionInForm(
                    getStringPlaceRecognition(R.string.text_works), false
                ),
                PlaceRecognitionInForm(
                    getStringPlaceRecognition(R.string.text_ratings), false
                ),
                PlaceRecognitionInForm(
                    getStringPlaceRecognition(R.string.text_mailing), false
                ),
                PlaceRecognitionInForm(
                    getStringPlaceRecognition(R.string.text_advertisement), false
                ),
            )
            placeRecognitionListInForm.collect { emit(it) }
        }

    override fun setSelectionPlaceRecognition(placeRecognitionInForm: PlaceRecognitionInForm) {
        placeRecognitionListInForm.update { placeRecognition ->
            placeRecognition.map {
                val isActive = if (
                    it.title == placeRecognitionInForm.title
                ) !placeRecognitionInForm.isActive else false
                it.copy(isActive = isActive)
            }
        }

    }

    override fun getActivePlaceRecognition(): Flow<PlaceRecognitionInForm?> {
        return placeRecognitionListInForm.map { list -> list.find { it.isActive } }
    }

    private fun getStringPlaceRecognition(idString: Int): String {
        return context.resources.getString(idString)
    }
}