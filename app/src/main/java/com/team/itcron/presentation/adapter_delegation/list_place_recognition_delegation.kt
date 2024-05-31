package com.team.itcron.presentation.adapter_delegation

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.team.itcron.R
import com.team.itcron.databinding.PlaceRecognitionFormItemBinding
import com.team.itcron.domain.models.PlaceRecognitionInForm

fun placeRecognitionInFormDelegate(
    setSelectionBudget: (placeRecognitionInForm: PlaceRecognitionInForm) -> Unit
) =
    adapterDelegateViewBinding<
            PlaceRecognitionInForm, PlaceRecognitionInForm, PlaceRecognitionFormItemBinding
            >(
        { layoutInflater, root ->
            PlaceRecognitionFormItemBinding.inflate(
                layoutInflater, root, false
            )
        }
    ) {
        with(binding) {
            textPlaceRecognition.setOnClickListener {
                setSelectionBudget(item)
            }
        }

        bind {
            with(binding) {
                textPlaceRecognition.text = item.title
                if (item.isActive) {
                    textPlaceRecognition.setBackgroundDrawable(
                        getDrawable(R.drawable.background_active_service_item)
                    )
                    textPlaceRecognition.setTextColor(getColor(R.color.white))
                } else {
                    textPlaceRecognition.setBackgroundDrawable(
                        getDrawable(R.drawable.background_no_active_service_item)
                    )
                    textPlaceRecognition.setTextColor(getColor(R.color.color_main))
                }
            }
        }
    }