/**
 * CheapTrip Mobile
 * This is mobile client for LowCostsTrip server.
 */
package com.travelapp.bustrainflightmobile.androidApp.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.animation.*
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.setPadding
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.satoshun.coroutine.autodispose.view.autoDisposeScope
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
import com.travelapp.bustrainflightmobile.androidApp.R
import com.travelapp.bustrainflightmobile.androidApp.databinding.ItemRouteBinding
import com.travelapp.bustrainflightmobile.androidApp.databinding.NativeAdViewRouteBinding
import com.travelapp.bustrainflightmobile.androidApp.model.data.Path
import com.travelapp.bustrainflightmobile.androidApp.model.data.Route
import io.github.aakira.napier.Napier
import kotlinx.coroutines.launch

/**
 * Declares adapter for route list as result of searching.
 *
 * @param liveData Observable source of routes data
 */
class RouteListAdapter(
    liveData: LiveData<List<Route>>,
    val isNested: Boolean = false
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mRoutes: List<Route>

    private val AD_VIEW_TYPE = 1
    private val DATA_VIEW_TYPE = 2

    init {
        mRoutes = liveData.value!!
        liveData.observeForever {
            mRoutes = it
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == DATA_VIEW_TYPE) {
            val binding = ItemRouteBinding.inflate(LayoutInflater.from(parent.context))
            RouteViewHolder(binding)
        } else {
            val binding = NativeAdViewRouteBinding.inflate(LayoutInflater.from(parent.context))
            AdViewHolder(binding)
        }
    }

    override fun getItemViewType(position: Int): Int {

        return if (position == mRoutes.size && isNested) {
            AD_VIEW_TYPE
        } else if (position == 1 && !isNested) {
            AD_VIEW_TYPE
        } else {
            DATA_VIEW_TYPE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is RouteViewHolder) {
            val currentRoute = mRoutes[calculateDataIndex(position)]
            holder.bind(currentRoute)
        } else if (holder is AdViewHolder) {
            holder.bind()
        }
    }

    private fun calculateDataIndex(position: Int): Int {
        return if (!isNested) {
            if (position == 0) {
                position
            } else {
                position - 1
            }
        } else {
            position
        }
    }

    override fun getItemCount(): Int {
        val adCount = 1
        val dataCount = mRoutes.size
        return if (dataCount == 0) {
            0
        } else {
            dataCount + adCount
        }
    }

    class RouteViewHolder(val binding: ItemRouteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(currentRoute: Route) {
            with(binding) {
                val context = root.context

                model =
                    currentRoute // ignore probably IDE error message "Cannot access class..."
                pathList.adapter = PathListAdapter(currentRoute.directPaths)
                executePendingBindings()

                root.setOnClickListener { openIndicator.isChecked = !openIndicator.isChecked }
                openIndicator.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        pathList.visibility = View.VISIBLE
//                    euroPrice.visibility = View.GONE
//                    duration.visibility = View.GONE
                    } else {
                        pathList.visibility = View.GONE
//                    euroPrice.visibility = View.VISIBLE
//                    duration.visibility = View.VISIBLE
                    }
                }
                openIndicator.isChecked = false

                transportIconContainer.addIcons(currentRoute.directPaths, context)

                root.layoutParams = RecyclerView.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
                pathList.layoutManager = LinearLayoutManager(context)
            }
        }

        private fun LinearLayout.addIcons(paths: List<Path>, context: Context) {
            removeAllViews()

            paths.forEach { path ->
                addView(
                    ImageView(context).apply {
                        setImageResource(path.transportationType.imageResource)
                        setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP)
                        val padding =
                            context.resources.getDimension(R.dimen.transport_icon_margin)
                                .toInt()
                        setPadding(padding)
                    }
                )
            }
        }
    }

    class AdViewHolder(val binding: NativeAdViewRouteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            with(binding) {
                root.visibility = View.GONE
                val params: ViewGroup.LayoutParams = ViewGroup.LayoutParams(0, 0)
                root.layoutParams = params
            }
        }

        fun bind() {

            binding.root.autoDisposeScope.launch {
                val adLoader =
                    AdLoader.Builder(binding.root.context, "ca-app-pub-7574006463043131/4046840341")
//                    AdLoader.Builder(binding.root.context, "ca-app-pub-3940256099942544/2247696110")
                        .forNativeAd { ad: NativeAd ->
                            with(binding) {
                                bindingAd = ad

                                val params: ViewGroup.LayoutParams = root.layoutParams
                                params.height = WRAP_CONTENT
                                params.width = MATCH_PARENT
                                (root as NativeAdView).layoutParams = params

                                root.visibility = View.VISIBLE
                                root.requestLayout()
                                routeMainView.callToActionView = root
                                routeMainView.setNativeAd(ad)
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