/**
 * CheapTrip Mobile
 * This is mobile client for LowCostsTrip server.
 */
package com.travelapp.bustrainflightmobile.androidApp.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
import io.github.aakira.napier.Napier
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import com.travelapp.bustrainflightmobile.androidApp.databinding.ItemRouteAnywhereBinding
import com.travelapp.bustrainflightmobile.androidApp.databinding.NativeAdViewAnywhereBinding
import com.travelapp.bustrainflightmobile.androidApp.model.LocationRepository
import com.travelapp.bustrainflightmobile.androidApp.model.data.Route

class AnywhereListAdapter(
    liveData: MutableLiveData<MutableList<Pair<Int, MutableList<Route>>>>,
//    val handler: DestinationSelectedHandler
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mDestinationRoutes: MutableList<Pair<Int, MutableList<Route>>>

    private val AD_ITEM_INTERVAL = 5
    private val AD_VIEW_TYPE = 1
    private val DATA_VIEW_TYPE = 2

    init {
        mDestinationRoutes = liveData.value!!
        liveData.observeForever {
            mDestinationRoutes = it
            notifyDataSetChanged()
        }
    }

    fun isListEmpty() = mDestinationRoutes.isEmpty()

    fun addRoutes(routes: MutableList<Pair<Int, MutableList<Route>>>) {
        if (routes.isNotEmpty()) {
            val startPosition = mDestinationRoutes.size
            mDestinationRoutes.addAll(routes)
            notifyItemRangeInserted(startPosition, routes.size)
//            notifyDataSetChanged()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0 || (position + 1) % AD_ITEM_INTERVAL == 0) {
            AD_VIEW_TYPE
        } else {
            DATA_VIEW_TYPE
        }
    }

    private fun calculateDataIndex(position: Int): Int {
        return if (position >= AD_ITEM_INTERVAL) {
            position - position / AD_ITEM_INTERVAL
        } else {
            position
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == DATA_VIEW_TYPE) {
            val binding = ItemRouteAnywhereBinding.inflate(LayoutInflater.from(parent.context))
            RouteViewHolder(binding)
        } else {
            val binding = NativeAdViewAnywhereBinding.inflate(LayoutInflater.from(parent.context))
            AdViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RouteViewHolder) {
            val dataIndex = calculateDataIndex(position)
            val currentDestination = mDestinationRoutes[dataIndex]
            holder.bind(currentDestination)
        } else if (holder is AdViewHolder) {
            holder.bind()
        }
    }

    override fun getItemCount(): Int {
        val adCount = (mDestinationRoutes.size - 1) / AD_ITEM_INTERVAL
        val dataCount = mDestinationRoutes.size
        return dataCount + adCount
    }

    class RouteViewHolder(val binding: ItemRouteAnywhereBinding) :
        RecyclerView.ViewHolder(binding.root),
        KoinComponent {

        private val locationRepository: LocationRepository by inject()
        fun bind(currentDestination: Pair<Int, MutableList<Route>>) {
            with(binding) {
                root.layoutParams = RecyclerView.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
                routeList.layoutManager = LinearLayoutManager(binding.root.context)

                routeDuration =
                    currentDestination.second.minByOrNull { it.euroPrice }!!.getRouteDuration()
                price = currentDestination.second.minByOrNull { it.euroPrice }!!.euroPrice
                routeDestination =
                    locationRepository.searchLocationById(currentDestination.first)!!.getLocation()
                routeList.adapter =
                    RouteListAdapter(MutableLiveData(currentDestination.second), true)

                executePendingBindings()
//            destinationMainView.setOnClickListener {
//                handler.onItemClicked(currentDestination)
//            }

                root.setOnClickListener { openIndicator.isChecked = !openIndicator.isChecked }
                openIndicator.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        routeList.visibility = View.VISIBLE
//                    euroPrice.visibility = View.GONE
//                    duration.visibility = View.GONE
                    } else {
                        routeList.visibility = View.GONE
//                    euroPrice.visibility = View.VISIBLE
//                    duration.visibility = View.VISIBLE
                    }
                }
                openIndicator.isChecked = false
            }
        }
    }

    class AdViewHolder(val binding: NativeAdViewAnywhereBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {

//            val adLoader =
//                AdLoader.Builder(binding.root.context, "ca-app-pub-3940256099942544/2247696110")
            val adLoader = AdLoader.Builder(binding.root.context, "ca-app-pub-7574006463043131/4046840341")
                    .forNativeAd { ad: NativeAd ->
                        with(binding) {
                            bindingAd = ad
//                        imageView.setImageDrawable(ad.icon?.drawable)
//                        titleTextView.text = ad.headline
////                        descriptionTextView.text = ad.body
//                        ratingBar.rating = ad.starRating?.toFloat() ?: 0f
//                        storeTextView.text = ad.store
//                        actionButton.text = ad.callToAction
                            (root as NativeAdView).setNativeAd(ad)
                            root.visibility = View.VISIBLE
                        }
                    }
                    .withAdListener(object : AdListener() {
                        override fun onAdFailedToLoad(adError: LoadAdError) {
                            Napier.d("Ad Error: $adError")
                            // Handle the failure by logging, altering the UI, and so on.
                        }
                    })
                    .build()

            adLoader.loadAd(AdRequest.Builder().build())
        }
    }
}