package com.team.itcron.presentation.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.team.itcron.R
import com.team.itcron.databinding.FragmentListFilterBinding
import com.team.itcron.presentation.navigate.NavigateHelper
import com.team.itcron.presentation.view_models.ListCaseViewModel
import com.team.itcron.presentation.view_models.ListFilterViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
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
        listFilterViewModel.getFilterToCategoryList()
        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    // ****** lifecycle *****

    private fun observeViewModel() {
        lifecycleScope.launch {
            listFilterViewModel.filterToCategoryList.flowWithLifecycle(lifecycle)
                .filterNotNull()
                .collect {filterToCategoryList ->
                    Log.d("ListFilterFragment", filterToCategoryList.data.toString())
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
    }

    companion object {
        fun newInstance(): ListFilterFragment {
            return ListFilterFragment()
        }
    }
}