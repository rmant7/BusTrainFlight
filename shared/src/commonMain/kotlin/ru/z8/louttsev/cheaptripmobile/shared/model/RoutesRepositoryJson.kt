package ru.z8.louttsev.cheaptripmobile.shared.model

import ru.z8.louttsev.cheaptripmobile.shared.currentLocale
import ru.z8.louttsev.cheaptripmobile.shared.infrastructure.persistence.LocationsDbJson
import ru.z8.louttsev.cheaptripmobile.shared.infrastructure.persistence.RoutesDbJson
import ru.z8.louttsev.cheaptripmobile.shared.model.data.*

class RoutesRepositoryJson(val db: RoutesDbJson) {

    private val routes = db.getDirectRoutes()
    fun getDirectRoutes() = routes

    private val transport = db.getTransport()
    fun getTransport() = transport

    fun getRoutes(
        from: Location,
        to: Location,
        locale: Locale = currentLocale
    ): List<Route> {
        val routeList = mutableListOf<Route>()

        for (route in routes.values) {
            if (route.from == from.id && route.to == to.id) {
                routeList.add(
                    Route(
                        routeType = Route.Type.DIRECT,
                        euroPrice = route.price.toFloat(),
                        durationMinutes = route.duration,
                        directPaths = listOf(Path(
                            transportationType = (TransportationType fromValue transport[route.transport]?.name),
                            euroPrice = route.price.toFloat(),
                            durationMinutes = route.duration,
                            from = from.name,
                            to = to.name
                        ))
                    )
                )
            }
        }

        return routeList
    }

}