/**
 * CheapTrip Mobile
 * This is mobile client for LowCostsTrip server.
 */
package ru.z8.louttsev.bustrainflightmobile.androidApp.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View.*
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.recyclerview.widget.RecyclerView
import io.github.aakira.napier.Napier
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ru.z8.louttsev.bustrainflightmobile.androidApp.databinding.ItemPathBinding
import ru.z8.louttsev.bustrainflightmobile.androidApp.model.data.Country
import ru.z8.louttsev.bustrainflightmobile.androidApp.model.data.Path
import ru.z8.louttsev.bustrainflightmobile.androidApp.payload.AffiliateProgram
import ru.z8.louttsev.bustrainflightmobile.androidApp.model.LocationsRepositoryJson

/**
 * Declares adapter for path list as part of route view.
 *
 * @param mPaths Source of paths data
 */
class PathListAdapter(
    private val mPaths: List<Path>
) : RecyclerView.Adapter<PathListAdapter.ViewHolder>(), KoinComponent {

    val locationRepository: LocationsRepositoryJson by inject()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPathBinding.inflate(LayoutInflater.from(parent.context))

        binding.root.apply {
            layoutParams = RecyclerView.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        }

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentPath = mPaths[position]

        with(holder.binding) {
            model = currentPath // ignore probably IDE error message "Cannot access class..."
            executePendingBindings()

            with(buyTicketButton) {
                // TODO change country stub to auto detected country, issue #3
                val affiliateUrl = AffiliateProgram.getAffiliateUrl(currentPath, Country.INDEFINITE)
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
                            locationRepository.getBookingId(
                                locationRepository.searchLocationsByName(
                                    currentPath.to
                                )[0].id
                            ) +
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

    override fun getItemCount(): Int = mPaths.size

    class ViewHolder(val binding: ItemPathBinding) : RecyclerView.ViewHolder(binding.root)
}