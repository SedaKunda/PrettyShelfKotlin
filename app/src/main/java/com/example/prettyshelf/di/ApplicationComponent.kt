package com.example.prettyshelf.di

import com.example.prettyshelf.main.MainActivity
import com.example.prettyshelf.main.MainActivityCompose
import com.example.prettyshelf.main.MainViewModel
import com.example.prettyshelf.networking.NetworkModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface ApplicationComponent {
    fun inject(activity: MainActivity)
    fun inject(activity: MainActivityCompose)
    fun getViewModel() : MainViewModel
}