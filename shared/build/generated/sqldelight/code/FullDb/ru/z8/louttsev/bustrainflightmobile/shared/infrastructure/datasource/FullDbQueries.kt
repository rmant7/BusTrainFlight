package com.travelapp.bustrainflightmobile.shared.infrastructure.datasource

import com.squareup.sqldelight.Query
import com.squareup.sqldelight.Transacter
import kotlin.Any
import kotlin.Float
import kotlin.Int
import kotlin.String
import kotlin.collections.Collection

interface FullDbQueries : Transacter {
  fun <T : Any> selectLocationsByName(needle: String, mapper: (
    id: Int,
    name: String,
    name_ru: String
  ) -> T): Query<T>

  fun selectLocationsByName(needle: String): Query<SelectLocationsByName>

  fun <T : Any> selectLocationNamesById(id: Int, mapper: (name: String, name_ru: String) -> T):
      Query<T>

  fun selectLocationNamesById(id: Int): Query<SelectLocationNamesById>

  fun <T : Any> selectDirectPaths(
    from: Int,
    to: Int,
    mapper: (
      id: Int,
      from: Int,
      to: Int,
      transportation_type: Int?,
      euro_price: Float,
      time_in_minutes: Int?
    ) -> T
  ): Query<T>

  fun selectDirectPaths(from: Int, to: Int): Query<Travel_data>

  fun <T : Any> selectPathsByIds(pathIds: Collection<Int>, mapper: (
    id: Int,
    from: Int,
    to: Int,
    transportation_type: Int?,
    euro_price: Float,
    time_in_minutes: Int?
  ) -> T): Query<T>

  fun selectPathsByIds(pathIds: Collection<Int>): Query<Travel_data>

  fun selectFromIdsOfRoutes(): Query<Int>

  fun selectToIdsOfRoutes(): Query<Int>

  fun <T : Any> selectMixedRoutes(
    from: Int,
    to: Int,
    mapper: (
      id: Int,
      from: Int,
      to: Int,
      euro_price: Float,
      travel_data: String
    ) -> T
  ): Query<T>

  fun selectMixedRoutes(from: Int, to: Int): Query<Routes>

  fun <T : Any> selectFlyingRoutes(
    from: Int,
    to: Int,
    mapper: (
      id: Int,
      from: Int,
      to: Int,
      euro_price: Float,
      travel_data: String
    ) -> T
  ): Query<T>

  fun selectFlyingRoutes(from: Int, to: Int): Query<Flying_routes>

  fun <T : Any> selectFixedRoutes(
    from: Int,
    to: Int,
    mapper: (
      id: Int,
      from: Int,
      to: Int,
      euro_price: Float,
      travel_data: String
    ) -> T
  ): Query<T>

  fun selectFixedRoutes(from: Int, to: Int): Query<Fixed_routes>

  fun <T : Any> selectRoutesWithoutRideShare(
    from: Int,
    to: Int,
    mapper: (
      id: Int,
      from: Int,
      to: Int,
      euro_price: Float,
      travel_data: String
    ) -> T
  ): Query<T>

  fun selectRoutesWithoutRideShare(from: Int, to: Int): Query<Routes_without_ride_share>

  fun <T : Any> selectFixedRoutesWithoutRideShare(
    from: Int,
    to: Int,
    mapper: (
      id: Int,
      from: Int,
      to: Int,
      euro_price: Float,
      travel_data: String
    ) -> T
  ): Query<T>

  fun selectFixedRoutesWithoutRideShare(from: Int, to: Int): Query<Fixed_routes_without_ride_share>

  fun selectTransportationTypeNameById(id: Int): Query<String>
}
