package com.team.itcron.presentation.adapter_delegation

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.team.itcron.R
import com.team.itcron.databinding.ServiceFormItemBinding
import com.team.itcron.domain.models.ServiceInForm

fun serviceInFormDelegate(
    setSelectionService: (serviceInForm: ServiceInForm) -> Unit
) = adapterDelegateViewBinding<ServiceInForm, ServiceInForm, ServiceFormItemBinding>(
    { layoutInflater, root ->
        ServiceFormItemBinding.inflate(
            layoutInflater, root, false
        )
    }
) {
    with(binding) {
        textService.setOnClickListener {
            setSelectionService(item)
        }
    }

    bind {
        with(binding) {
            textService.text = item.title
            if (item.isActive) {
                textService.setBackgroundDrawable(
                    getDrawable(R.drawable.background_active_service_item)
                )
                textService.setTextColor(getColor(R.color.white))
            } else {
                textService.setBackgroundDrawable(
                    getDrawable(R.drawable.background_no_active_service_item)
                )
                textService.setTextColor(getColor(R.color.color_main))
            }
        }
    }
}