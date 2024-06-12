package ru.it_cron.intern3.presentation.fragments

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent
import ru.it_cron.intern3.R
import ru.it_cron.intern3.databinding.FragmentReviewListBinding
import ru.it_cron.intern3.presentation.adapter_delegation.ReviewDiffCallback
import ru.it_cron.intern3.presentation.adapter_delegation.reviewDetailDelegate
import ru.it_cron.intern3.presentation.navigate.NavigateHelper
import ru.it_cron.intern3.presentation.view_models.ReviewListViewModel

class ReviewListFragment : Fragment(), KoinComponent {

    private var _binding: FragmentReviewListBinding? = null
    private val binding: FragmentReviewListBinding
        get() = _binding ?: throw RuntimeException(
            getString(R.string.text_is_null_binding_view_review_list_fragment)
        )

    private val navigateHelper: NavigateHelper by lazy {
        (requireActivity() as? NavigateHelper)
            ?: throw RuntimeException(
                getString(R.string.text_error_no_implements_interface)
            )
    }

    private val glide by lazy { Glide.with(this) }

    private val reviewViewModel by viewModel<ReviewListViewModel>()

    private val adapterReview by lazy {
        AsyncListDifferDelegationAdapter(
            ReviewDiffCallback(),
            reviewDetailDelegate(glide)
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
        _binding = FragmentReviewListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        reviewViewModel.getReviewInfo()
        observeViewModel()
        listeningOnClickCompanyLink()
        listeningOnClickPhoneCompany()
        listeningOnClickBtnBack()
        listeningOnClickBtnSendForm()
        listeningOnClickMoreReviewText()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    // ****** lifecycle *****

    private fun observeViewModel() {
        lifecycleScope.launch {
            reviewViewModel.reviewInfo.flowWithLifecycle(lifecycle)
                .filterNotNull()
                .collect { reviewInfo ->
                    with(binding) {
                        if (reviewInfo.data.size > COUNT_REVIEW) {
                            textMoreReview.visibility = View.VISIBLE
                            lineTextMoreReview.visibility = View.VISIBLE
                            reviewViewModel.getFirstReviews()
                        }
                    }
                }
        }

        lifecycleScope.launch {
            reviewViewModel.isOnline.flowWithLifecycle(lifecycle)
                .collect {
                    if (!it) {
                        navigateHelper.navigateTo(NoInternetFragment.newInstance())
                    }
                }
        }

        lifecycleScope.launch {
            reviewViewModel.reviewList.flowWithLifecycle(lifecycle)
                .collect { reviewList ->
                    binding.listReview.layoutManager = LinearLayoutManager(context).apply {
                        stackFromEnd = true
                    }
                    binding.listReview.adapter = adapterReview
                    adapterReview.items = reviewList
                    reviewViewModel.determiningIfThereAreReviews()
                }
        }

        lifecycleScope.launch {
            reviewViewModel.isNoReviews.flowWithLifecycle(lifecycle)
                .collect { isNoReviews ->
                    if (!isNoReviews) {
                        with(binding) {
                            textMoreReview.visibility = View.GONE
                            lineTextMoreReview.visibility = View.GONE
                        }
                    }
                }
        }
    }

    private fun listeningOnClickMoreReviewText() {
        binding.textMoreReview.setOnClickListener {
            reviewViewModel.getMoreReview()
        }
    }

    private fun listeningOnClickBtnSendForm() {
        binding.buttonSubmitApplication.setOnClickListener {
            navigateHelper.navigateTo(SendFormFragment.newInstance(NAME_FRAGMENT))
        }
    }

    private fun listeningOnClickBtnBack() {
        binding.btnBack.setOnClickListener {
            navigateHelper.navigateTo(MainFragment.newInstance())
        }
    }

    private fun listeningOnClickPhoneCompany() {
        binding.textPhoneCompany.setOnClickListener {
            callPhoneCompany()
        }
    }

    private fun listeningOnClickCompanyLink() {
        with(binding) {
            linkTelegram.setOnClickListener {
                switchingToApplication(
                    idApp = getString(R.string.text_id_app_telegram),
                    linkCompany = getString(R.string.text_link_telegram)
                )
            }
            emailCompany.setOnClickListener {
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:${emailCompany.text}")
                }
                startActivity(intent)
            }
        }
    }

    private fun switchingToApplication(idApp: String, linkCompany: String) {
        if (checkInstalledApp(idApp)) {
            val launchIntent: Intent? = requireActivity()
                .packageManager
                .getLaunchIntentForPackage(idApp)
            launchIntent?.let { startActivity(it) }
        } else {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW, Uri.parse(
                        linkCompany
                    )
                )
            )
        }
    }

    private fun checkInstalledApp(packageName: String): Boolean {
        val packageManager: PackageManager = requireContext().packageManager
        return isPackageInstalled(
            packageName, packageManager
        )
    }

    private fun isPackageInstalled(
        packageName: String,
        packageManager: PackageManager
    ): Boolean {
        return try {
            packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    private fun callPhoneCompany() {
        val intent = Intent(Intent.ACTION_DIAL)
        val number = getString(R.string.text_company_phone)
        intent.data = Uri.parse("tel:$number")
        startActivity(intent)
    }

    companion object {
        const val NAME_FRAGMENT = "review_list"
        private const val COUNT_REVIEW = 3

        fun newInstance(): ReviewListFragment {
            return ReviewListFragment()
        }
    }
}