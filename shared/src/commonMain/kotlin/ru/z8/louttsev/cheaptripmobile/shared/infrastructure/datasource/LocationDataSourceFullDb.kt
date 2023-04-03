/**
 * CheapTrip Mobile
 * This is mobile client for LowCostsTrip server.
 */
package ru.z8.louttsev.cheaptripmobile.shared.infrastructure.datasource

import com.squareup.sqldelight.db.SqlDriver
import ru.z8.louttsev.cheaptripmobile.shared.model.DataSource.ParamsBundle
import ru.z8.louttsev.cheaptripmobile.shared.model.DataSource.ParamsBundle.Key
import ru.z8.louttsev.cheaptripmobile.shared.model.data.Locale
import ru.z8.louttsev.cheaptripmobile.shared.model.data.Location
import ru.z8.louttsev.cheaptripmobile.shared.model.data.Location.Type
import ru.z8.louttsev.cheaptripmobile.shared.model.data.Location.Type.*

// TODO mark deprecate and/or remove, issue #1
/**
 * Declares locations data source implementation based on a copy of the server DB.
 *
 * @param sqlDriver A platform-specific implementation of the SQLite driver.
 */
class LocationDataSourceFullDb(sqlDriver: SqlDriver) : FullDbDataSource<Location>(sqlDriver) {
    override fun get(parameters: ParamsBundle): List<Location> {
        val needle = parameters.get(Key.NEEDLE) as String
        val type = parameters.get(Key.TYPE) as Type
        val limit = parameters.get(Key.LIMIT) as Int
        val locale = parameters.get(Key.LOCALE) as Locale

        return selectLocationsByNameIncluding(
            needle,
            locale
        ).filterByType(type).sortedByIncluding(needle).take(limit)
    }

    private fun List<Location>.filterByType(type: Type): List<Location> =
        when (type) {
            ALL -> this // no filtration required
            FROM -> {
                val fromLocationIds = selectFromIdsOfRoutes()
                this.filter { (id) -> fromLocationIds.contains(id) }
            }
            TO -> {
                val toLocationIds = selectToIdsOfRoutes()
                this.filter { (id) -> toLocationIds.contains(id) }
            }
        }

    private fun List<Location>.sortedByIncluding(needle: String): List<Location> {
        val (startWithNeedle, containNeedle) = this.partition {
            it.name.startsWith(needle, ignoreCase = true)
        }

        return listOf(startWithNeedle, containNeedle).flatten()
    }
}