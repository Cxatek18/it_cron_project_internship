package com.team.itcron.presentation.adapter_delegation

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.team.itcron.databinding.RequisiteItemBinding
import com.team.itcron.domain.models.Requisite

fun requisiteDelegate(
) = adapterDelegateViewBinding<Requisite, Requisite, RequisiteItemBinding>(
    { layoutInflater, root ->
        RequisiteItemBinding.inflate(
            layoutInflater, root, false
        )
    }
) {

    bind {
        with(binding) {
            textNameRequisite.text = item.name
            textValueRequisite.text = item.value
        }
    }
}
