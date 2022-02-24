package com.example.prettyshelf.di

import com.example.prettyshelf.networking.NetworkModule
import com.example.prettyshelf.ui.screens.search.ISBNSearchActivity
import com.example.prettyshelf.ui.screens.search.ISBNSearchComposeActivity
import com.example.prettyshelf.ui.screens.search.ISBNSearchViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface ApplicationComponent {
    fun inject(activity: ISBNSearchActivity)
    fun inject(activity: ISBNSearchComposeActivity)
    fun getViewModel(): ISBNSearchViewModel
}