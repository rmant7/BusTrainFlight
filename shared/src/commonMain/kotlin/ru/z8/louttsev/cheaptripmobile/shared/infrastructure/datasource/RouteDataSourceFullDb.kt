/**
 * CheapTrip Mobile
 * This is mobile client for LowCostsTrip server.
 */
package ru.z8.louttsev.cheaptripmobile.shared.infrastructure.datasource

import com.squareup.sqldelight.db.SqlDriver
import ru.z8.louttsev.cheaptripmobile.shared.model.DataSource.*
import ru.z8.louttsev.cheaptripmobile.shared.model.DataSource.ParamsBundle.*
import ru.z8.louttsev.cheaptripmobile.shared.model.data.Locale
import ru.z8.louttsev.cheaptripmobile.shared.model.data.Location
import ru.z8.louttsev.cheaptripmobile.shared.model.data.Route
import ru.z8.louttsev.cheaptripmobile.shared.model.data.Route.Type.*

// TODO mark deprecate and/or remove, issue #1
/**
 * Declares routes data source implementation based on a copy of the server DB.
 *
 * @param sqlDriver A platform-specific implementation of the SQLite driver.
 */
class RouteDataSourceFullDb(sqlDriver: SqlDriver) : FullDbDataSource<Route>(sqlDriver) {
    override fun get(parameters: ParamsBundle): List<Route> {
        val fromLocation = parameters.get(Key.FROM) as Location
        val toLocation = parameters.get(Key.TO) as Location
        val locale = parameters.get(Key.LOCALE) as Locale

        return listOf(
            selectRoutes(GROUND)(fromLocation, toLocation, locale),
            selectRoutes(MIXED)(fromLocation, toLocation, locale),
            selectRoutes(FLYING)(fromLocation, toLocation, locale),
            selectRoutes(FIXED_WITHOUT_RIDE_SHARE)(fromLocation, toLocation, locale),
            selectRoutes(WITHOUT_RIDE_SHARE)(fromLocation, toLocation, locale),
            selectRoutes(DIRECT)(fromLocation, toLocation, locale)
        ).mergeUniqueBy { it.directPaths }.sortedBy { it.euroPrice }
    }

    private fun <T> List<List<Route>>.mergeUniqueBy(selector: (Route) -> T): List<Route> =
        this.flatten().distinctBy(selector)
}