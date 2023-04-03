/**
 * CheapTrip Mobile
 * This is mobile client for LowCostsTrip server.
 */
@file:Suppress("unused")

package ru.z8.louttsev.cheaptripmobile.shared.payload

import ru.z8.louttsev.cheaptripmobile.shared.model.data.Country
import ru.z8.louttsev.cheaptripmobile.shared.model.data.Country.*
import ru.z8.louttsev.cheaptripmobile.shared.model.data.Location
import ru.z8.louttsev.cheaptripmobile.shared.model.data.Path
import ru.z8.louttsev.cheaptripmobile.shared.model.data.TransportationType
import ru.z8.louttsev.cheaptripmobile.shared.model.data.TransportationType.*

/**
 * Declares available affiliate programs.
 *
 * @param applicableCountries Set of countries where program is applicable, empty = everywhere
 * @param applicableLocations Set of locations where program is applicable, empty = everywhere
 * @param affiliateUrls Map of affiliate URLs associated to transport type
 */
enum class AffiliateProgram(
    private val applicableCountries: Set<Country>,
    private val applicableLocations: Set<Location>,
    private val affiliateUrls: Map<TransportationType, String>
) {
    // TODO: implement new item for transferbuses.com affiliate program, issue #40
    // TODO: Eliminate conflicts with other items that have BUS as affiliateUrls key, isuue #40

    SKYSCANNER(
        applicableCountries = emptySet(),
        applicableLocations = emptySet(),
        affiliateUrls = mapOf(
            FLIGHT to "https://skyscanner.com/"
        )
    ),
    TUTU(
        applicableCountries = setOf(RUSSIA, BELARUS, UKRAINE),
        applicableLocations = emptySet(),
        affiliateUrls = mapOf(
            BUS to "https://bus.tutu.ru/",
            TRAIN to "https://www.tutu.ru/poezda/"
        )
    ),
    BUSTRAVEL_DN(
        applicableCountries = emptySet(),
        applicableLocations = setOf(
            Location(545, "Донецк"),
            Location(545, "Donetsk")
        ),
        affiliateUrls = mapOf(
            BUS to "http://bustravel.dn.ua/"
        )
    ),
    MAKEMYTRIP(
        applicableCountries = setOf(INDIA),
        applicableLocations = emptySet(),
        affiliateUrls = mapOf(
            BUS to "https://www.makemytrip.com/bus-tickets/",
            TRAIN to "https://www.makemytrip.com/railways/"
        )
    ),
    OMIO(
        applicableCountries = emptySet(),
        applicableLocations = emptySet(),
        affiliateUrls = mapOf(
            BUS to "http://www.omio.com/",
            TRAIN to "http://www.omio.com/"
        )
    ),
    AFERRY(
        applicableCountries = emptySet(),
        applicableLocations = emptySet(),
        affiliateUrls = mapOf(
            FERRY to "https://www.aferry.com/"
        )
    ),
    BLABLACAR(
        applicableCountries = emptySet(),
        applicableLocations = emptySet(),
        affiliateUrls = mapOf(
            RIDE_SHARE to "https://www.blablacar.com/"
        )
    );

    companion object {
        /**
         * Chooses URL of a suitable in relation to the specified path affiliate program.
         *
         * @param path Specified section of the route
         * @return Suitable payload URL or empty
         */
        fun getAffiliateUrl(path: Path, country: Country): String =
            try {
                @Suppress("SimplifiableCallChain")
                values()
                    .filter {
                        it.affiliateUrls.containsKey(path.transportationType)
                    }
                    .filter {
                        it.applicableCountries.isEmpty() || it.applicableCountries.contains(country)
                    }
                    .filter {
                        it.applicableLocations.isEmpty() || path.relate(it.applicableLocations)
                    }
                    .first() // can throw NoSuchElementException if empty filter result
                    .affiliateUrls[path.transportationType]!! // checked by first filter
            } catch (cause: NoSuchElementException) {
                "" // suitable program not found
            }

        private fun Path.relate(locations: Set<Location>): Boolean {
            locations.forEach {
                if (this.from == it.name || this.to == it.name) return true
            }
            return false
        }
    }
}