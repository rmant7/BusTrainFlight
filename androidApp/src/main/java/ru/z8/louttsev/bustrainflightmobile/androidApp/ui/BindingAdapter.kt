package ru.z8.louttsev.bustrainflightmobile.androidApp.ui

import android.widget.TextView
import androidx.databinding.BindingAdapter
import ru.z8.louttsev.bustrainflightmobile.androidApp.model.data.TransportationType

@BindingAdapter("transportationText")
fun setTransportationText(textView: TextView, transportationType: TransportationType?) {
    transportationType?.let {
        textView.text = textView.context.getString(it.stringResourceId)
    }
}