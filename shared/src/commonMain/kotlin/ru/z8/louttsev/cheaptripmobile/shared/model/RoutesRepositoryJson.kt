package ru.z8.louttsev.cheaptripmobile.shared.model

import io.github.aakira.napier.Napier
import ru.z8.louttsev.cheaptripmobile.shared.currentLocale
import ru.z8.louttsev.cheaptripmobile.shared.infrastructure.persistence.RoutesDbJson
import ru.z8.louttsev.cheaptripmobile.shared.model.data.*

class RoutesRepositoryJson(val db: RoutesDbJson, val locationRepository: LocationsRepositoryJson) {

    private val directRoutes = db.getDirectRoutes()
    fun getDirectRoutes() = directRoutes

    private val fixedRoutes = db.getFixedRoutes()
    fun getFixedRoutes() = fixedRoutes

    private val flyingRoutes = db.getFlyingRoutes()
    fun getFlyingRoutes() = flyingRoutes

    private val mixedRoutes = db.getMixedRoutes()
    fun getMixedRoutes() = mixedRoutes

    private val transport = db.getTransport()
    fun getTransport() = transport

    fun getRoutes(
        from: Location,
        to: Location,
        locale: Locale = currentLocale
    ): List<Route> {
        val routeList = mutableListOf<Route>()

        val addedPath = mutableListOf(listOf<Int>())

        for (route in fixedRoutes.values){
            if (route.from == from.id && route.to == to.id){
                Napier.d("Fixed route: $route")
                if (route.directRoutes.size == 1)
                    break
                addedPath.add(route.directRoutes)
                val pathList = mutableListOf<Path>()
                for (path in route.directRoutes){
                    pathList.add(Path(
                        transportationType = (TransportationType fromValue transport[directRoutes[path]?.transport]?.name),
                        euroPrice = directRoutes[path]?.price!!.toFloat(),
                        durationMinutes = directRoutes[path]?.duration!!,
                        from = locationRepository.searchLocationById(directRoutes[path]!!.from)!!.name,
                        to = locationRepository.searchLocationById(directRoutes[path]!!.to)!!.name
                    ))
                }
                routeList.add(
                    Route(
                        routeType = Route.Type.FIXED_WITHOUT_RIDE_SHARE,
                        euroPrice = route.price.toFloat(),
                        durationMinutes = route.duration,
                        directPaths = pathList
                ))
            }
        }

        for (route in flyingRoutes.values){
            if (route.from == from.id && route.to == to.id){
                Napier.d("Fling route: $route")
                if (route.directRoutes.size == 1)
                    break
                addedPath.add(route.directRoutes)
                val pathList = mutableListOf<Path>()
                for (path in route.directRoutes){
                    pathList.add(Path(
                        transportationType = (TransportationType fromValue transport[directRoutes[path]?.transport]?.name),
                        euroPrice = directRoutes[path]?.price!!.toFloat(),
                        durationMinutes = directRoutes[path]?.duration!!,
                        from = locationRepository.searchLocationById(directRoutes[path]!!.from)!!.name,
                        to = locationRepository.searchLocationById(directRoutes[path]!!.to)!!.name
                    ))
                }
                routeList.add(
                    Route(
                        routeType = Route.Type.FLYING,
                        euroPrice = route.price.toFloat(),
                        durationMinutes = route.duration,
                        directPaths = pathList
                    ))
            }
        }

        for (route in directRoutes.values) {
            if (route.from == from.id && route.to == to.id) {
                Napier.d("Direct route: $route")
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

        for (route in mixedRoutes.values){
            if (route.from == from.id && route.to == to.id){
                Napier.d("Mixed routes: $route")
                if (addedPath.contains(route.directRoutes))
                    continue
                val pathList = mutableListOf<Path>()
                for (path in route.directRoutes){
                    pathList.add(Path(
                        transportationType = (TransportationType fromValue transport[directRoutes[path]?.transport]?.name),
                        euroPrice = directRoutes[path]?.price!!.toFloat(),
                        durationMinutes = directRoutes[path]?.duration!!,
                        from = locationRepository.searchLocationById(directRoutes[path]!!.from)!!.name,
                        to = locationRepository.searchLocationById(directRoutes[path]!!.to)!!.name
                    ))
                }
                routeList.add(
                    Route(
                        routeType = Route.Type.MIXED,
                        euroPrice = route.price.toFloat(),
                        durationMinutes = route.duration,
                        directPaths = pathList
                    ))
            }
        }

        return routeList.sortedBy { it.euroPrice }
    }

}