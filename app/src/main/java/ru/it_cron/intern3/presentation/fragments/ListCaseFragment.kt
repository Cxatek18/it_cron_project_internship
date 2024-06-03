package ru.it_cron.intern3.presentation.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.it_cron.intern3.R
import ru.it_cron.intern3.databinding.FragmentListCaseBinding
import ru.it_cron.intern3.domain.models.Case
import ru.it_cron.intern3.domain.models.Filter
import ru.it_cron.intern3.presentation.adapter_delegation.CaseDiffCallback
import ru.it_cron.intern3.presentation.adapter_delegation.caseAdapterDelegate
import ru.it_cron.intern3.presentation.navigate.NavigateHelper
import ru.it_cron.intern3.presentation.view_models.ListCaseViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent

class ListCaseFragment : Fragment(), KoinComponent {

    private var _binding: FragmentListCaseBinding? = null
    private val binding: FragmentListCaseBinding
        get() = _binding ?: throw RuntimeException(
            getString(R.string.text_is_null_binding_view_list_case_fragment)
        )

    private val listCaseViewModel by viewModel<ListCaseViewModel>()

    private val navigateHelper: NavigateHelper by lazy {
        (requireActivity() as? NavigateHelper)
            ?: throw RuntimeException(
                getString(R.string.text_error_no_implements_interface)
            )
    }

    private val glide by lazy { Glide.with(this) }

    private lateinit var cases: ArrayList<Case>

    private val adapter by lazy {
        AsyncListDifferDelegationAdapter(
            CaseDiffCallback(),
            caseAdapterDelegate(glide) { caseId, caseImage ->
                onClickCase(caseId, caseImage)
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
        _binding = FragmentListCaseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listCase.adapter = adapter
        parseArgs()
        listCaseViewModel.getActiveFilter()
        listCaseViewModel.getCaseToList()
        observeViewModel()
        onClickBackBtn()
        listeningOnBackPressed()
        onCLickFilters()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    // ****** lifecycle *****

    private fun observeViewModel() {
        lifecycleScope.launch {
            listCaseViewModel.listActiveFilter.flowWithLifecycle(lifecycle)
                .collect { listActiveFilter ->
                    if (listActiveFilter.isNotEmpty()) {
                        listCaseViewModel.filteringCases(listActiveFilter)
                        setColorTextFilter(listActiveFilter)
                        listCaseViewModel.filteringCaseToList.flowWithLifecycle(lifecycle)
                            .collect { caseToList ->
                                if (caseToList.isEmpty() && listActiveFilter.isNotEmpty()) {
                                    binding.listCase.visibility = View.GONE
                                    binding.textNoSearchResult.visibility = View.VISIBLE
                                }
                                adapter.items = caseToList
                                binding.progressBar.visibility = View.GONE
                                cases = ArrayList(caseToList)
                            }
                    } else {
                        listCaseViewModel.caseToList.flowWithLifecycle(lifecycle)
                            .collect { caseToList ->
                                if (caseToList.isNotEmpty()) {
                                    adapter.items = caseToList
                                    binding.progressBar.visibility = View.GONE
                                    cases = ArrayList(caseToList)
                                }
                            }
                    }
                }
        }

        lifecycleScope.launch {
            listCaseViewModel.isOnline.flowWithLifecycle(lifecycle)
                .collect { isOnline ->
                    if (!isOnline) {
                        navigateHelper.navigateTo(NoInternetFragment.newInstance())
                    }
                }
        }
    }

    private fun setColorTextFilter(filters: List<Filter>) {
        if (filters.isNotEmpty()) {
            binding.textFilterAppbar.setTextColor(
                requireContext().getColor(R.color.color_main_second)
            )
        }
    }

    private fun onCLickFilters() {
        binding.textFilterAppbar.setOnClickListener {
            navigateHelper.navigateTo(ListFilterFragment.newInstance())
        }
    }

    private fun onClickBackBtn() {
        binding.btnBack.setOnClickListener {
            navigateHelper.navigateTo(MainFragment.newInstance())
        }
    }

    private fun onClickCase(caseId: String, caseImage: String) {
        navigateHelper.navigateTo(
            CaseDetailFragment.newInstance(
                caseId,
                caseImage,
                cases
            )
        )
    }

    private fun listeningOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback {
            navigateHelper.navigateTo(MainFragment.newInstance())
        }
    }

    private fun parseArgs() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelableArrayList(
                EXTRA_ARGS_FILTERS,
                Filter::class.java
            ).let {
                if (it != null) {
                    val listActiveFilter = it.toList()
                    listCaseViewModel.addFiltersToListActiveFilter(listActiveFilter)
                }
            }
        } else {
            arguments?.getParcelableArrayList<Filter>(
                EXTRA_ARGS_FILTERS
            ).let {
                if (it != null) {
                    val listActiveFilter = it.toList()
                    listCaseViewModel.addFiltersToListActiveFilter(listActiveFilter)
                }
            }
        }
    }

    companion object {
        private const val EXTRA_ARGS_FILTERS = "filters"

        fun newInstance(): ListCaseFragment {
            return ListCaseFragment()
        }

        fun newInstance(filters: ArrayList<Filter>): ListCaseFragment {
            return ListCaseFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(
                        EXTRA_ARGS_FILTERS,
                        filters
                    )
                }
            }
        }
    }
}