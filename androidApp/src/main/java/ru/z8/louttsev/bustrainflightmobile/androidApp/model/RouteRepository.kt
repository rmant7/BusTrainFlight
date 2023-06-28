package ru.z8.louttsev.bustrainflightmobile.androidApp.model

import ru.z8.louttsev.bustrainflightmobile.androidApp.infrastructure.persistence.RoutesDbJson
import ru.z8.louttsev.bustrainflightmobile.androidApp.model.data.*
import io.github.aakira.napier.Napier

class RouteRepository(db: RoutesDbJson, private val locationRepository: LocationRepository) {

    private val directRoutes = db.getDirectRoutes()

    private val fixedRoutes = db.getFixedRoutes()

    private val flyingRoutes = db.getFlyingRoutes()

    private val mixedRoutes = db.getMixedRoutes()

    private val transport = db.getTransport()

    private lateinit var currentAnywhereLocation: Location
    private lateinit var fullSortedRoutesFromCurrentLocation: MutableList<Pair<Int, MutableList<Route>>>

    private fun getFullSortedRoutesFromLocation(
        originLocation: Location
    ): List<Pair<Int, MutableList<Route>>> {

        val routes = mutableMapOf<Int, MutableList<Route>>()

        for (route in fixedRoutes.values) {
            if (route.from == originLocation.id) {
                if (route.directRoutes.size == 1)
                    continue
                val pathList = mutableListOf<Path>()
                for (path in route.directRoutes) {
                    pathList.add(
                        Path(
                            transportationType = (TransportationType fromValue transport[directRoutes[path]?.transport]?.name),
                            euroPrice = directRoutes[path]?.price!!.toFloat(),
                            durationMinutes = directRoutes[path]?.duration!!,
                            from = locationRepository.searchLocationById(directRoutes[path]!!.from)!!,
                            to = locationRepository.searchLocationById(directRoutes[path]!!.to)!!
                        )
                    )
                }
                if (!routes.getOrPut(route.to) { mutableListOf() }
                        .any { it.directPaths == pathList }) {
                    routes.getOrPut(route.to) { mutableListOf() }.add(
                        Route(
                            routeType = Route.Type.FIXED_WITHOUT_RIDE_SHARE,
                            euroPrice = route.price.toFloat(),
                            durationMinutes = route.duration,
                            directPaths = pathList
                        )
                    )
                }
            }
        }

        for (route in flyingRoutes.values) {
            if (route.from == originLocation.id) {
                if (route.directRoutes.size == 1)
                    continue
                val pathList = mutableListOf<Path>()
                for (path in route.directRoutes) {
                    pathList.add(
                        Path(
                            transportationType = (TransportationType fromValue transport[directRoutes[path]?.transport]?.name),
                            euroPrice = directRoutes[path]?.price!!.toFloat(),
                            durationMinutes = directRoutes[path]?.duration!!,
                            from = locationRepository.searchLocationById(directRoutes[path]!!.from)!!,
                            to = locationRepository.searchLocationById(directRoutes[path]!!.to)!!
                        )
                    )
                }
                if (!routes.getOrPut(route.to) { mutableListOf() }
                        .any { it.directPaths == pathList }) {
                    routes.getOrPut(route.to) { mutableListOf() }.add(
                        Route(
                            routeType = Route.Type.FLYING,
                            euroPrice = route.price.toFloat(),
                            durationMinutes = route.duration,
                            directPaths = pathList
                        )
                    )
                }
            }
        }

        for (route in directRoutes.values) {
            if (route.from == originLocation.id) {
                routes.getOrPut(route.to) { mutableListOf() }.add(
                    Route(
                        routeType = Route.Type.DIRECT,
                        euroPrice = route.price.toFloat(),
                        durationMinutes = route.duration,
                        directPaths = listOf(
                            Path(
                                transportationType = (TransportationType fromValue transport[route.transport]?.name),
                                euroPrice = route.price.toFloat(),
                                durationMinutes = route.duration,
                                from = originLocation,
                                to = locationRepository.searchLocationById(route.to)!!
                            )
                        )
                    )
                )
            }
        }

        for (route in mixedRoutes.values) {
            if (route.from == originLocation.id) {
                if (route.directRoutes.size == 1)
                    continue
                val pathList = mutableListOf<Path>()
                for (path in route.directRoutes) {
                    pathList.add(
                        Path(
                            transportationType = (TransportationType fromValue transport[directRoutes[path]?.transport]?.name),
                            euroPrice = directRoutes[path]?.price!!.toFloat(),
                            durationMinutes = directRoutes[path]?.duration!!,
                            from = locationRepository.searchLocationById(directRoutes[path]!!.from)!!,
                            to = locationRepository.searchLocationById(directRoutes[path]!!.to)!!
                        )
                    )
                }
                if (!routes.getOrPut(route.to) { mutableListOf() }
                        .any { it.directPaths == pathList }) {
                    routes.getOrPut(route.to) { mutableListOf() }.add(
                        Route(
                            routeType = Route.Type.MIXED,
                            euroPrice = route.price.toFloat(),
                            durationMinutes = route.duration,
                            directPaths = pathList
                        )
                    )
                }
            }
        }

        routes.values.forEach { list ->
            list.sortBy { it.euroPrice }
        }

        return routes.toList().sortedBy { it.second.minByOrNull { it.euroPrice }!!.euroPrice }
//        return if (withEmptySpaceForAd){
//            addAdEmptySpaceInList(result)
//        } else {
//            result
//        }
    }

//    private fun addAdEmptySpaceInList(list: List<Pair<Int, MutableList<Route>>>):
//            MutableList<Pair<Int, MutableList<Route>>> {
//        val spaceForAd = Pair<Int, MutableList<Route>>(-1, mutableListOf())
//
//        val modifiedList = mutableListOf<Pair<Int, MutableList<Route>>>()
//        val n = 5
//        list.forEachIndexed { index, item ->
//            modifiedList.add(item) // Add the original item to the modified list
//
//            if ((index + 1) % n == 0) {
//                modifiedList.add(spaceForAd) // Add the new item at every nth position
//            }
//        }
//
//        return modifiedList
//    }

    fun getPackOfRoutesFromLocation(
        originLocation: Location,
        limit: Int = 20,
        newList: Boolean = false
    ): MutableList<Pair<Int, MutableList<Route>>> {
        Napier.d("getPackOfRoutesFromLocation")
        if (!::fullSortedRoutesFromCurrentLocation.isInitialized) {
            fullSortedRoutesFromCurrentLocation =
                getFullSortedRoutesFromLocation(originLocation).toMutableList()
        } else if (originLocation != currentAnywhereLocation) {
            fullSortedRoutesFromCurrentLocation =
                getFullSortedRoutesFromLocation(originLocation).toMutableList()
        } else if (newList) {
            fullSortedRoutesFromCurrentLocation =
                getFullSortedRoutesFromLocation(originLocation).toMutableList()
        }

        currentAnywhereLocation = originLocation
        return if (limit >= fullSortedRoutesFromCurrentLocation.size) {
            val elements = ArrayList(fullSortedRoutesFromCurrentLocation)
            fullSortedRoutesFromCurrentLocation.clear()
            Napier.d(elements.toString())
            elements
        } else {
            val elementsToRemove = fullSortedRoutesFromCurrentLocation.subList(0, limit)
            val elements = ArrayList(elementsToRemove)
            fullSortedRoutesFromCurrentLocation.removeAll(elementsToRemove)
            elements
        }
    }

    fun getRoutes(
        from: Location,
        to: Location,
//        locale: Locale = currentLocale
    ): List<Route> {
        val routeList = mutableListOf<Route>()

        val addedPath = mutableListOf(listOf<Int>())

        for (route in fixedRoutes.values) {
            if (route.from == from.id && route.to == to.id) {
                if (route.directRoutes.size == 1)
                    break
                addedPath.add(route.directRoutes)
                val pathList = mutableListOf<Path>()
                for (path in route.directRoutes) {
                    pathList.add(
                        Path(
                            transportationType = (TransportationType fromValue transport[directRoutes[path]?.transport]?.name),
                            euroPrice = directRoutes[path]?.price!!.toFloat(),
                            durationMinutes = directRoutes[path]?.duration!!,
                            from = locationRepository.searchLocationById(directRoutes[path]!!.from)!!,
                            to = locationRepository.searchLocationById(directRoutes[path]!!.to)!!
                        )
                    )
                }
                routeList.add(
                    Route(
                        routeType = Route.Type.FIXED_WITHOUT_RIDE_SHARE,
                        euroPrice = route.price.toFloat(),
                        durationMinutes = route.duration,
                        directPaths = pathList
                    )
                )
                break
            }
        }

        for (route in flyingRoutes.values) {
            if (route.from == from.id && route.to == to.id) {
                if (route.directRoutes.size == 1)
                    break
                addedPath.add(route.directRoutes)
                val pathList = mutableListOf<Path>()
                for (path in route.directRoutes) {
                    pathList.add(
                        Path(
                            transportationType = (TransportationType fromValue transport[directRoutes[path]?.transport]?.name),
                            euroPrice = directRoutes[path]?.price!!.toFloat(),
                            durationMinutes = directRoutes[path]?.duration!!,
                            from = locationRepository.searchLocationById(directRoutes[path]!!.from)!!,
                            to = locationRepository.searchLocationById(directRoutes[path]!!.to)!!
                        )
                    )
                }
                routeList.add(
                    Route(
                        routeType = Route.Type.FLYING,
                        euroPrice = route.price.toFloat(),
                        durationMinutes = route.duration,
                        directPaths = pathList
                    )
                )
                break
            }
        }

        for (route in directRoutes.values) {
            if (route.from == from.id && route.to == to.id) {
                addedPath.add(listOf(directRoutes.entries.find { it.value == route }!!.key))
                routeList.add(
                    Route(
                        routeType = Route.Type.DIRECT,
                        euroPrice = route.price.toFloat(),
                        durationMinutes = route.duration,
                        directPaths = listOf(
                            Path(
                                transportationType = (TransportationType fromValue transport[route.transport]?.name),
                                euroPrice = route.price.toFloat(),
                                durationMinutes = route.duration,
                                from = from,
                                to = to
                            )
                        )
                    )
                )
            }
        }

        for (route in mixedRoutes.values) {
            if (route.from == from.id && route.to == to.id) {
                if (addedPath.contains(route.directRoutes))
                    continue
                val pathList = mutableListOf<Path>()
                for (path in route.directRoutes) {
                    pathList.add(
                        Path(
                            transportationType = (TransportationType fromValue transport[directRoutes[path]?.transport]?.name),
                            euroPrice = directRoutes[path]?.price!!.toFloat(),
                            durationMinutes = directRoutes[path]?.duration!!,
                            from = locationRepository.searchLocationById(directRoutes[path]!!.from)!!,
                            to = locationRepository.searchLocationById(directRoutes[path]!!.to)!!
                        )
                    )
                }
                routeList.add(
                    Route(
                        routeType = Route.Type.MIXED,
                        euroPrice = route.price.toFloat(),
                        durationMinutes = route.duration,
                        directPaths = pathList
                    )
                )
                break
            }
        }

        return routeList.sortedBy { it.euroPrice }
    }
}