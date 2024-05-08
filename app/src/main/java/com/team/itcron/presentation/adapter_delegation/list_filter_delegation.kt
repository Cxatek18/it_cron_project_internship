package com.team.itcron.presentation.adapter_delegation

import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.team.itcron.R
import com.team.itcron.databinding.CasesFilterBinding
import com.team.itcron.databinding.FilterItemBinding
import com.team.itcron.domain.models.CategoryFilter
import com.team.itcron.domain.models.Filter

fun caseFilterAdapterDelegate(
    setSelectionFilter: (Filter) -> Unit,
) = adapterDelegateViewBinding<
        CategoryFilter,
        CategoryFilter,
        CasesFilterBinding
        >(
    { layoutInflater, root -> CasesFilterBinding.inflate(layoutInflater, root, false) }
) {
    val adapter = AsyncListDifferDelegationAdapter(
        FilterDiffCallback(),
        filterAdapterDelegate(
            setSelectionFilter,
        )
    )
    bind {
        binding.titleFilters.text = item.name
        binding.listFilter.adapter = adapter
        binding.listFilter.itemAnimator = null
        adapter.items = item.filters
    }
}

fun filterAdapterDelegate(
    setSelectionFilter: (Filter) -> Unit,
) = adapterDelegateViewBinding<
        Filter,
        Filter,
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