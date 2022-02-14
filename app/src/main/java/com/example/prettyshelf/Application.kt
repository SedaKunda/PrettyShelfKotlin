package com.example.prettyshelf

import android.app.Application

class PrettyShelfApplication: Application() {
    val appComponent: ApplicationComponent = DaggerApplicationComponent.create()
}