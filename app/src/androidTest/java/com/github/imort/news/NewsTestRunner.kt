package com.github.imort.news

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication

@Suppress("unused")
class NewsTestRunner : AndroidJUnitRunner() {
    override fun newApplication(cl: ClassLoader, appName: String, context: Context): Application =
        super.newApplication(cl, HiltTestApplication::class.java.name, context)
}
