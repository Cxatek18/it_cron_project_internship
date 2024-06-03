package ru.it_cron.intern3.presentation.adapter_delegation

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import ru.it_cron.intern3.R
import ru.it_cron.intern3.databinding.ServiceFormItemBinding
import ru.it_cron.intern3.domain.models.ServiceInForm

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
            textService.isSelected = item.isActive
        }
    }
}