/**
 * CheapTrip Mobile
 * This is mobile client for LowCostsTrip server.
 */
package ru.z8.louttsev.bustrainflightmobile.androidApp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.satoshun.coroutine.autodispose.view.autoDisposeScope
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
import io.github.aakira.napier.BuildConfig
import ru.z8.louttsev.bustrainflightmobile.androidApp.databinding.ItemRouteAnywhereBinding
import ru.z8.louttsev.bustrainflightmobile.androidApp.databinding.NativeAdViewAnywhereBinding
import ru.z8.louttsev.bustrainflightmobile.androidApp.model.LocationRepository
import ru.z8.louttsev.bustrainflightmobile.androidApp.model.data.Route
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

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

    private lateinit var mRecyclerView: RecyclerView

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecyclerView = recyclerView
    }

    fun isListEmpty() = mDestinationRoutes.isEmpty()

    fun addRoutes(routes: MutableList<Pair<Int, MutableList<Route>>>) {
        if (routes.isNotEmpty()) {
            val startPosition = mDestinationRoutes.size
            mDestinationRoutes.addAll(routes)
            notifyItemRangeInserted(itemCount, routes.size + 4)
            mRecyclerView.scrollToPosition(itemCount)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position % AD_ITEM_INTERVAL == 1) {
            AD_VIEW_TYPE
        } else {
            DATA_VIEW_TYPE
        }
    }

    private fun calculateDataIndex(position: Int): Int {
        Napier.d("Position: $position")
        var index = 0
        if (position < 1) {
            index = position
        } else if (position % (AD_ITEM_INTERVAL) == 0) {
            index = position - position / (AD_ITEM_INTERVAL)
        } else {
            index = position - position / (AD_ITEM_INTERVAL) - 1
        }
        Napier.d("Index: $index")
        return index
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Napier.d("$mDestinationRoutes")
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
            holder.bind(mRecyclerView, mDestinationRoutes.size-1)
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

        init {
            with(binding) {
                root.visibility = View.GONE
                val params: ViewGroup.LayoutParams = ViewGroup.LayoutParams(0, 0)
                root.layoutParams = params
            }
        }

        fun bind(recyclerView: RecyclerView, position: Int) {

//            val adLoader =
//                AdLoader.Builder(binding.root.context, "ca-app-pub-3940256099942544/2247696110")
            binding.root.autoDisposeScope.launch {
                withContext(Dispatchers.IO) {
                    val id = if (BuildConfig.DEBUG) {
                        "ca-app-pub-3940256099942544/2247696110"
                    } else {
                        "ca-app-pub-7574006463043131/4046840341"
                    }
                    val adLoader =
                        AdLoader.Builder(
                            binding.root.context,
                            id
                        )
                            .forNativeAd { ad: NativeAd ->
                                with(binding) {
                                    bindingAd = ad
                                    Napier.d("${ad.icon}")
                                    Napier.d("${ad.price}")

                                    (root as NativeAdView).callToActionView = root
                                    val params: ViewGroup.LayoutParams = root.layoutParams
                                    params.height = WRAP_CONTENT
                                    params.width = MATCH_PARENT
                                    (root as NativeAdView).layoutParams = params
                                    root.visibility = View.VISIBLE
                                    (root as NativeAdView).setNativeAd(ad)
//                                    recyclerView.smoothScrollToPosition(position)
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
    }
}