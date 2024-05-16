package com.team.itcron.presentation.adapter_delegation

import com.bumptech.glide.RequestManager
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.team.itcron.databinding.CaseImageItemBinding
import com.team.itcron.databinding.CaseOpportunitiesItemBinding
import com.team.itcron.databinding.TechnologicalItemBinding
import com.team.itcron.domain.models.Platform
import com.team.itcron.domain.models.Technology

fun caseImagesDelegate(
    glide: RequestManager,
    clickImage: () -> Unit
) = adapterDelegateViewBinding<String, String, CaseImageItemBinding>(
    { layoutInflater, root -> CaseImageItemBinding.inflate(layoutInflater, root, false) }
) {
    with(binding) {
        caseItemImage.setOnClickListener {
            clickImage()
        }
    }

    bind {
        glide.load(item).into(binding.caseItemImage)
    }
}

fun caseOpportunitiesDelegate() = adapterDelegateViewBinding<
        String, String, CaseOpportunitiesItemBinding
        >(
    { layoutInflater, root ->
        CaseOpportunitiesItemBinding.inflate(
            layoutInflater, root, false
        )
    }
) {
    bind {
        binding.caseOpportunitiesText.text = item
    }
}

fun caseTechnologyDelegate() = adapterDelegateViewBinding<
        Technology, Technology, TechnologicalItemBinding
        >(
    { layoutInflater, root ->
        TechnologicalItemBinding.inflate(
            layoutInflater, root, false
        )
    }
) {
    bind {
        binding.caseTextTechnology.text = item.name
    }
}

fun casePlatformDelegate() = adapterDelegateViewBinding<
        Platform, Platform, TechnologicalItemBinding
        >(
    { layoutInflater, root ->
        TechnologicalItemBinding.inflate(
            layoutInflater, root, false
        )
    }
) {
    bind {
        binding.caseTextTechnology.text = item.name
    }
}