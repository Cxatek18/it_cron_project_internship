package com.team.itcron.presentation.adapter_delegation

import com.bumptech.glide.RequestManager
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.team.itcron.databinding.ReviewItemBinding
import com.team.itcron.domain.models.Review

fun reviewDelegate(
    glide: RequestManager,
) = adapterDelegateViewBinding<Review, Review, ReviewItemBinding>(
    { layoutInflater, root -> ReviewItemBinding.inflate(layoutInflater, root, false) }
) {
    bind {
        with(binding) {
            textReview.text = item.text
            userNameReview.text = item.customerName
            companyName.text = item.company.trim()
        }
        glide.load(item.icon).into(binding.imageUserReview)
    }
}