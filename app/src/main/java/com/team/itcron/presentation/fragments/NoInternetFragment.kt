package com.team.itcron.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.team.itcron.R
import com.team.itcron.databinding.FragmentNoInternetBinding
import com.team.itcron.presentation.activity.NetworkChecker
import com.team.itcron.presentation.navigate.NavigateHelper

class NoInternetFragment : Fragment() {

    private var _binding: FragmentNoInternetBinding? = null
    private val binding: FragmentNoInternetBinding
        get() = _binding ?: throw RuntimeException(
            getString(R.string.text_is_null_binding_no_internet_fragment)
        )

    private val navigateHelper: NavigateHelper by lazy {
        (requireActivity() as? NavigateHelper)
            ?: throw RuntimeException(
                getString(R.string.text_error_no_implements_interface)
            )
    }

    private lateinit var networkChecker: NetworkChecker

    // ****** lifecycle *****
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoInternetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        networkChecker = NetworkChecker(requireContext())
        checkConnectToInternet()
        clickCloseBtn()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    // ****** lifecycle *****

    private fun checkConnectToInternet() {
        networkChecker.observe(viewLifecycleOwner) {
            if (it) {
                navigateHelper.navigateTo(MainFragment.newInstance())
            }
        }
    }

    private fun clickCloseBtn() {
        binding.btnClose.setOnClickListener {
            navigateHelper.navigateTo(MainFragment.newInstance())
        }
    }

    companion object {
        fun newInstance(): NoInternetFragment {
            return NoInternetFragment()
        }
    }
}