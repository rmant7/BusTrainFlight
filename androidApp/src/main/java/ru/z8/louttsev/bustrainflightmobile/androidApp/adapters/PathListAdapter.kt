/**
 * CheapTrip Mobile
 * This is mobile client for LowCostsTrip server.
 */
package ru.z8.louttsev.bustrainflightmobile.androidApp.adapters

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.activity.viewModels
import androidx.core.content.ContextCompat.getString
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.viewmodel.viewModelFactory
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
import ru.z8.louttsev.bustrainflightmobile.androidApp.databinding.ItemPathBinding
import ru.z8.louttsev.bustrainflightmobile.androidApp.databinding.NativeAdViewRouteBinding
import ru.z8.louttsev.bustrainflightmobile.androidApp.model.data.Path
import ru.z8.louttsev.bustrainflightmobile.androidApp.model.LocationRepository
import kotlinx.coroutines.launch
import org.json.JSONObject
import ru.z8.louttsev.bustrainflightmobile.androidApp.R
import ru.z8.louttsev.bustrainflightmobile.androidApp.model.data.TransportationType
import ru.z8.louttsev.bustrainflightmobile.androidApp.model.data.loadCityNamesJsonFromRaw
import ru.z8.louttsev.bustrainflightmobile.androidApp.ui.Constants
import ru.z8.louttsev.bustrainflightmobile.androidApp.ui.MainActivity
import ru.z8.louttsev.bustrainflightmobile.androidApp.viewmodel.MainViewModel
import javax.inject.Inject
import kotlin.getValue

/**
 * Declares adapter for path list as part of route view.
 *
 * @param mPaths Source of paths data
 */
class PathListAdapter @Inject constructor(
    private val locationRepository: LocationRepository
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var mPaths: List<Path>

    fun initialize(paths: List<Path>) {
        this.mPaths = paths
        notifyDataSetChanged()
    }

    private val AD_VIEW_TYPE = 1
    private val DATA_VIEW_TYPE = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == DATA_VIEW_TYPE) {
            val binding = ItemPathBinding.inflate(LayoutInflater.from(parent.context))
            PathViewHolder(
                binding = binding,
                locationRepository = locationRepository
            )
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
        }
//        else if (holder is AdViewHolder) {
//            holder.bind()
//        }
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

    class PathViewHolder (
        val locationRepository: LocationRepository,
        val binding: ItemPathBinding
    ) : RecyclerView.ViewHolder(binding.root) {


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
                    var affiliateUrl = ""
                    if (path.to.country == "Russia") affiliateUrl = "https://ostrovok.ru/"
                    else affiliateUrl =
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


                val citiesNameJson = JSONObject(loadCityNamesJsonFromRaw(root.context))
                with(anywhereTravelTipsButton) {
                    var url: String? = ""
                    if (citiesNameJson != null && path.to.name != null) {
                        val matchingCityKey = citiesNameJson.keys().asSequence().find { key ->
                            citiesNameJson.optString(key).equals(path.to.name, ignoreCase = true)
                        }
                        if (matchingCityKey != null) {
                            visibility = View.VISIBLE
                            text =
                                root.context.getString(R.string.city_travel_tips, model?.to?.name)
                            val cityName = citiesNameJson.getString(matchingCityKey)
                            url =
                                cityName?.let {
                                    "https://cheaptrip.guru/budgettraveltips/tree/city_descriptions/en/${it}"
                                }
                        }
                    } else {
                        visibility = View.GONE
                    }
                    setOnClickListener {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url ?: ""))
                        root.context.startActivity(intent)
                    }
                    setOnFocusChangeListener { view, hasFocus ->
                        if (hasFocus) {
                            view.performClick()
                        }
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
            val qiwiCityId = locationRepository.kiwiCityIds

            if (path.from.country == "Russia" || path.to.country == "Russia") {
                return when (path.transportationType) {
                    TransportationType.FERRY -> "https://www.aferry.com/"
                    TransportationType.RIDE_SHARE -> "https://www.blablacar.co.uk/"
                    TransportationType.BUS -> "https://bus.tutu.ru/"
                    TransportationType.TRAIN -> "https://tutu.ru/poezda/"
                    TransportationType.FLIGHT -> "https://www.aviasales.ru/?params=KWG1"
                    else -> ""
                }
            }

            if (path.from.country == "India" && path.to.country == "India" && path.transportationType != TransportationType.FLIGHT) {
                return when (path.transportationType) {
                    TransportationType.FERRY -> "https://www.aferry.com/"
                    TransportationType.RIDE_SHARE -> "https://www.blablacar.co.uk/"
                    TransportationType.BUS -> "https://www.makemytrip.com/bus-tickets/"
                    TransportationType.TRAIN -> "https://www.makemytrip.com/railways/"
                    else -> ""
                }
            }

            return when (path.transportationType) {
                TransportationType.FERRY -> "https://www.aferry.com/"
                TransportationType.RIDE_SHARE -> "https://www.blablacar.co.uk/"
                else -> {
                    if (qiwiCityId[path.to.id] == null || qiwiCityId[path.from.id] == null) {
                        return "https://omio.sjv.io/XxEWmb"
                    }

                    if (qiwiCityId[path.to.id]!![0] != null && qiwiCityId[path.from.id]!![0] != null) {
                        val transport = when (path.transportationType) {
                            TransportationType.BUS -> "bus"
                            TransportationType.TRAIN -> "train"
                            else -> ""
                        }
                        qiwiCityId[path.from.id]!![0]?.let { Log.d("asdfg", it) }
                        qiwiCityId[path.to.id]!![0]?.let { Log.d("asdfg", it) }
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

//        fun bind() {
//            binding.root.autoDisposeScope.launch {
//                val id = if (BuildConfig.DEBUG) {
//                    Constants.NATIVE_AD_ID_SAMPLE
//                } else {
//                    Constants.NATIVE_AD_ID_VER_3
//                }
//                val adLoader =
//                    AdLoader.Builder(binding.root.context, id)
////                    AdLoader.Builder(binding.root.context, "ca-app-pub-3940256099942544/2247696110")
//                        .forNativeAd { ad: NativeAd ->
//                            with(binding) {
//                                bindingAd = ad
//                                val params: ViewGroup.LayoutParams = root.layoutParams
//                                params.height = WRAP_CONTENT
//                                params.width = MATCH_PARENT
//                                (root as NativeAdView).layoutParams = params
//                                routeMainView.callToActionView = root
//                                root.visibility = VISIBLE
//                                (root as NativeAdView).callToActionView = root
//                                routeMainView.setNativeAd(ad)
//                            }
//                        }
//                        .withAdListener(object : AdListener() {
//                            override fun onAdFailedToLoad(adError: LoadAdError) {
//                                Napier.d("Ad Error: $adError")
//                                // Handle the failure by logging, altering the UI, and so on.
//                            }
//                        })
//                        .build()
//
//                adLoader.loadAd(AdRequest.Builder().build())
//            }
//        }
    }
}