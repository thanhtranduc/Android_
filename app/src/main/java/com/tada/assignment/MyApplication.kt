package com.tada.assignment

import com.tada.base.application.BaseApplication
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
    }
}