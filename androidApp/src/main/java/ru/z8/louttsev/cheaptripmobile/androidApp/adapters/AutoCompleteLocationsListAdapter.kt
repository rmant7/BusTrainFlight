/**
 * CheapTrip Mobile
 * This is mobile client for LowCostsTrip server.
 */
package ru.z8.louttsev.cheaptripmobile.androidApp.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.LiveData
import io.github.aakira.napier.Napier
import ru.z8.louttsev.cheaptripmobile.androidApp.R
import ru.z8.louttsev.cheaptripmobile.androidApp.model.data.LocationData

/**
 * Declares adapter for location list as base for autocomplete input fields.
 *
 * @param liveData Observable source of locations data
 */
class AutoCompleteLocationsListAdapter(
    liveData: LiveData<List<LocationData>>
) : BaseAdapter(), Filterable {
    private var mLocations: List<LocationData>

    init {
        mLocations = liveData.value!!
        liveData.observeForever {
            Napier.d(mLocations.toString())
            mLocations = it
            notifyDataSetChanged()
        }
    }

    override fun getCount(): Int = mLocations.size

    override fun getItem(position: Int): Any = mLocations[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, cacheView: View?, parent: ViewGroup): View {
        val context = parent.context

        return TextView(context).apply {
            text = mLocations[position].name
            val padding = context.resources.getDimension(R.dimen.location_item_padding).toInt()
            setPadding(0, padding, 0, padding)
            textSize = 18f
            if (text.toString() == "Anywhere"){
                setBackgroundColor(ContextCompat.getColor(this.context, R.color.colorAccent))
                setTextColor(ContextCompat.getColor(this.context, R.color.white))
            }
            typeface = ResourcesCompat.getFont(context, R.font.montserrat)
        }
    }

    // this implementation does nothing,
    // filtering is performed by the model in TextChangedListener
    override fun getFilter(): Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?) = FilterResults()
//        override fun performFiltering(constraint: CharSequence?) = null

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            // nothing
        }
    }
}