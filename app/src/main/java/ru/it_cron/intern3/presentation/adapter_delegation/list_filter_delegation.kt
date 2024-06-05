package ru.it_cron.intern3.presentation.adapter_delegation

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import ru.it_cron.intern3.R
import ru.it_cron.intern3.databinding.CategoryFilterItemBinding
import ru.it_cron.intern3.databinding.FilterItemBinding
import ru.it_cron.intern3.domain.models.FilterItem

fun caseFilterAdapterDelegate() = adapterDelegateViewBinding<
        FilterItem.Category,
        FilterItem,
        CategoryFilterItemBinding
        >(
    { layoutInflater, root -> CategoryFilterItemBinding.inflate(layoutInflater, root, false) }
) {
    bind {
        binding.titleFilters.text = item.name
    }
}

fun filterAdapterDelegate(
    setSelectionFilter: (FilterItem.Filter) -> Unit,
) = adapterDelegateViewBinding<
        FilterItem.Filter,
        FilterItem,
        FilterItemBinding
        >(
    { layoutInflater, root -> FilterItemBinding.inflate(layoutInflater, root, false) }
) {
    with(binding) {
        filterBlock.setOnClickListener {
            setSelectionFilter(item)
        }
    }

    bind {
        with(binding) {
            filterName.text = item.name
            if (item.isActive) {
                filterBlock.setBackgroundColor(getColor(R.color.color_main_second))
                filterName.setTextColor(getColor(R.color.white))
                filterCheckBox.setImageResource(R.drawable.active_checkbox_image)
            } else {
                filterBlock.setBackgroundColor(getColor(R.color.white))
                filterName.setTextColor(getColor(R.color.color_text_title))
                filterCheckBox.setImageResource(R.drawable.no_active_checkbox_image)
            }
        }
    }
}