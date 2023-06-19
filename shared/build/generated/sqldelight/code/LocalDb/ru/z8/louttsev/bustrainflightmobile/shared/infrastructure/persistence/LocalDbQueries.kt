package ru.z8.louttsev.bustrainflightmobile.shared.infrastructure.persistence

import com.squareup.sqldelight.Query
import com.squareup.sqldelight.Transacter
import kotlin.Any
import kotlin.Float
import kotlin.Int
import kotlin.Long
import kotlin.String

interface LocalDbQueries : Transacter {
  fun <T : Any> selectLocationsByName(
    needle: String,
    limit: Long,
    mapper: (id: Int, name: String) -> T
  ): Query<T>

  fun selectLocationsByName(needle: String, limit: Long): Query<SelectLocationsByName>

  fun selectRouteId(
    typeName: String,
    fromLocationName: String,
    toLocationName: String,
    languageCode: String
  ): Query<Long>

  fun <T : Any> selectRoutesByLocations(
    fromLocationName: String,
    toLocationName: String,
    languageCode: String,
    mapper: (
      id: Long,
      type_name: String,
      price: Float,
      duration: Int
    ) -> T
  ): Query<T>

  fun selectRoutesByLocations(
    fromLocationName: String,
    toLocationName: String,
    languageCode: String
  ): Query<SelectRoutesByLocations>

  fun <T : Any> selectPathsByRouteId(routeId: Long, mapper: (
    transportation_type_name: String,
    price: Float,
    duration: Int,
    from_location_name: String,
    to_location_name: String
  ) -> T): Query<T>

  fun selectPathsByRouteId(routeId: Long): Query<SelectPathsByRouteId>

  fun deletePathsByRouteId(routeId: Long)

  fun insertPath(
    transportationTypeName: String,
    price: Float,
    duration: Int,
    fromLocationName: String,
    toLocationName: String,
    routeId: Long
  )

  fun updateOrInsertLocation(
    name: String,
    id: Int,
    typeName: String,
    languageCode: String
  )

  fun updateOrInsertRoute(
    price: Float,
    duration: Int,
    typeName: String,
    fromLocationName: String,
    toLocationName: String,
    languageCode: String
  )
}
