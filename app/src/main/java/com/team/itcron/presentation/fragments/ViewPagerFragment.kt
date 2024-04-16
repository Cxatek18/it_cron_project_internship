package com.team.itcron.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.team.itcron.R
import com.team.itcron.databinding.FragmentViewPagerBinding
import com.team.itcron.presentation.fragments.fragments_view_pager.FirstFragment
import com.team.itcron.presentation.fragments.fragments_view_pager.SecondFragment
import com.team.itcron.presentation.fragments.fragments_view_pager.ThirdFragment
import com.team.itcron.presentation.viewPager.ViewPagerAdapter

class ViewPagerFragment : Fragment() {

    private var _binding: FragmentViewPagerBinding? = null
    private val binding: FragmentViewPagerBinding
        get() = _binding ?: throw RuntimeException(
            getString(R.string.text_is_null_binding_view_pager_fragment)
        )

    // ****** lifecycle *****
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentList = arrayListOf<Fragment>(
            FirstFragment(),
            SecondFragment(),
            ThirdFragment()
        )
        val adapter = ViewPagerAdapter(
            list = fragmentList,
            fragmentManager = requireActivity().supportFragmentManager,
            lifecycle = lifecycle
        )
        binding.viewPager.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    // ****** lifecycle *****

    companion object {
        fun newInstance(): ViewPagerFragment {
            return ViewPagerFragment()
        }
    }
}