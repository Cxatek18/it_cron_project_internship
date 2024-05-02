package com.team.itcron.di

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.team.itcron.presentation.activity.NetworkChecker
import com.team.itcron.presentation.view_models.ListCaseViewModel
import com.team.itcron.presentation.view_models.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    viewModel<MainViewModel> {
        MainViewModel(getMenuUseCase = get())
    }

    viewModel<ListCaseViewModel> {
        ListCaseViewModel(getCaseToListUseCase = get())
    }

    single<Cicerone<Router>> {
        Cicerone.create()
    }

    single<NavigatorHolder> {
        get<Cicerone<Router>>().getNavigatorHolder()
    }

    single<Router> {
        get<Cicerone<Router>>().router
    }

    factory<NetworkChecker> {
        NetworkChecker(context = get())
    }
}