package com.sample.analytics

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig

class MainActivity : AppCompatActivity() {


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    YandexMetrica.activate(
        this,
        YandexMetricaConfig.newConfigBuilder("af6d8752-4179-46be-a948-8e86b0de6959")
            .build()
    )
  }
}
