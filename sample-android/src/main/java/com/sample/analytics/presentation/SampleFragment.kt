package com.sample.analytics.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.sample.analytics.analytics.AnalyticsProvider
import com.sample.analytics.analytics.event.CheckoutAnalytics.purchase
import com.sample.analytics.analytics.event.CheckoutAnalytics.selectAddress
import com.sample.analytics.analytics.event.CheckoutAnalytics.startCheckout

class SampleFragment : Fragment() {

  private val analytics = AnalyticsProvider.provide()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    return ComposeView(requireContext()).apply {
      setContent {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

          Button(modifier = padding, onClick = { analytics.track(startCheckout(3.0, "coffee")) }) {
            Text(text = "Start checkout")
          }

          Button(modifier = padding, onClick = { analytics.track(selectAddress("coffee")) }) {
            Text(text = "Address selected")
          }

          Button(modifier = padding, onClick = { analytics.track(purchase(3.0, "coffee")) }) {
            Text(text = "Purchase")
          }
        }
      }
    }
  }

  val padding = Modifier.padding(top = 16.dp)
}