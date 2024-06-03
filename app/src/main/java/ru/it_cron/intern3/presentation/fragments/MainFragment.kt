package ru.it_cron.intern3.presentation.fragments

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import ru.it_cron.intern3.R
import ru.it_cron.intern3.databinding.FragmentMainBinding
import ru.it_cron.intern3.presentation.navigate.NavigateHelper

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding ?: throw RuntimeException(
            getString(R.string.text_is_null_binding_main_fragment)
        )

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
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setColorTheWhiteAllPoints()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listeningOnClickPoints()
        listeningOnClickEmailCompany()
        listeningOnClickCompanyLink()
        listeningOnClickBtnSendForm()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        requireActivity().finish()
    }
    // ****** lifecycle *****

    private fun listeningOnClickBtnSendForm() {
        binding.btnSendRequest.setOnClickListener {
            navigateHelper.navigateTo(SendFormFragment.newInstance(NAME_FRAGMENT))
        }
    }

    private fun listeningOnClickCompanyLink() {
        with(binding) {
            facebookLink.setOnClickListener {
                switchingToApplication(
                    idApp = getString(R.string.text_id_app_facebook),
                    linkCompany = getString(R.string.text_link_facebook)
                )
            }
            instagramLink.setOnClickListener {
                switchingToApplication(
                    idApp = getString(R.string.text_id_app_instagram),
                    linkCompany = getString(R.string.text_link_instagram)
                )
            }
            telegramLink.setOnClickListener {
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

    private fun listeningOnClickEmailCompany() {
        val emailCompany = getString(R.string.text_email_company)
        binding.companyEmail.setOnClickListener {
            setColorTheWhiteAllPoints()
            setColorText(binding.companyEmail, R.color.color_text_click)
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:$emailCompany")
            }
            startActivity(intent)
        }
    }

    private fun listeningOnClickPoints() {
        with(binding) {
            textCase.setOnClickListener {
                setColorTheWhiteAllPoints()
                setColorText(textCase, R.color.color_text_click)
                navigateHelper.navigateTo(ListCaseFragment.newInstance())
            }
            textCompany.setOnClickListener {
                setColorTheWhiteAllPoints()
                setColorText(textCompany, R.color.color_text_click)
                navigateHelper.navigateTo(CompanyFragment.newInstance())
            }
            textServices.setOnClickListener {
                setColorTheWhiteAllPoints()
                setColorText(textServices, R.color.color_text_click)
            }
            textContacts.setOnClickListener {
                setColorTheWhiteAllPoints()
                setColorText(textContacts, R.color.color_text_click)
                navigateHelper.navigateTo(ContactFragment.newInstance())
            }
        }
    }

    private fun setColorText(textItem: AppCompatTextView, colorId: Int) {
        textItem.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                colorId
            )
        )
    }

    private fun setColorTheWhiteAllPoints() {
        with(binding) {
            setColorText(textCase, R.color.white)
            setColorText(textCompany, R.color.white)
            setColorText(textServices, R.color.white)
            setColorText(textContacts, R.color.white)
            setColorText(companyEmail, R.color.white)
        }
    }

    companion object {
        const val NAME_FRAGMENT = "main"

        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }
}