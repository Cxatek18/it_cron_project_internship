package ru.it_cron.intern3.presentation.adapter_delegation

import com.bumptech.glide.RequestManager
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import ru.it_cron.intern3.databinding.ReviewItemBinding
import ru.it_cron.intern3.domain.models.Review

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