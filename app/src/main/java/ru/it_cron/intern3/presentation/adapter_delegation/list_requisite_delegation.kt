package ru.it_cron.intern3.presentation.adapter_delegation

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import ru.it_cron.intern3.databinding.RequisiteItemBinding
import ru.it_cron.intern3.domain.models.Requisite

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
