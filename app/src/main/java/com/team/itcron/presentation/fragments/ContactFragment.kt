package com.team.itcron.presentation.fragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.util.Calendar
import android.icu.util.TimeZone
import android.net.Uri
import android.os.Bundle
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.buildSpannedString
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.team.itcron.R
import com.team.itcron.databinding.FragmentContactBinding
import com.team.itcron.domain.models.Requisite
import com.team.itcron.presentation.adapter_delegation.RequisiteDiffCallback
import com.team.itcron.presentation.adapter_delegation.requisiteDelegate
import com.team.itcron.presentation.navigate.NavigateHelper
import com.team.itcron.presentation.view_models.ContactViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent

class ContactFragment : Fragment(), KoinComponent {

    private var _binding: FragmentContactBinding? = null
    private val binding: FragmentContactBinding
        get() = _binding ?: throw RuntimeException(
            getString(R.string.text_is_null_binding_view_contact_fragment)
        )

    private val navigateHelper: NavigateHelper by lazy {
        (requireActivity() as? NavigateHelper)
            ?: throw RuntimeException(
                getString(R.string.text_error_no_implements_interface)
            )
    }

    private val contactViewModel by viewModel<ContactViewModel>()

    private val adapter by lazy {
        AsyncListDifferDelegationAdapter(
            RequisiteDiffCallback(),
            requisiteDelegate()
        )
    }

    private var sharingListRequisite: List<Requisite> = emptyList<Requisite>()

    // ****** lifecycle *****
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listRequisite.adapter = adapter
        contactViewModel.getListRequisite()
        listeningOnClickBtnBack()
        listeningOnClickBtnSubmitApplication()
        onClickPhoneCompany()
        listeningOnClickCompanyLink()
        changeTextSendPortfolioEmail()
        observeViewModel()
        getCurrentDay()
        listeningOnClickSharingBtn()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    // ****** lifecycle *****

    private fun observeViewModel() {
        lifecycleScope.launch {
            contactViewModel.listRequisite.flowWithLifecycle(lifecycle)
                .collect { listRequisite ->
                    adapter.items = listRequisite
                    sharingListRequisite = listRequisite
                }
        }
    }

    private fun listeningOnClickSharingBtn() {
        binding.btnSharing.setOnClickListener {
            sharingRequisitesCompany()
        }
    }

    private fun sharingRequisitesCompany() {
        val sendIntent = Intent(Intent.ACTION_SEND)
        sendIntent.putExtra(Intent.EXTRA_TEXT, formingRequisiteMessage())
        sendIntent.type = "text/plain"
        requireContext().startActivity(
            Intent.createChooser(
                sendIntent,
                getString(R.string.text_sharing)
            )
        )
    }

    private fun formingRequisiteMessage(): String {
        var result = ""
        sharingListRequisite.forEach {
            result += "${it.name}: ${it.value}\n"
        }
        return result
    }

    private fun getCurrentDay() {
        val mscTimeZone: TimeZone = TimeZone.getTimeZone(
            getString(R.string.text_time_zone_msk)
        )
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeZone = mscTimeZone
        with(binding) {
            when (calendar.get(Calendar.DAY_OF_WEEK)) {
                Calendar.SUNDAY -> {
                    blockDayWeekend.isSelected = true
                }

                Calendar.MONDAY -> {
                    blockDayMonday.isSelected = true
                }

                Calendar.TUESDAY -> {
                    blockDayTuesday.isSelected = true
                }

                Calendar.WEDNESDAY -> {
                    blockDayWednesday.isSelected = true
                }

                Calendar.THURSDAY -> {
                    blockDayThursday.isSelected = true
                }

                Calendar.FRIDAY -> {
                    blockDayFriday.isSelected = true
                }

                Calendar.SATURDAY -> {
                    binding.blockDayWeekend.isSelected = true
                }
            }
        }
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

    private fun listeningOnClickCompanyLink() {
        with(binding) {
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

    private fun listeningOnClickBtnBack() {
        binding.btnBack.setOnClickListener {
            navigateHelper.navigateTo(MainFragment.newInstance())
        }
    }

    private fun listeningOnClickBtnSubmitApplication() {
        binding.buttonSubmitApplication.setOnClickListener {
            navigateHelper.navigateTo(SendFormFragment.newInstance(NAME_FRAGMENT))
        }
    }

    private fun onClickPhoneCompany() {
        binding.textPhoneCompany.setOnClickListener {
            callPhoneCompany()
        }
    }

    private fun callPhoneCompany() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CALL_PHONE),
                REQUEST_CODE_CALL
            )
        } else {
            val intent = Intent(Intent.ACTION_CALL)
            val number = getString(R.string.text_company_phone)
            intent.data = Uri.parse("tel:$number")
            startActivity(intent)
        }
    }

    companion object {
        const val NAME_FRAGMENT = "contact"

        private const val REQUEST_CODE_CALL = 10
        private const val TEXT_EMAIL_HR_COMPANY_SIZE = 50f

        fun newInstance(): ContactFragment {
            return ContactFragment()
        }
    }
}