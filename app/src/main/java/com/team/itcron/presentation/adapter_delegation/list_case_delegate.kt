package com.team.itcron.presentation.adapter_delegation

import com.bumptech.glide.RequestManager
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.team.itcron.databinding.CaseItemBinding
import com.team.itcron.domain.models.Case

fun caseAdapterDelegate(
    glide: RequestManager
) = adapterDelegateViewBinding<Case, Case, CaseItemBinding>(
    { layoutInflater, root -> CaseItemBinding.inflate(layoutInflater, root, false) }
) {
    bind {
        binding.titleCase.text = item.title
        glide.load(item.image).into(binding.imageCase)
    }
}