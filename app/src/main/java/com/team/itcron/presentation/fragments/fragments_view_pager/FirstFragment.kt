package com.team.itcron.presentation.fragments.fragments_view_pager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.team.itcron.R
import com.team.itcron.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding: FragmentFirstBinding
        get() = _binding ?: throw RuntimeException(
            getString(R.string.text_is_null_binding_first_fragment)
        )

    // ****** lifecycle *****
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            btnNextPage.setOnClickListener {
                val viewPager = requireActivity().findViewById<ViewPager2>(R.id.view_pager)
                viewPager.currentItem = 1
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    // ****** lifecycle *****
}