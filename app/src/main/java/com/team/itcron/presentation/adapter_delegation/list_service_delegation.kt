package com.team.itcron.presentation.adapter_delegation

import androidx.core.content.ContextCompat
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
            textService.setBackgroundDrawable(
                getDrawable(R.drawable.state_form_item)
            )
            textService.setTextColor(
                if (item.isActive)
                    ContextCompat.getColor(context, R.color.white)
                else
                    ContextCompat.getColor(context, R.color.color_main)
            )
            textService.isSelected = item.isActive
        }
    }
}