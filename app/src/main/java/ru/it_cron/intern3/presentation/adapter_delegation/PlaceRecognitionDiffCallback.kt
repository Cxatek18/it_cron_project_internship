package ru.it_cron.intern3.presentation.adapter_delegation

import androidx.recyclerview.widget.DiffUtil
import ru.it_cron.intern3.domain.models.PlaceRecognitionInForm

class PlaceRecognitionDiffCallback : DiffUtil.ItemCallback<PlaceRecognitionInForm>() {

    override fun areItemsTheSame(
        oldItem: PlaceRecognitionInForm,
        newItem: PlaceRecognitionInForm
    ): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(
        oldItem: PlaceRecognitionInForm,
        newItem: PlaceRecognitionInForm
    ): Boolean {
        return oldItem == newItem
    }
}