/**
 * CheapTrip Mobile
 * This is mobile client for LowCostsTrip server.
 */
package ru.z8.louttsev.cheaptripmobile.shared.model.data

import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.StringDesc
import ru.z8.louttsev.cheaptripmobile.MR
import ru.z8.louttsev.cheaptripmobile.shared.convertToString
import ru.z8.louttsev.cheaptripmobile.shared.model.data.TransportationType.FLIGHT

/**
 * Declares clause or disclaimer in terms of transportation.
 *
 * @param applicableTransportationTypes Set of transports for which the clause is applicable
 * @param excludableLocations  Set of locations where clause is not applicable
 * @param stringResourceId Resource ID for string representation into UI
 */
enum class ConditionalClause(
    private val applicableTransportationTypes: Set<TransportationType>,
    private val excludableLocations: Set<Location>,
    private val stringResourceId: StringResource,
) {
    DURATION_INCLUDE_AIRPORT_TRANSFER(
        applicableTransportationTypes = setOf(FLIGHT),
        excludableLocations = setOf(
            Location(387, "Москва"), Location(387, "Moscow"),
            Location(377, "Киев"), Location(377, "Kiev")
        ),
        MR.strings.clause_duration_included_airport_transfer_text
    );

    override fun toString(): String {
        return StringDesc.Resource(stringResourceId).convertToString()
    }

    companion object {
        /**
         * Finds the clauses applicable to the specific path
         *
         * @param path Path under checking
         * @return Applicable clauses or empty list if no applicable
         */
        fun getClausesFor(path: Path): List<String> {
            return values()
                .filter { it.applicableTransportationTypes.contains(path.transportationType) }
                .filter {
                    it.excludableLocations.none { location ->
                        location.name == path.from || location.name == path.to
                    }
                }
                .map { it.toString() }
        }
    }
}
