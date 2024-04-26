package com.team.itcron.presentation.di

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.team.itcron.presentation.activity.NetworkChecker
import org.koin.dsl.module

val presentationModule = module {

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