package com.team.itcron.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.team.itcron.R
import com.team.itcron.databinding.FragmentListFilterBinding
import com.team.itcron.presentation.adapter_delegation.FilterItemDiffCallback
import com.team.itcron.presentation.adapter_delegation.caseFilterAdapterDelegate
import com.team.itcron.presentation.adapter_delegation.filterAdapterDelegate
import com.team.itcron.presentation.navigate.NavigateHelper
import com.team.itcron.presentation.view_models.ListFilterViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent

class ListFilterFragment : Fragment(), KoinComponent {

    private var _binding: FragmentListFilterBinding? = null
    private val binding: FragmentListFilterBinding
        get() = _binding ?: throw RuntimeException(
            getString(R.string.text_is_null_binding_view_list_filter_fragment)
        )

    private val navigateHelper: NavigateHelper by lazy {
        (requireActivity() as? NavigateHelper)
            ?: throw RuntimeException(
                getString(R.string.text_error_no_implements_interface)
            )
    }

    private val listFilterViewModel by viewModel<ListFilterViewModel>()

    private val adapter by lazy {
        AsyncListDifferDelegationAdapter(
            FilterItemDiffCallback(),
            caseFilterAdapterDelegate(),
            filterAdapterDelegate {
                listFilterViewModel.setSelectionFilter(it)
            }
        )
    }

    // ****** lifecycle *****
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.casesFilters.adapter = adapter
        binding.casesFilters.itemAnimator = null
        listFilterViewModel.getFilterToCategoryList()
        observeViewModel()
        clickedListenerClearFilter()
        clickedListenerBtnBack()
        listeningOnBackPressed()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    // ****** lifecycle *****

    private fun observeViewModel() {
        lifecycleScope.launch {
            listFilterViewModel.filterToCategoryList.flowWithLifecycle(lifecycle)
                .collect {
                    adapter.items = it
                    listFilterViewModel.determiningPossibilityCleaning()
                }
        }

        lifecycleScope.launch {
            listFilterViewModel.isOnline.flowWithLifecycle(lifecycle)
                .collect { isOnline ->
                    if (!isOnline) {
                        navigateHelper.navigateTo(NoInternetFragment.newInstance())
                    }
                }
        }

        lifecycleScope.launch {
            listFilterViewModel.canClear.flowWithLifecycle(lifecycle)
                .collect { isClear ->
                    binding.btnClearFilters.isInvisible = !isClear
                }
        }

        lifecycleScope.launch {
            listFilterViewModel.listActiveFilter.flowWithLifecycle(lifecycle)
                .collect { filters ->
                    navigateHelper.navigateTo(
                        ListCaseFragment.newInstance(ArrayList(filters))
                    )
                }
        }
    }

    private fun clickedListenerClearFilter() {
        binding.btnClearFilters.setOnClickListener {
            listFilterViewModel.clearFilterList()
        }
    }

    private fun clickedListenerBtnBack() {
        binding.btnBack.setOnClickListener {
            listFilterViewModel.formingListActiveFilters()
        }
    }

    private fun listeningOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback {
            listFilterViewModel.formingListActiveFilters()
        }
    }

    companion object {
        fun newInstance(): ListFilterFragment {
            return ListFilterFragment()
        }
    }
}