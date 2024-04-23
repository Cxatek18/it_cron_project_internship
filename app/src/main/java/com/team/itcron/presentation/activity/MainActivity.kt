package com.team.itcron.presentation.activity

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
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
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainActivity : AppCompatActivity(), NavigateHelper, KoinComponent {

    private lateinit var binding: ActivityMainBinding

    private val navigator: Navigator by lazy {
        AppNavigator(
            this,
            R.id.main_fragment_container,
            supportFragmentManager,
        )
    }

    private val navigatorHolder: NavigatorHolder by inject<NavigatorHolder>()
    private val router: Router by inject<Router>()
    private val networkChecker: NetworkChecker by inject<NetworkChecker>()

    // ****** lifecycle *****
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        startSplashScreen()
        setContentView(binding.root)
        checkConnectToInternet()
        if (onBoardingFinished()) {
            navigateTo(MainFragment.newInstance())
        } else {
            navigateTo(ViewPagerFragment.newInstance())
        }
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

    private fun startSplashScreen() {
        installSplashScreen()
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
}