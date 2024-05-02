package com.team.itcron.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.team.itcron.R
import com.team.itcron.databinding.FragmentListCaseBinding
import com.team.itcron.presentation.adapter.CaseAdapter
import com.team.itcron.presentation.navigate.NavigateHelper
import com.team.itcron.presentation.view_models.ListCaseViewModel
import kotlinx.coroutines.flow.filterNotNull
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
    private lateinit var adapter: CaseAdapter

    private val navigateHelper: NavigateHelper by lazy {
        (requireActivity() as? NavigateHelper)
            ?: throw RuntimeException(
                getString(R.string.text_error_no_implements_interface)
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
        listCaseViewModel.getCaseToList()
        adapter = CaseAdapter(Glide.with(this))
        binding.listCase.adapter = adapter
        observeViewModel()
        onClickBackBtn()
        listeningOnBackPressed()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    // ****** lifecycle *****

    private fun observeViewModel() {
        lifecycleScope.launch {
            listCaseViewModel.caseToList.flowWithLifecycle(lifecycle)
                .filterNotNull()
                .collect { caseToList ->
                    adapter.submitList(caseToList.data)
                    binding.progressBar.visibility = View.GONE
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

    private fun onClickBackBtn() {
        binding.btnBack.setOnClickListener {
            navigateHelper.exit()
        }
    }

    private fun listeningOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback {
            navigateHelper.navigateTo(MainFragment.newInstance())
        }
    }

    companion object {
        fun newInstance(): ListCaseFragment {
            return ListCaseFragment()
        }
    }
}