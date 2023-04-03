/**
 * CheapTrip Mobile
 * This is mobile client for LowCostsTrip server.
 */
package ru.z8.louttsev.cheaptripmobile.shared.infrastructure.persistence

import com.squareup.sqldelight.db.SqlDriver
import ru.z8.louttsev.cheaptripmobile.shared.model.DataStorage
import ru.z8.louttsev.cheaptripmobile.shared.model.data.Location
import ru.z8.louttsev.cheaptripmobile.shared.model.data.Path
import ru.z8.louttsev.cheaptripmobile.shared.model.data.Route
import ru.z8.louttsev.cheaptripmobile.shared.model.data.Route.Type
import ru.z8.louttsev.cheaptripmobile.shared.model.data.TransportationType

/**
 * Declares data storage based on local database.
 *
 * @param sqlDriver A platform-specific implementation of the SQLite driver.
 */
abstract class LocalDbStorage<T>(sqlDriver: SqlDriver) : DataStorage<T> {
    private val queries = LocalDb(sqlDriver).localDbQueries

    /**
     * Tries to update location record; if it doesn't exist, inserts it.
     *
     * @param location Updated/inserted location
     * @param typeName Location's type in relation to route
     * @param languageCode Two-letter code accordingly ISO 639-1
     */
    protected fun updateOrInsertLocation(
        location: Location,
        typeName: String,
        languageCode: String
    ) {
        val (locationId, locationName) = location

        // the method violates the expected order of the arguments
        queries.updateOrInsertLocation(locationName, locationId, typeName, languageCode)
    }

    /**
     * Finds locations that have search string in their name.
     *
     * @param needle Search string
     * @param limit Desired number of locations in the response
     * @return List of matching locations no larger than the specified size
     */
    protected fun selectLocationsByName(needle: String, limit: Long): List<Location> =
        queries.selectLocationsByName(
            needle,
            limit,
            mapper = { id, name -> Location(id, name) }
        ).executeAsList()

    /**
     * Tries to update route record; if it doesn't exist, inserts it.
     *
     * @param route Updated/inserted route
     * @param fromLocationName Origin location name
     * @param toLocationName Destination location name
     * @param languageCode Two-letter code accordingly ISO 639-1
     */
    protected fun updateOrInsertRoute(
        route: Route,
        fromLocationName: String,
        toLocationName: String,
        languageCode: String
    ) {
        val (type, price, duration, paths) = route
        val typeName = type.name

        // wrapped in transaction, due to associated path records are deleted
        // and inserted at the same time.
        queries.transaction {
            val routeId =
                // the method violates the expected order of the arguments
                updateOrInsertRouteAndGetId(
                    price,
                    duration,
                    typeName,
                    fromLocationName,
                    toLocationName,
                    languageCode
                )

            deletePathsByRouteId(routeId)

            paths.forEach { path: Path ->
                insertPath(path, routeId)
            }
        }
    }

    /**
     * Finds routes by origin/destination locations.
     *
     * @param fromLocationName Origin location name
     * @param toLocationName Destination location name
     * @param languageCode Two-letter code accordingly ISO 639-1
     */
    protected fun selectRoutesByLocations(
        fromLocationName: String,
        toLocationName: String,
        languageCode: String
    ): List<Route> =
        queries.selectRoutesByLocations(
            fromLocationName,
            toLocationName,
            languageCode,
            mapper = { id, type, price, duration ->
                Route(Type.valueOf(type), price, duration, selectPathsByRouteId(id))
            }
        ).executeAsList()

    private fun updateOrInsertRouteAndGetId(
        price: Float,
        duration: Int,
        typeName: String,
        fromLocationName: String,
        toLocationName: String,
        languageCode: String
    ): Long {
        queries.updateOrInsertRoute(
            price,
            duration,
            typeName,
            fromLocationName,
            toLocationName,
            languageCode
        )

        return queries.selectRouteId(
            typeName,
            fromLocationName,
            toLocationName,
            languageCode
        ).executeAsOne()
    }

    private fun selectRouteId(
        typeName: String,
        fromLocationName: String,
        toLocationName: String,
        languageCode: String
    ): Long =
        queries.selectRouteId(
            typeName,
            fromLocationName,
            toLocationName,
            languageCode
        ).executeAsOne()

    private fun deletePathsByRouteId(routeId: Long) {
        queries.deletePathsByRouteId(routeId)
    }

    private fun insertPath(path: Path, routeId: Long) {
        val (transportationType, price, duration, fromLocationName, toLocationName) = path
        val transportationTypeName = transportationType.name

        queries.insertPath(
            transportationTypeName,
            price,
            duration,
            fromLocationName,
            toLocationName,
            routeId
        )
    }

    private fun selectPathsByRouteId(routeId: Long): List<Path> =
        queries.selectPathsByRouteId(
            routeId,
            mapper = { transportationType,
                       price,
                       duration,
                       fromLocationName,
                       toLocationName ->
                Path(
                    TransportationType.valueOf(transportationType),
                    price,
                    duration,
                    fromLocationName,
                    toLocationName
                )
            }
        ).executeAsList()
}
