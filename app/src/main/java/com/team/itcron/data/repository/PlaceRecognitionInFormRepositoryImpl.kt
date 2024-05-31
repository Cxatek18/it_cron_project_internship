package com.team.itcron.data.repository

import com.team.itcron.domain.models.PlaceRecognitionInForm
import com.team.itcron.domain.repository.PlaceRecognitionInFormRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update

class PlaceRecognitionInFormRepositoryImpl : PlaceRecognitionInFormRepository {

    private val placeRecognitionListInForm = MutableStateFlow<List<PlaceRecognitionInForm>>(
        emptyList()
    )

    private val activePlaceRecognition = MutableStateFlow<PlaceRecognitionInForm?>(null)
    override fun createListPlaceRecognition(): Flow<List<PlaceRecognitionInForm>> =
        flow<List<PlaceRecognitionInForm>> {
            placeRecognitionListInForm.value = arrayListOf(
                PlaceRecognitionInForm("Соцсети", false),
                PlaceRecognitionInForm("Рекомендации", false),
                PlaceRecognitionInForm("Работы", false),
                PlaceRecognitionInForm("Рейтинги", false),
                PlaceRecognitionInForm("Рассылка", false),
                PlaceRecognitionInForm("Реклама", false),
            )
            placeRecognitionListInForm.collect { emit(it) }
        }

    override fun setSelectionPlaceRecognition(placeRecognitionInForm: PlaceRecognitionInForm) {
        placeRecognitionListInForm.update { placeRecognition ->
            val listPlaceRecognitionNoActive =
                mutableListOf<PlaceRecognitionInForm>()
            placeRecognition.forEach {
                listPlaceRecognitionNoActive.add(
                    it.copy(title = it.title, isActive = false)
                )
            }
            var listNew = listOf<PlaceRecognitionInForm>()
            val idx = listPlaceRecognitionNoActive.indexOfFirst {
                it.title == placeRecognitionInForm.title
            }
            if (idx >= 0) {
                listPlaceRecognitionNoActive[idx] = listPlaceRecognitionNoActive[idx]
                    .copy(isActive = !placeRecognitionInForm.isActive)
                listNew = listPlaceRecognitionNoActive.toList()
            }
            listNew
        }
    }

    override fun getActivePlaceRecognition(): Flow<PlaceRecognitionInForm?> =
        flow<PlaceRecognitionInForm?> {
            activePlaceRecognition.value = placeRecognitionListInForm.value.find {
                it.isActive
            }
            activePlaceRecognition.collect { emit(it) }
        }
}