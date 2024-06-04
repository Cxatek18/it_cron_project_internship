package ru.it_cron.intern3.presentation.adapter_delegation

import androidx.core.content.ContextCompat
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import ru.it_cron.intern3.R
import ru.it_cron.intern3.databinding.PlaceRecognitionFormItemBinding
import ru.it_cron.intern3.domain.models.PlaceRecognitionInForm

fun placeRecognitionInFormDelegate(
    setSelectionPlaceRecognition: (placeRecognitionInForm: PlaceRecognitionInForm) -> Unit
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
                setSelectionPlaceRecognition(item)
            }
        }

        bind {
            with(binding) {
                textPlaceRecognition.text = item.title
                textPlaceRecognition.background =
                    ContextCompat.getDrawable(context, R.drawable.state_form_item)
                textPlaceRecognition.isSelected = item.isActive
            }
        }
    }