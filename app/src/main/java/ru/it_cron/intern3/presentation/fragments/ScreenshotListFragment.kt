package ru.it_cron.intern3.presentation.fragments

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.activity.addCallback
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import ru.it_cron.intern3.R
import ru.it_cron.intern3.databinding.FragmentScreenshotListBinding
import ru.it_cron.intern3.domain.models.Case
import ru.it_cron.intern3.presentation.adapter_delegation.ScreenshotImageDiffCallback
import ru.it_cron.intern3.presentation.adapter_delegation.screenImageDelegate
import ru.it_cron.intern3.presentation.navigate.NavigateHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ScreenshotListFragment : Fragment() {

    private var _binding: FragmentScreenshotListBinding? = null
    private val binding: FragmentScreenshotListBinding
        get() = _binding ?: throw RuntimeException(
            getString(R.string.text_is_null_binding_view_screenshot_list_fragment)
        )

    private val navigateHelper: NavigateHelper by lazy {
        (requireActivity() as? NavigateHelper)
            ?: throw RuntimeException(
                getString(R.string.text_error_no_implements_interface)
            )
    }

    private val glide by lazy { Glide.with(this) }

    private lateinit var images: List<String>
    private lateinit var color: String
    private lateinit var caseId: String
    private lateinit var caseImage: String
    private lateinit var listCase: List<Case>

    private val adapterViewPager by lazy {
        AsyncListDifferDelegationAdapter(
            ScreenshotImageDiffCallback(),
            screenImageDelegate(glide)
        )
    }

    private val scope = CoroutineScope(Job() + Dispatchers.Main)
    private var job: Job? = null

    // ****** lifecycle *****
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScreenshotListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parseArgs()
        setBackgroundColorFragment()
        binding.viewPager.adapter = adapterViewPager
        adapterViewPager.items = images
        createProgressBar()
        changeScreenShotToTime()
        listeningOnBackPressed()
        listeningOnClickBtnClose()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    // ****** lifecycle *****

    private fun createProgressBar() {
        repeat(images.size) {
            val progressBar = ProgressBar(
                requireContext(),
                null,
                0,
                com.bumptech.glide.R.style.Base_Widget_AppCompat_ProgressBar_Horizontal
            )
            val params = LinearLayout.LayoutParams(
                WIDTH_PROGRESSBAR,
                HEIGHT_PROGRESSBAR
            )
            progressBar.progress = 0
            progressBar.max = TIME_MILLISECONDS.toInt()
            params.marginEnd = MARGIN_END_PROGRESSBAR
            progressBar.layoutParams = params
            progressBar.progressDrawable = AppCompatResources.getDrawable(
                requireContext(), R.drawable.progress_bar_style
            )
            binding.progressLinearLayout.addView(progressBar)
        }
    }

    private fun setBackgroundColorFragment() {
        val color: Int = Color.parseColor("#${color}")
        binding.screenshotList.setBackgroundColor(color)
    }

    private fun changeScreenShotToTime() {
        var itemViewPagerIdx = 0
        var counter = 0
        job = scope.launch {
            while (true) {
                val currentItemViewPager = binding.viewPager.currentItem
                itemViewPagerIdx = currentItemViewPager

                val currentScreenshot = binding.progressLinearLayout.getChildAt(
                    currentItemViewPager
                ) as ProgressBar
                currentScreenshot.progress = counter

                if (counter > 0 && counter % TIME_MILLISECONDS.toInt() == 0) {
                    if (currentItemViewPager == binding.viewPager.adapter!!.itemCount - 1) {
                        binding.progressLinearLayout.removeAllViews()
                        navigateHelper.navigateTo(
                            CaseDetailFragment.newInstance(
                                caseId,
                                caseImage,
                                ArrayList(listCase)
                            )
                        )
                        job?.cancel()
                        break
                    } else {
                        binding.viewPager.currentItem = currentItemViewPager + 1
                        counter = 0
                    }
                }

                delay(WORK_MILLISECONDS)
                counter += NUMBER_PROGRESS_INCREASES
            }
        }

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                counter = 0
                val lastScreenshot = binding.progressLinearLayout.getChildAt(
                    itemViewPagerIdx
                ) as ProgressBar
                lastScreenshot.progress = counter
            }
        })
    }

    private fun listeningOnClickBtnClose() {
        binding.btnClose.setOnClickListener {
            binding.progressLinearLayout.removeAllViews()
            job?.cancel()
            navigateHelper.navigateTo(
                CaseDetailFragment.newInstance(
                    caseId,
                    caseImage,
                    ArrayList(listCase)
                )
            )
        }
    }

    private fun listeningOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback {
            binding.progressLinearLayout.removeAllViews()
            job?.cancel()
            navigateHelper.navigateTo(
                CaseDetailFragment.newInstance(
                    caseId,
                    caseImage,
                    ArrayList(listCase)
                )
            )
        }
    }

    private fun parseArgs() {
        val args = requireArguments()
        images = args.getStringArrayList(EXTRA_IMAGES)?.toList() as List<String>
        color = args.getString(EXTRA_COLOR).toString()
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
        private const val EXTRA_IMAGES = "images"
        private const val EXTRA_COLOR = "color"
        private const val EXTRA_CASE_ID = "case_id"
        private const val EXTRA_CASE_IMAGE = "case_image"
        private const val EXTRA_CASES = "cases"
        private const val TIME_MILLISECONDS = 3000L
        private const val WORK_MILLISECONDS = 5L
        private const val WIDTH_PROGRESSBAR = 150
        private const val HEIGHT_PROGRESSBAR = 10
        private const val MARGIN_END_PROGRESSBAR = 16
        private const val NUMBER_PROGRESS_INCREASES = 10

        fun newInstance(
            images: ArrayList<String>,
            color: String,
            caseId: String,
            caseImage: String,
            cases: ArrayList<Case>
        ): ScreenshotListFragment {
            return ScreenshotListFragment().apply {
                arguments = Bundle().apply {
                    putStringArrayList(EXTRA_IMAGES, images)
                    putString(EXTRA_COLOR, color)
                    putString(EXTRA_CASE_ID, caseId)
                    putString(EXTRA_CASE_IMAGE, caseImage)
                    putParcelableArrayList(EXTRA_CASES, cases)
                }
            }
        }
    }
}