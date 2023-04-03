/**
 * CheapTrip Mobile
 * This is mobile client for LowCostsTrip server.
 */
package ru.z8.louttsev.cheaptripmobile.shared.infrastructure.persistence

import com.squareup.sqldelight.db.SqlDriver
import ru.z8.louttsev.cheaptripmobile.shared.model.DataSource.ParamsBundle
import ru.z8.louttsev.cheaptripmobile.shared.model.DataSource.ParamsBundle.Key
import ru.z8.louttsev.cheaptripmobile.shared.model.data.Locale
import ru.z8.louttsev.cheaptripmobile.shared.model.data.Location
import ru.z8.louttsev.cheaptripmobile.shared.model.data.Route

/**
 * Declares routes storage implementation.
 *
 * @param sqlDriver A platform-specific implementation of the SQLite driver.
 */
class RouteDb(sqlDriver: SqlDriver) : LocalDbStorage<Route>(sqlDriver) {
    override fun put(data: List<Route>, parameters: ParamsBundle) {
        val fromLocation = parameters.get(Key.FROM) as Location
        val toLocation = parameters.get(Key.TO) as Location
        val locale = parameters.get(Key.LOCALE) as Locale

        data.forEach { route: Route ->
            updateOrInsertRoute(route, fromLocation.name, toLocation.name, locale.languageCode)
        }
    }

    override fun get(parameters: ParamsBundle): List<Route> {
        val fromLocation = parameters.get(Key.FROM) as Location
        val toLocation = parameters.get(Key.TO) as Location
        val locale = parameters.get(Key.LOCALE) as Locale

        return selectRoutesByLocations(fromLocation.name, toLocation.name, locale.languageCode)
    }
}
