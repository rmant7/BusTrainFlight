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
import ru.z8.louttsev.cheaptripmobile.shared.model.data.Route

/**
 * Declares read-only storage of available routes.
 *
 * @param mainSource Read-only data access source
 * @param reserveSource Full data access source
 * @param strategy Data sources usage logic
 */
class RouteRepository(
    mainSource: DataSource<Route>,
    reserveSource: DataStorage<Route>,
    strategy: RepositoryStrategy
) {
    private val loadRoutes = strategy.combineLoaderFrom(mainSource, reserveSource)

    /**
     * Finds possible routes between specified locations.
     *
     * @param from Origin location
     * @param to Destination location
     * @param locale Desired results language
     * @return List of matching results (m.b. empty)
     */
    fun getRoutes(
        from: Location,
        to: Location,
        locale: Locale = currentLocale
    ): List<Route> {
        val params = ParamsBundle().apply {
            put(Key.FROM, from)
            put(Key.TO, to)
            put(Key.LOCALE, locale)
        }

        return loadRoutes(params)
    }
}
