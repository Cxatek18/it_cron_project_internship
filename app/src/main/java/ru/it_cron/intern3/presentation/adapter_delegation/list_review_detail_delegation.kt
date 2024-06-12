package ru.it_cron.intern3.presentation.adapter_delegation

import com.bumptech.glide.RequestManager
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import ru.it_cron.intern3.R
import ru.it_cron.intern3.databinding.ReviewItemDetailBinding
import ru.it_cron.intern3.domain.models.Review

fun reviewDetailDelegate(
    glide: RequestManager,
) = adapterDelegateViewBinding<Review, Review, ReviewItemDetailBinding>(
    { layoutInflater, root -> ReviewItemDetailBinding.inflate(layoutInflater, root, false) }
) {
    with(binding) {
        textWatchMore.setOnClickListener {
            val countLines = textReview.maxLines
            val countLinesReview = getString(
                R.string.count_max_lines_review_text
            ).toInt()
            if (countLines == countLinesReview) {
                textReview.maxLines = Integer.MAX_VALUE
                textWatchMore.text = getString(R.string.text_hide_information)
            } else {
                textReview.maxLines = countLinesReview
                textWatchMore.text = getString(R.string.text_watch_more)
            }
        }
    }

    bind {
        with(binding) {
            textReview.text = item.text
            nameUser.text = item.customerName
            companyUser.text = item.company.trim()
        }
        glide.load(item.icon).into(binding.photoUser)
    }
}