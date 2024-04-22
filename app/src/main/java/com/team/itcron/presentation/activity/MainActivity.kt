package com.team.itcron.presentation.activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.team.itcron.R
import com.team.itcron.databinding.ActivityMainBinding
import com.team.itcron.presentation.fragments.MainFragment
import com.team.itcron.presentation.fragments.NoInternetFragment
import com.team.itcron.presentation.fragments.ViewPagerFragment
import com.team.itcron.presentation.navigate.NavigateHelper
import com.team.itcron.presentation.view_models.MainViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), NavigateHelper {

    private lateinit var binding: ActivityMainBinding

    private val navigator: Navigator by lazy {
        AppNavigator(
            this,
            R.id.main_fragment_container,
            supportFragmentManager,
        )
    }

    private val cicerone: Cicerone<Router> by lazy {
        Cicerone.create()
    }

    private val navigatorHolder: NavigatorHolder by lazy {
        cicerone.getNavigatorHolder()
    }

    private val router: Router by lazy {
        cicerone.router
    }

    private val mainViewModel by viewModel<MainViewModel>()

    private lateinit var networkChecker: NetworkChecker

    private lateinit var splashScreen: SplashScreen

    // ****** lifecycle *****
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        splashScreen = installSplashScreen().apply {
            setKeepOnScreenCondition(SplashScreen.KeepOnScreenCondition {
                mainViewModel.isLoadSplash.value
            })
        }
        mainViewModel.getMenu()
        setContentView(binding.root)
        observeViewModel()
        networkChecker = NetworkChecker(this)
        checkConnectToInternet()
        checkingActivationIsOnBoarding()
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
    // ****** lifecycle *****

    override fun navigateTo(fragment: Fragment) {
        router.navigateTo(FragmentScreen { fragment })
    }

    private fun checkConnectToInternet() {
        networkChecker.observe(this) {
            if (!it) {
                navigateTo(NoInternetFragment.newInstance())
            }
        }
    }

    private fun onBoardingFinished(): Boolean {
        val sharedPreferences = this.getSharedPreferences(
            getString(R.string.text_key_shared_preferences_on_boarding),
            Context.MODE_PRIVATE
        )
        return sharedPreferences.getBoolean(
            getString(R.string.text_extra_finished_on_boarding),
            false
        )
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            mainViewModel.menu.flowWithLifecycle(lifecycle)
                .filterNotNull()
                .onEach { menu ->
                    Log.d("MainActivity", menu.toString())
                }.collect()
        }
    }

    private fun checkingActivationIsOnBoarding() {
        if (onBoardingFinished()) {
            navigateTo(MainFragment.newInstance())
        } else {
            navigateTo(ViewPagerFragment.newInstance())
        }
    }
}