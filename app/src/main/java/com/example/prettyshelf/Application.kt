package com.example.prettyshelf

import android.app.Application
import com.example.prettyshelf.di.ApplicationComponent
import com.example.prettyshelf.di.DaggerApplicationComponent

class PrettyShelfApplication: Application() {
    val appComponent: ApplicationComponent = DaggerApplicationComponent.create()
}