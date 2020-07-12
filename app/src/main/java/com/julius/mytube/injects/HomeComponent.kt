package com.julius.mytube.injects

import com.julius.mytube.viewmodels.home.HomeViewModel
import dagger.Component

@Component(modules = [HomeModule::class])
interface HomeComponent {
    fun inject(context: HomeViewModel)
}