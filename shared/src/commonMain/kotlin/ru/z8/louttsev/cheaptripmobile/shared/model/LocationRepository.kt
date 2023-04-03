/**
 * CheapTrip Mobile
 * This is mobile client for LowCostsTrip server.
 */
package ru.z8.louttsev.cheaptripmobile.shared.model

import ru.z8.louttsev.cheaptripmobile.shared.currentLocale
import ru.z8.louttsev.cheaptripmobile.shared.model.DataSource.ParamsBundle
import ru.z8.louttsev.cheaptripmobile.shared.model.DataSource.ParamsBundle.Key
import ru.z8.louttsev.cheaptripmobile.shared.model.data.Locale
import ru.z8.louttsev.cheaptripmobile.shared.model.data.Location
import ru.z8.louttsev.cheaptripmobile.shared.model.data.Location.Type
import ru.z8.louttsev.cheaptripmobile.shared.model.data.Location.Type.ALL

/**
 * Declares read-only storage of available locations.
 *
 * @param mainSource Read-only data access source
 * @param reserveSource Full data access source
 * @param strategy Data sources usage logic
 */
class LocationRepository(
    mainSource: DataSource<Location>,
    reserveSource: DataStorage<Location>,
    strategy: RepositoryStrategy
) {
    private val loadLocations = strategy.combineLoaderFrom(mainSource, reserveSource)

    /**
     * Finds all locations with matching fragments in the name.
     *
     * @param needle Search pattern
     * @param type Narrows the search by route start/finish point
     * @param limit Desired number of search results
     * @param locale Desired results language
     * @return List of matching results (m.b. empty)
     */
    fun searchLocationsByName(
        needle: String,
        type: Type = ALL,
        limit: Int = 10,
        locale: Locale = currentLocale
    ): List<Location> {
        val params = ParamsBundle().apply {
            put(Key.NEEDLE, needle)
            put(Key.TYPE, type)
            put(Key.LIMIT, limit)
            put(Key.LOCALE, locale)
        }

        return loadLocations(params)
    }
}
