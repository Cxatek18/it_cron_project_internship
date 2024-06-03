package ru.it_cron.intern3.presentation.navigate

import androidx.fragment.app.Fragment

interface NavigateHelper {

    fun navigateTo(fragment: Fragment)

    fun exit()
}