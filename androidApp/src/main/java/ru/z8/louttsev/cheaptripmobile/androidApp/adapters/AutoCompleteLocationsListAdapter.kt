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
import dev.icerock.moko.mvvm.livedata.LiveData
import ru.z8.louttsev.cheaptripmobile.androidApp.R
import ru.z8.louttsev.cheaptripmobile.shared.model.data.Location

/**
 * Declares adapter for location list as base for autocomplete input fields.
 *
 * @param liveData Observable source of locations data
 */
class AutoCompleteLocationsListAdapter(
    liveData: LiveData<List<Location>>
) : BaseAdapter(), Filterable {
    private var mLocations: List<Location>

    init {
        mLocations = liveData.value
        liveData.addObserver {
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
        }
    }

    // this implementation does nothing,
    // filtering is performed by the model in TextChangedListener
    override fun getFilter(): Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?) = FilterResults()

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            // nothing
        }
    }
}