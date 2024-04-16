package com.team.itcron.presentation.fragments.fragments_view_pager

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.team.itcron.R
import com.team.itcron.databinding.FragmentThirdBinding
import com.team.itcron.presentation.fragments.MainFragment
import com.team.itcron.presentation.navigate.NavigateHelper

class ThirdFragment : Fragment() {

    private var _binding: FragmentThirdBinding? = null
    private val binding: FragmentThirdBinding
        get() = _binding ?: throw RuntimeException(
            getString(R.string.text_is_null_binding_third_fragment)
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
        _binding = FragmentThirdBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            btnNextMainPage.setOnClickListener {
                navigateHelper.navigateTo(MainFragment.newInstance())
                onBoardingFinished()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    // ****** lifecycle *****

    private fun onBoardingFinished() {
        val sharedPreferences = requireActivity().getSharedPreferences(
            getString(R.string.text_key_shared_preferences_on_boarding),
            Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        editor.putBoolean(
            getString(R.string.text_extra_finished_on_boarding),
            true
        )
        editor.apply()
    }
}