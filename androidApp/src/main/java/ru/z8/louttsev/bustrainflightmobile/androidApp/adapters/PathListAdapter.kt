/**
 * CheapTrip Mobile
 * This is mobile client for LowCostsTrip server.
 */
package ru.z8.louttsev.bustrainflightmobile.androidApp.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.recyclerview.widget.RecyclerView
import com.github.satoshun.coroutine.autodispose.view.autoDisposeScope
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
import io.github.aakira.napier.BuildConfig
import io.github.aakira.napier.Napier
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.z8.louttsev.bustrainflightmobile.androidApp.databinding.ItemPathBinding
import ru.z8.louttsev.bustrainflightmobile.androidApp.databinding.ItemRouteBinding
import ru.z8.louttsev.bustrainflightmobile.androidApp.databinding.NativeAdViewRouteBinding
import ru.z8.louttsev.bustrainflightmobile.androidApp.model.data.Country
import ru.z8.louttsev.bustrainflightmobile.androidApp.model.data.Path
import ru.z8.louttsev.bustrainflightmobile.androidApp.model.LocationRepository
import kotlinx.coroutines.launch
import ru.z8.louttsev.bustrainflightmobile.androidApp.model.data.TransportationType
import kotlin.io.path.Path

/**
 * Declares adapter for path list as part of route view.
 *
 * @param mPaths Source of paths data
 */
class PathListAdapter(
    private val mPaths: List<Path>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val AD_VIEW_TYPE = 1
    private val DATA_VIEW_TYPE = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == DATA_VIEW_TYPE) {
            val binding = ItemPathBinding.inflate(LayoutInflater.from(parent.context))
            PathViewHolder(binding)
        } else {
            val binding = NativeAdViewRouteBinding.inflate(LayoutInflater.from(parent.context))
            AdViewHolder(binding)
        }
    }

    override fun getItemViewType(position: Int): Int {

        return if (position == mPaths.size) {
            AD_VIEW_TYPE
        } else {
            DATA_VIEW_TYPE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PathViewHolder) {
            val currentRoute = mPaths[position]
            holder.bind(currentRoute)
        } else if (holder is AdViewHolder) {
            holder.bind()
        }
    }

    override fun getItemCount(): Int {
        val adCount = 1
        val dataCount = mPaths.size
        return if (dataCount == 0) {
            0
        } else {
            dataCount + adCount
        }
    }

    class PathViewHolder(val binding: ItemPathBinding) : RecyclerView.ViewHolder(binding.root),
        KoinComponent {
        private val locationRepository: LocationRepository by inject()

        fun bind(path: Path) {
            with(binding) {
                model = path // ignore probably IDE error message "Cannot access class..."
                executePendingBindings()

                with(buyTicketButton) {
                    // TODO change country stub to auto detected country, issue #3
//                    val affiliateUrl = AffiliateProgram.getAffiliateUrl(path, Country.INDEFINITE)
//                    Napier.d(locationRepository.kiwiCityIds.toString())
                    val affiliateUrl = getAffiliateUrlForBuyTicket(path)
                    Napier.d(affiliateUrl)
                    if (affiliateUrl.isNotEmpty()) {
                        visibility = VISIBLE
                        setOnClickListener {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(affiliateUrl))
                            root.context.startActivity(intent)
                        }
                    } else {
                        visibility = GONE
                        setOnClickListener(null)
                    }
                }

                with(hostelworldButton) {
                    val affiliateUrl =
                        "https://hostelworld.prf.hn/click/camref:1101lmmsq/[p_id:1011l441771]"
                    setOnClickListener {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(affiliateUrl))
                        root.context.startActivity(intent)
                    }
                }
                with(bookingButton) {
                    val affiliateUrl =
                        "https://www.booking.com/searchresults.en.html?aid=7920152&city=" +
//                                locationRepository.getBookingId(
//                                    locationRepository.searchLocationsByName(path.to)[0].id
//                                ) +
                                locationRepository.getBookingId(path.to.id) +
                                "&lang=en&selected_currency=EUR"
                    setOnClickListener {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(affiliateUrl))
                        root.context.startActivity(intent)
                    }
                }

//            with(conditionalClause) {
//                val clauses = ConditionalClause.getClausesFor(currentPath)
//
//                if (clauses.isNotEmpty()) {
//                    text = clauses.joinToString("\n")
//                    visibility = VISIBLE
//                } else {
//                    text = ""
//                    visibility = GONE
//                }
//            }
            }
        }

        private fun getAffiliateUrlForBuyTicket(path: Path): String {
            return when (path.transportationType) {
                TransportationType.FERRY -> "https://www.aferry.com/"
                TransportationType.RIDE_SHARE -> "https://www.blablacar.co.uk/"
                else -> {
                    val qiwiCityId = locationRepository.kiwiCityIds

                    if (qiwiCityId[path.to.id]!![0] != null && qiwiCityId[path.from.id]!![0] != null){
                        val transport = when(path.transportationType){
                            TransportationType.BUS -> "bus"
                            TransportationType.TRAIN -> "train"
                            else -> ""
                        }
                        "http://www.kiwi.com/deep?affilid=cheaptripcheaptrip&currency=EUR" +
                                "&departure=anytime" +
                                "&destination=" +
                                qiwiCityId[path.to.id]!![0] +
                                "&lang=en" +
                                "&origin=" +
                                qiwiCityId[path.from.id]!![0] +
                                "&return=no-return&returnFromDifferentAirport=false&returnToDifferentAirport=false&sortBy=price" +
                                "&transport=$transport"
                    } else {
                            "https://omio.sjv.io/XxEWmb"
                    }
                }
            }
        }
    }


    class AdViewHolder(val binding: NativeAdViewRouteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            with(binding) {
                root.visibility = GONE
                val params: ViewGroup.LayoutParams = ViewGroup.LayoutParams(0, 0)
                root.layoutParams = params
            }
        }

        fun bind() {
            binding.root.autoDisposeScope.launch {
                val id = if (ru.z8.louttsev.bustrainflightmobile.androidApp.BuildConfig.DEBUG) {
                    "ca-app-pub-3940256099942544/2247696110"
                } else {
                    "ca-app-pub-7574006463043131/4046840341"
                }
                val adLoader =
                    AdLoader.Builder(binding.root.context, id)
//                    AdLoader.Builder(binding.root.context, "ca-app-pub-3940256099942544/2247696110")
                        .forNativeAd { ad: NativeAd ->
                            with(binding) {
                                bindingAd = ad
                                val params: ViewGroup.LayoutParams = root.layoutParams
                                params.height = WRAP_CONTENT
                                params.width = MATCH_PARENT
                                (root as NativeAdView).layoutParams = params
                                routeMainView.callToActionView = root
                                root.visibility = VISIBLE
                                (root as NativeAdView).callToActionView = root
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