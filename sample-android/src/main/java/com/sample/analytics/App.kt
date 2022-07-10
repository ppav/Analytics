package com.sample.analytics

import android.app.Application

class App : Application() {

  companion object {
    lateinit var INSTANCE: App
      private set
  }

  override fun onCreate() {
    INSTANCE = this
    super.onCreate()

  }

}
