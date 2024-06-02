package com.team.itcron.presentation.fragments

import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.buildSpannedString
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.team.itcron.R
import com.team.itcron.databinding.FragmentCompanyBinding
import com.team.itcron.domain.models.Review
import com.team.itcron.presentation.adapter_delegation.ReviewDiffCallback
import com.team.itcron.presentation.adapter_delegation.reviewDelegate
import com.team.itcron.presentation.navigate.NavigateHelper
import com.team.itcron.presentation.view_models.CompanyViewModel
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent

class CompanyFragment : Fragment(), KoinComponent {

    private var _binding: FragmentCompanyBinding? = null
    private val binding: FragmentCompanyBinding
        get() = _binding ?: throw RuntimeException(
            getString(R.string.text_is_null_binding_view_company_fragment)
        )

    private val navigateHelper: NavigateHelper by lazy {
        (requireActivity() as? NavigateHelper)
            ?: throw RuntimeException(
                getString(R.string.text_error_no_implements_interface)
            )
    }

    private val glide by lazy { Glide.with(this) }

    private val companyViewModel by viewModel<CompanyViewModel>()

    private val adapterReview by lazy {
        AsyncListDifferDelegationAdapter(
            ReviewDiffCallback(),
            reviewDelegate(glide)
        )
    }

    private val params = LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT,
    ).apply {
        val dpToPx = Resources.getSystem().displayMetrics.density
        setMargins((6 * dpToPx).toInt(), 0, (6 * dpToPx).toInt(), 0)
    }

    private var onPageChangeCallback: ViewPager2.OnPageChangeCallback? = null

    // ****** lifecycle *****
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompanyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listeningOnClickCompanyLink()
        listeningOnClickBtnBack()
        companyViewModel.getReviewInfo()
        changeTextSendPortfolioEmail()
        observeViewModel()
        listeningOnClickBtnSendForm()
    }

    override fun onStop() {
        super.onStop()
        onPageChangeCallback?.let {
            binding.reviewList.unregisterOnPageChangeCallback(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    // ****** lifecycle *****

    private fun observeViewModel() {
        lifecycleScope.launch {
            companyViewModel.reviewInfo.flowWithLifecycle(lifecycle)
                .filterNotNull()
                .collect { reviewInfo ->
                    binding.reviewList.adapter = adapterReview
                    val newList = createFakeReviewList(reviewInfo.data)
                    adapterReview.items = newList.toList()
                    binding.reviewList.currentItem = 1
                    createDots(reviewInfo.data.size)
                    onPageChangeCallback?.let {
                        binding.reviewList.registerOnPageChangeCallback(it)
                    }
                }
        }

        lifecycleScope.launch {
            companyViewModel.isOnline.flowWithLifecycle(lifecycle)
                .collect {
                    if (!it) {
                        navigateHelper.navigateTo(NoInternetFragment.newInstance())
                    }
                }
        }

        lifecycleScope.launch {
            companyViewModel.reviewInfoLoadIsError.flowWithLifecycle(lifecycle)
                .collect {
                    if (it) {
                        binding.blockReviews.visibility = View.GONE
                    }
                }
        }

        lifecycleScope.launch {
            companyViewModel.reviewHttpException.flowWithLifecycle(lifecycle)
                .collect {
                    if (it) {
                        binding.blockReviews.visibility = View.GONE
                    }
                }
        }
    }

    private fun createDots(countDots: Int) {
        binding.pointsViewPager.removeAllViews()
        val dots = Array(countDots) {
            val imageView = ImageView(requireContext())
            imageView.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.state_image_dot
                )
            )
            imageView
        }
        setNoActiveAllDots(dots)
        setFirstActiveDot(dots)
        listeningChangeViewPager(dots)
    }

    private fun changeTextSendPortfolioEmail() {
        val textView = binding.textSendPortfolio
        textView.movementMethod = LinkMovementMethod.getInstance()
        val link = getString(R.string.text_email_company_hr)
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:$link")
                }
                startActivity(intent)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                val typeface = ResourcesCompat.getFont(
                    requireContext(), R.font.gothampro_medium
                )
                ds.typeface = typeface
                ds.isUnderlineText = false
                ds.textSize = TEXT_EMAIL_HR_COMPANY_SIZE
                ds.color = ContextCompat.getColor(requireActivity(), R.color.color_main_second)
            }
        }
        val spannable = buildSpannedString {
            append(getString(R.string.text_send_portfolio))
            append(" ")
            append(link, clickableSpan, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        textView.text = spannable
    }

    private fun listeningChangeViewPager(dots: Array<ImageView>) {
        onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                dots.mapIndexed { index, imageView ->
                    imageView.isSelected = position - 1 == index
                }
                if (position == 0) {
                    binding.reviewList.setCurrentItem(
                        binding.reviewList.adapter?.itemCount?.minus(2) ?: 1,
                        false
                    )
                }
                if (position == (binding.reviewList.adapter?.itemCount?.minus(1) ?: 1)) {
                    binding.reviewList.setCurrentItem(1, false)
                }
                super.onPageSelected(position)
            }
        }
    }

    private fun createFakeReviewList(reviews: List<Review>): List<Review> {
        val newList = mutableListOf<Review>()
        newList.add(reviews.last())
        newList.addAll(reviews)
        newList.add(reviews.first())
        return newList
    }

    private fun setFirstActiveDot(dots: Array<ImageView>) {
        dots[0].isSelected = true
    }

    private fun setNoActiveAllDots(dots: Array<ImageView>) {
        dots.forEach {
            it.isSelected = false
            binding.pointsViewPager.addView(it, params)
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

    private fun listeningOnClickCompanyLink() {
        with(binding) {
            facebookLogo.setOnClickListener {
                switchingToApplication(
                    idApp = getString(R.string.text_id_app_facebook),
                    linkCompany = getString(R.string.text_link_facebook)
                )
            }
            instagramLogo.setOnClickListener {
                switchingToApplication(
                    idApp = getString(R.string.text_id_app_instagram),
                    linkCompany = getString(R.string.text_link_instagram)
                )
            }
            telegramLogo.setOnClickListener {
                switchingToApplication(
                    idApp = getString(R.string.text_id_app_telegram),
                    linkCompany = getString(R.string.text_link_telegram)
                )
            }
            linkFacebook.setOnClickListener {
                switchingToApplication(
                    idApp = getString(R.string.text_id_app_facebook),
                    linkCompany = getString(R.string.text_link_facebook)
                )
            }
            linkInstagram.setOnClickListener {
                switchingToApplication(
                    idApp = getString(R.string.text_id_app_instagram),
                    linkCompany = getString(R.string.text_link_instagram)
                )
            }
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
            telegramLinkSecond.setOnClickListener {
                switchingToApplication(
                    idApp = getString(R.string.text_id_app_telegram),
                    linkCompany = getString(R.string.text_link_telegram)
                )
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

    companion object {
        const val NAME_FRAGMENT = "company"
        private const val TEXT_EMAIL_HR_COMPANY_SIZE = 50f

        fun newInstance(): CompanyFragment {
            return CompanyFragment()
        }
    }
}