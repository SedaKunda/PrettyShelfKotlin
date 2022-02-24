package com.example.prettyshelf

import android.app.Application
import android.content.Context
import com.example.prettyshelf.di.ApplicationComponent
import com.example.prettyshelf.di.DaggerApplicationComponent

open class PrettyShelfApplication : Application() {
    lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerApplicationComponent.builder().build()
    }
}

val Context.appComponent: ApplicationComponent
    get() = (applicationContext as PrettyShelfApplication).appComponent