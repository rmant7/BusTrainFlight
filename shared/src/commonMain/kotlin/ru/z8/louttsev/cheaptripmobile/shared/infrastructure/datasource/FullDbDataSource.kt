/**
 * CheapTrip Mobile
 * This is mobile client for LowCostsTrip server.
 */
package ru.z8.louttsev.cheaptripmobile.shared.infrastructure.datasource

import com.squareup.sqldelight.db.SqlDriver
import ru.z8.louttsev.cheaptripmobile.shared.model.DataSource
import ru.z8.louttsev.cheaptripmobile.shared.model.data.*
import ru.z8.louttsev.cheaptripmobile.shared.model.data.Locale.RU
import ru.z8.louttsev.cheaptripmobile.shared.model.data.Route.Type.*
import kotlin.math.round

// TODO mark deprecate and/or remove, issue #1
/**
 * Declares data source implementation based on a copy of the server DB.
 *
 * @param sqlDriver A platform-specific implementation of the SQLite driver.
 */
abstract class FullDbDataSource<T>(sqlDriver: SqlDriver) : DataSource<T> {
    private val queries = FullDb(sqlDriver).fullDbQueries

    /**
     * Selects locations that have search string in their name.
     *
     * @param needle Search string
     * @param locale Desired results language
     * @return List of matching locations no larger than the specified size
     */
    protected fun selectLocationsByNameIncluding(
        needle: String,
        locale: Locale
    ): List<Location> =
        queries.selectLocationsByName(
            needle,
            mapper = locationMapper(locale)
        ).executeAsList()

    private fun locationMapper(locale: Locale): (Int, String, String) -> Location =
        { id: Int, name: String, nameRu: String ->
            Location(
                id = id,
                name = pickOut(locale)(name, nameRu)
            )
        }

    private fun pickOut(locale: Locale): (String, String) -> String =
        { name, nameRu ->
            when (locale) {
                RU -> nameRu
                else -> name
            }
        }

    /**
     * Selects locations IDs that are origin of the routes.
     *
     * @return List of all suitable locations IDs
     */
    protected fun selectFromIdsOfRoutes(): List<Int> =
        queries.selectFromIdsOfRoutes().executeAsList()

    /**
     * Selects locations IDs that are destination of the routes.
     *
     * @return List of all suitable locations IDs
     */
    protected fun selectToIdsOfRoutes(): List<Int> =
        queries.selectToIdsOfRoutes().executeAsList()

    /**
     * Provides function that finds possible routes required type between from/to locations.
     *
     * Returned function has the signature: (from: Location, to: Location, locale: Locale), where
     * "from" is origin location, "to" is destination location, "locale" is desired results language.
     * Function return list of matching routes (currying by route type applied).
     *
     * @param type Needed route type
     * @return Function of select that do query the suitable table
     */
    protected fun selectRoutes(
        type: Route.Type
    ): (from: Location, to: Location, locale: Locale) -> List<Route> =
        when (type) {
            GROUND -> { from: Location, to: Location, locale: Locale ->
                queries.selectFixedRoutes(
                    from.id,
                    to.id,
                    mapper = routeMapper(GROUND, locale)
                ).executeAsList()
            }
            MIXED -> { from: Location, to: Location, locale: Locale ->
                queries.selectMixedRoutes(
                    from.id,
                    to.id,
                    mapper = routeMapper(MIXED, locale)
                ).executeAsList()
            }
            FLYING -> { from: Location, to: Location, locale: Locale ->
                queries.selectFlyingRoutes(
                    from.id,
                    to.id,
                    mapper = routeMapper(FLYING, locale)
                ).executeAsList()
            }
            FIXED_WITHOUT_RIDE_SHARE -> { from: Location, to: Location, locale: Locale ->
                queries.selectFixedRoutesWithoutRideShare(
                    from.id,
                    to.id,
                    mapper = routeMapper(FIXED_WITHOUT_RIDE_SHARE, locale)
                ).executeAsList()
            }
            WITHOUT_RIDE_SHARE -> { from: Location, to: Location, locale: Locale ->
                queries.selectRoutesWithoutRideShare(
                    from.id,
                    to.id,
                    mapper = routeMapper(WITHOUT_RIDE_SHARE, locale)
                ).executeAsList()
            }
            DIRECT -> { from: Location, to: Location, locale: Locale ->
                queries.selectDirectPaths(
                    from.id,
                    to.id,
                    // a custom mapper is used since the direct route is the single path itself
                    mapper = { id, fromId, toId, transportationTypeId, price, duration ->
                        Route(
                            routeType = DIRECT,
                            euroPrice = round(price*100)/100,
                            durationMinutes = duration!!,
                            directPaths = listOf(
                                pathMapper(locale)(
                                    id,
                                    fromId,
                                    toId,
                                    transportationTypeId,
                                    price,
                                    duration
                                )
                            )
                        )
                    }
                ).executeAsList()
            }
        }

    private fun routeMapper(
        type: Route.Type,
        locale: Locale
    ): (Int, Int, Int, Float, String) -> Route =
        { _: Int, _: Int, _: Int, price: Float, travelData: String ->
            // The travelData DB field contains a comma-separated list of paths IDs,
            // sorted by the order in which the route was planned.
            val pathIds = travelData.split(",").map { it.toInt() }
            val paths = selectPathsByIds(pathIds, locale).sortByListOrder(pathIds)
            Route(
                routeType = type,
                euroPrice = round(price*100)/100,
                durationMinutes = paths.sumOf { it.durationMinutes },
                directPaths = paths
            )
        }

    private fun selectPathsByIds(pathIds: List<Int>, locale: Locale): List<Path> =
        queries.selectPathsByIds(
            pathIds,
            mapper = pathMapper(locale)
        ).executeAsList()

    private fun List<Path>.sortByListOrder(list: List<Int>): List<Path> {
        val reSortedIndexes = list.withIndex().sortedBy { it.value }.map { it.index }
        return this.withIndex().sortedBy { reSortedIndexes[it.index] }.map { it.value }
    }

    private fun pathMapper(locale: Locale): (Int, Int, Int, Int?, Float, Int?) -> Path =
        { _: Int, fromId: Int, toId: Int, transportationTypeId: Int?, price: Float, duration: Int? ->
            Path(
                transportationType = TransportationType.fromValue(
                    selectTransportationTypeNameById(transportationTypeId!!)
                ),
                euroPrice = round(price*100)/100,
                durationMinutes = duration!!,
                from = selectLocationNameById(fromId, locale),
                to = selectLocationNameById(toId, locale)
            )
        }

    private fun selectTransportationTypeNameById(id: Int): String =
        queries.selectTransportationTypeNameById(id).executeAsOne()

    private fun selectLocationNameById(id: Int, locale: Locale): String =
        queries.selectLocationNamesById(
            id,
            mapper = { name, nameRu -> pickOut(locale)(name, nameRu) }
        ).executeAsOne()
}