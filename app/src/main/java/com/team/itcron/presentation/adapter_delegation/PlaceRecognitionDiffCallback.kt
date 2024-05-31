package com.team.itcron.presentation.adapter_delegation

import androidx.recyclerview.widget.DiffUtil
import com.team.itcron.domain.models.PlaceRecognitionInForm

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