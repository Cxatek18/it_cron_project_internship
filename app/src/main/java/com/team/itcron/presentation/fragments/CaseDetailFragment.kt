package com.team.itcron.presentation.fragments

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.team.itcron.R
import com.team.itcron.databinding.FragmentCaseDetailBinding
import com.team.itcron.domain.models.Case
import com.team.itcron.domain.models.CaseDetail
import com.team.itcron.presentation.adapter_delegation.CaseImageDiffCallback
import com.team.itcron.presentation.adapter_delegation.CaseOpportunityDiffCallback
import com.team.itcron.presentation.adapter_delegation.CasePlatformDiffCallback
import com.team.itcron.presentation.adapter_delegation.CaseTechnologyDiffCallback
import com.team.itcron.presentation.adapter_delegation.caseImagesDelegate
import com.team.itcron.presentation.adapter_delegation.caseOpportunitiesDelegate
import com.team.itcron.presentation.adapter_delegation.casePlatformDelegate
import com.team.itcron.presentation.adapter_delegation.caseTechnologyDelegate
import com.team.itcron.presentation.navigate.NavigateHelper
import com.team.itcron.presentation.view_models.CaseDetailViewModel
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent

class CaseDetailFragment : Fragment(), KoinComponent {

    private var _binding: FragmentCaseDetailBinding? = null
    private val binding: FragmentCaseDetailBinding
        get() = _binding ?: throw RuntimeException(
            getString(R.string.text_is_null_binding_view_detail_case_fragment)
        )

    private val navigateHelper: NavigateHelper by lazy {
        (requireActivity() as? NavigateHelper)
            ?: throw RuntimeException(
                getString(R.string.text_error_no_implements_interface)
            )
    }

    private val glide by lazy { Glide.with(this) }

    private val caseDetailViewModel by viewModel<CaseDetailViewModel>()

    private val adapterImages by lazy {
        AsyncListDifferDelegationAdapter(
            CaseImageDiffCallback(),
            caseImagesDelegate(glide) {
                listeningOnClickScreenshot()
            }
        )
    }

    private val adapterCaseOpportunity by lazy {
        AsyncListDifferDelegationAdapter(
            CaseOpportunityDiffCallback(),
            caseOpportunitiesDelegate()
        )
    }

    private val adapterCaseTechnology by lazy {
        AsyncListDifferDelegationAdapter(
            CaseTechnologyDiffCallback(),
            caseTechnologyDelegate()
        )
    }

    private val adapterCasePlatform by lazy {
        AsyncListDifferDelegationAdapter(
            CasePlatformDiffCallback(),
            casePlatformDelegate()
        )
    }

    private lateinit var caseId: String
    private lateinit var caseImage: String
    private lateinit var listCase: List<Case>
    private lateinit var listScreenshot: List<String>
    private lateinit var caseColor: String

    // ****** lifecycle *****
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCaseDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listeningOnBackPressed()
        parseArgs()
        caseDetailViewModel.getCaseDetail(caseId)
        caseDetailViewModel.getNextCase(listCase, caseId)
        observeViewModel()
        setImageCase()
        listeningOnClickEmailCompany()
        listeningOnClickGoGeneralPage()
        listeningOnClickBtnBack()
        listeningOnClickBtnSendForm()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    // ****** lifecycle *****

    private fun setImageCase() {
        glide.load(caseImage).into(binding.caseImage)
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            caseDetailViewModel.caseDetail.flowWithLifecycle(lifecycle)
                .filterNotNull()
                .collect { caseDetail ->
                    setDataToDetailCase(caseDetail)
                    listScreenshot = caseDetail.data.images
                    caseColor = caseDetail.data.caseColor ?: getString(R.string.default_color)
                }
        }

        lifecycleScope.launch {
            caseDetailViewModel.isOnline.flowWithLifecycle(lifecycle)
                .collect {
                    if (!it) {
                        navigateHelper.navigateTo(NoInternetFragment.newInstance())
                    }
                }
        }

        lifecycleScope.launch {
            caseDetailViewModel.caseHttpException.flowWithLifecycle(lifecycle)
                .collect {
                    if (it) {
                        navigateHelper.navigateTo(ListCaseFragment.newInstance())
                    }
                }
        }

        lifecycleScope.launch {
            caseDetailViewModel.nextCase.flowWithLifecycle(lifecycle)
                .filterNotNull()
                .collect { case ->
                    glide.load(case.image).into(binding.blockNextCase)
                    listeningOnClickNextCase(case.id, case.image)
                }
        }
    }

    private fun setDataToDetailCase(caseDetail: CaseDetail) {
        with(caseDetail.data) {
            if (title.isNotEmpty()) {
                binding.titleCase.text = title
            } else {
                binding.titleCase.visibility = View.GONE
            }
            if (task.isNotEmpty()) {
                binding.caseTextTask.text = task
            } else {
                binding.caseTextTask.visibility = View.GONE
                binding.textTask.visibility = View.GONE
            }
            if (images.isNotEmpty()) {
                binding.caseImagesRv.adapter = adapterImages
                adapterImages.items = images
            } else {
                binding.caseImagesRv.visibility = View.GONE
            }
            if (featuresTitle.isNullOrEmpty()) {
                binding.textOpportunities.text = featuresTitle
            } else {
                binding.textOpportunities.visibility = View.GONE
            }
            if (featuresDone.isNullOrEmpty()) {
                binding.caseOpportunities.adapter = adapterCaseOpportunity
                adapterCaseOpportunity.items = featuresDone
            } else {
                binding.caseOpportunities.visibility = View.GONE
            }
            if (technologies.isEmpty() && platforms.isEmpty()) {
                binding.technicalBlock.visibility = View.GONE
            }
            if (technologies.isNotEmpty()) {
                var spanCount: Int = technologies.count() / 2
                if (spanCount == 0) {
                    spanCount = 1
                }
                binding.caseTechnologies.layoutManager = GridLayoutManager(
                    requireContext(), spanCount, GridLayoutManager.HORIZONTAL, false
                )
                binding.caseTechnologies.adapter = adapterCaseTechnology
                adapterCaseTechnology.items = technologies
            } else {
                binding.textTechnological.visibility = View.GONE
                binding.caseTechnologies.visibility = View.GONE
            }
            if (platforms.isNotEmpty()) {
                var spanCount: Int = technologies.count() / 2
                if (spanCount == 0) {
                    spanCount = 1
                }
                binding.casePlatforms.layoutManager = GridLayoutManager(
                    requireContext(), spanCount, GridLayoutManager.HORIZONTAL, false
                )
                binding.casePlatforms.adapter = adapterCasePlatform
                adapterCasePlatform.items = platforms
            } else {
                binding.textPlatform.visibility = View.GONE
                binding.casePlatforms.visibility = View.GONE
            }
            if (androidUrl.isNullOrEmpty()) {
                binding.btnPlayMarket.visibility = View.GONE
            }
            if (iosUrl.isNullOrEmpty()) {
                binding.btnAppStore.visibility = View.GONE
            }
            if (webUrl.isNullOrEmpty()) {
                binding.btnWebsiteBlock.visibility = View.GONE
            }
            if (caseColor?.isNotEmpty() == true) {
                val color: Int = Color.parseColor("#${caseColor}")
                binding.caseImagesRv.setBackgroundColor(color)
                binding.technicalBlock.setBackgroundColor(color)
            }
        }
    }

    private fun listeningOnClickBtnSendForm() {
        binding.buttonSubmitApplication.setOnClickListener {
            navigateHelper.navigateTo(
                SendFormFragment.newInstance(
                    NAME_FRAGMENT,
                    caseId,
                    caseImage,
                    ArrayList(listCase)
                )
            )
        }
    }

    private fun listeningOnClickScreenshot() {
        navigateHelper.navigateTo(
            ScreenshotListFragment.newInstance(
                ArrayList(listScreenshot),
                caseColor,
                caseId,
                caseImage,
                ArrayList(listCase)
            )
        )
    }


    private fun listeningOnClickEmailCompany() {
        val emailCompany = getString(R.string.text_email_company)
        binding.emailCompany.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:$emailCompany")
            }
            startActivity(intent)
        }
    }

    private fun listeningOnClickNextCase(newCaseId: String, newCaseImage: String) {
        binding.blockNextCase.setOnClickListener {
            navigateHelper.navigateTo(
                newInstance(
                    newCaseId,
                    newCaseImage,
                    ArrayList(listCase)
                )
            )
        }
    }

    private fun listeningOnClickGoGeneralPage() {
        binding.textGoBackToMainPage.setOnClickListener {
            navigateHelper.navigateTo(MainFragment.newInstance())
        }
    }

    private fun listeningOnClickBtnBack() {
        binding.btnBack.setOnClickListener {
            navigateHelper.navigateTo(ListCaseFragment.newInstance())
        }
    }

    private fun listeningOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback {
            navigateHelper.navigateTo(ListCaseFragment.newInstance())
        }
    }

    private fun parseArgs() {
        val args = requireArguments()
        caseId = args.getString(EXTRA_CASE_ID).toString()
        caseImage = args.getString(EXTRA_CASE_IMAGE).toString()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            args.getParcelableArrayList(
                EXTRA_CASES,
                Case::class.java
            )?.let {
                listCase = it.toList()
            }
        } else {
            args.getParcelableArrayList<Case>(
                EXTRA_CASES
            )?.let {
                listCase = it.toList()
            }
        }
    }

    companion object {
        const val NAME_FRAGMENT = "case_detail"
        private const val EXTRA_CASE_ID = "case_id"
        private const val EXTRA_CASE_IMAGE = "case_image"
        private const val EXTRA_CASES = "cases"

        fun newInstance(
            caseId: String, caseImage: String, cases: ArrayList<Case>
        ): CaseDetailFragment {
            return CaseDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_CASE_ID, caseId)
                    putString(EXTRA_CASE_IMAGE, caseImage)
                    putParcelableArrayList(EXTRA_CASES, cases)
                }
            }
        }
    }
}