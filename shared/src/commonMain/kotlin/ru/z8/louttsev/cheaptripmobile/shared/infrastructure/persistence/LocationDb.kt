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

/**
 * Declares locations storage implementation.
 *
 * @param sqlDriver A platform-specific implementation of the SQLite driver.
 */
class LocationDb(sqlDriver: SqlDriver) : LocalDbStorage<Location>(sqlDriver) {
    override fun put(data: List<Location>, parameters: ParamsBundle) {
        val type = parameters.get(Key.TYPE) as Location.Type
        val locale = parameters.get(Key.LOCALE) as Locale

        data.forEach { location: Location ->
            updateOrInsertLocation(location, type.name, locale.languageCode)
        }
    }

    override fun get(parameters: ParamsBundle): List<Location> {
        val needle = parameters.get(Key.NEEDLE) as String
        val limit = parameters.get(Key.LIMIT) as Int

        return selectLocationsByName(needle, limit.toLong())
    }
}
