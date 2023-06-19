package ru.z8.louttsev.bustrainflightmobile.shared.infrastructure.datasource.shared

import com.squareup.sqldelight.Query
import com.squareup.sqldelight.TransacterImpl
import com.squareup.sqldelight.db.SqlCursor
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.internal.copyOnWriteList
import kotlin.Any
import kotlin.Float
import kotlin.Int
import kotlin.String
import kotlin.collections.Collection
import kotlin.collections.MutableList
import kotlin.jvm.JvmField
import kotlin.reflect.KClass
import ru.z8.louttsev.bustrainflightmobile.shared.infrastructure.datasource.Fixed_routes
import ru.z8.louttsev.bustrainflightmobile.shared.infrastructure.datasource.Fixed_routes_without_ride_share
import ru.z8.louttsev.bustrainflightmobile.shared.infrastructure.datasource.Flying_routes
import ru.z8.louttsev.bustrainflightmobile.shared.infrastructure.datasource.FullDb
import ru.z8.louttsev.bustrainflightmobile.shared.infrastructure.datasource.FullDbQueries
import ru.z8.louttsev.bustrainflightmobile.shared.infrastructure.datasource.Routes
import ru.z8.louttsev.bustrainflightmobile.shared.infrastructure.datasource.Routes_without_ride_share
import ru.z8.louttsev.bustrainflightmobile.shared.infrastructure.datasource.SelectLocationNamesById
import ru.z8.louttsev.bustrainflightmobile.shared.infrastructure.datasource.SelectLocationsByName
import ru.z8.louttsev.bustrainflightmobile.shared.infrastructure.datasource.Travel_data

internal val KClass<FullDb>.schema: SqlDriver.Schema
  get() = FullDbImpl.Schema

internal fun KClass<FullDb>.newInstance(driver: SqlDriver): FullDb = FullDbImpl(driver)

private class FullDbImpl(
  driver: SqlDriver
) : TransacterImpl(driver), FullDb {
  override val fullDbQueries: FullDbQueriesImpl = FullDbQueriesImpl(this, driver)

  object Schema : SqlDriver.Schema {
    override val version: Int
      get() = 1

    override fun create(driver: SqlDriver) {
      driver.execute(null, """
          |CREATE TABLE IF NOT EXISTS locations (
          |  id INTEGER NOT NULL PRIMARY KEY,
          |  name TEXT NOT NULL,
          |  country_id INTEGER NOT NULL,
          |  latitude REAL DEFAULT NULL,
          |  longitude REAL DEFAULT NULL,
          |  name_ru TEXT NOT NULL
          |)
          """.trimMargin(), 0)
      driver.execute(null, """
          |CREATE TABLE IF NOT EXISTS travel_data (
          |  id INTEGER NOT NULL PRIMARY KEY,
          |  `from` INTEGER NOT NULL,
          |  `to` INTEGER NOT NULL,
          |  transportation_type INTEGER DEFAULT NULL,
          |  euro_price REAL NOT NULL,
          |  time_in_minutes INTEGER DEFAULT NULL,
          |  FOREIGN KEY (`from`) REFERENCES locations(id),
          |  FOREIGN KEY (`to`) REFERENCES locations(id)
          |)
          """.trimMargin(), 0)
      driver.execute(null, """
          |CREATE TABLE IF NOT EXISTS routes (
          |  id INTEGER NOT NULL PRIMARY KEY,
          |  `from` INTEGER NOT NULL,
          |  `to` INTEGER NOT NULL,
          |  euro_price REAL NOT NULL,
          |  travel_data TEXT NOT NULL,
          |  FOREIGN KEY (`from`) REFERENCES locations(id),
          |  FOREIGN KEY (`to`) REFERENCES locations(id)
          |)
          """.trimMargin(), 0)
      driver.execute(null, """
          |CREATE TABLE IF NOT EXISTS flying_routes (
          |  id INTEGER NOT NULL PRIMARY KEY,
          |  `from` INTEGER NOT NULL,
          |  `to` INTEGER NOT NULL,
          |  euro_price REAL NOT NULL,
          |  travel_data TEXT NOT NULL,
          |  FOREIGN KEY (`from`) REFERENCES locations(id),
          |  FOREIGN KEY (`to`) REFERENCES locations(id)
          |)
          """.trimMargin(), 0)
      driver.execute(null, """
          |CREATE TABLE IF NOT EXISTS fixed_routes (
          |  id INTEGER NOT NULL PRIMARY KEY,
          |  `from` INTEGER NOT NULL,
          |  `to` INTEGER NOT NULL,
          |  euro_price REAL NOT NULL,
          |  travel_data TEXT NOT NULL,
          |  FOREIGN KEY (`from`) REFERENCES locations(id),
          |  FOREIGN KEY (`to`) REFERENCES locations(id)
          |)
          """.trimMargin(), 0)
      driver.execute(null, """
          |CREATE TABLE IF NOT EXISTS routes_without_ride_share (
          |  id INTEGER NOT NULL PRIMARY KEY,
          |  `from` INTEGER NOT NULL,
          |  `to` INTEGER NOT NULL,
          |  euro_price REAL NOT NULL,
          |  travel_data TEXT COLLATE BINARY NOT NULL
          |)
          """.trimMargin(), 0)
      driver.execute(null, """
          |CREATE TABLE IF NOT EXISTS fixed_routes_without_ride_share (
          |  id INTEGER NOT NULL PRIMARY KEY,
          |  `from` INTEGER NOT NULL,
          |  `to` INTEGER NOT NULL,
          |  euro_price REAL NOT NULL,
          |  travel_data TEXT COLLATE BINARY NOT NULL
          |)
          """.trimMargin(), 0)
      driver.execute(null, """
          |CREATE TABLE IF NOT EXISTS transportation_types (
          |  id INTEGER NOT NULL PRIMARY KEY,
          |  name TEXT NOT NULL
          |)
          """.trimMargin(), 0)
      driver.execute(null, """
          |CREATE TABLE IF NOT EXISTS countries (
          |  country_name TEXT DEFAULT NULL,
          |  country_id INTEGER NOT NULL PRIMARY KEY,
          |  country_name_ru TEXT DEFAULT NULL
          |)
          """.trimMargin(), 0)
      driver.execute(null, """
          |CREATE TABLE IF NOT EXISTS currencies (
          |  id INTEGER NOT NULL PRIMARY KEY,
          |  name TEXT NOT NULL,
          |  code TEXT NOT NULL,
          |  symbol TEXT NOT NULL,
          |  one_euro_rate REAL DEFAULT NULL,
          |  r2r_symbol TEXT DEFAULT NULL
          |)
          """.trimMargin(), 0)
      driver.execute(null, """
          |CREATE INDEX IF NOT EXISTS
          |    locations_country_id_index
          |ON
          |    locations(country_id)
          """.trimMargin(), 0)
      driver.execute(null, """
          |CREATE INDEX IF NOT EXISTS
          |    travel_data_ffk_idx
          |ON
          |    travel_data(`from`)
          """.trimMargin(), 0)
      driver.execute(null, """
          |CREATE INDEX IF NOT EXISTS
          |    travel_data_tfk_idx
          |ON
          |    travel_data(`to`)
          """.trimMargin(), 0)
      driver.execute(null, """
          |CREATE UNIQUE INDEX IF NOT EXISTS
          |    routes_route_from_to_index
          |ON
          |    routes(`from`, `to`)
          """.trimMargin(), 0)
      driver.execute(null, """
          |CREATE INDEX IF NOT EXISTS
          |    routes_travel_data_ffk_idx
          |ON
          |    routes(`from`)
          """.trimMargin(), 0)
      driver.execute(null, """
          |CREATE INDEX IF NOT EXISTS
          |    routes_travel_data_tfk_idx
          |ON
          |    routes(`to`)
          """.trimMargin(), 0)
      driver.execute(null, """
          |CREATE INDEX IF NOT EXISTS
          |    routes_travel_data_rffk_idx
          |ON
          |    routes(`from`)
          """.trimMargin(), 0)
      driver.execute(null, """
          |CREATE INDEX IF NOT EXISTS
          |    routes_travel_data_rtfk_idx
          |ON
          |    routes(`to`)
          """.trimMargin(), 0)
      driver.execute(null, """
          |CREATE UNIQUE INDEX IF NOT EXISTS
          |    flying_routes_route_from_to_index
          |ON
          |    flying_routes(`from`, `to`)
          """.trimMargin(), 0)
      driver.execute(null, """
          |CREATE INDEX IF NOT EXISTS
          |    flying_routes_travel_data_flrtfk
          |ON
          |    flying_routes(`to`)
          """.trimMargin(), 0)
      driver.execute(null, """
          |CREATE UNIQUE INDEX IF NOT EXISTS
          |    fixed_routes_route_from_to_index
          |ON
          |    fixed_routes(`from`, `to`)
          """.trimMargin(), 0)
      driver.execute(null, """
          |CREATE INDEX IF NOT EXISTS
          |    fixed_routes_travel_data_frffk_idx
          |ON
          |    fixed_routes(`from`)
          """.trimMargin(), 0)
      driver.execute(null, """
          |CREATE INDEX IF NOT EXISTS
          |    fixed_routes_travel_data_frtfk_idx
          |ON
          |    fixed_routes(`to`)
          """.trimMargin(), 0)
      driver.execute(null, """
          |CREATE INDEX IF NOT EXISTS
          |    fixed_routes_travel_data_flrffk_idx
          |ON
          |    fixed_routes(`from`)
          """.trimMargin(), 0)
      driver.execute(null, """
          |CREATE INDEX IF NOT EXISTS
          |    fixed_routes_travel_data_flrtfk_idx
          |ON
          |    fixed_routes(`to`)
          """.trimMargin(), 0)
      driver.execute(null, """
          |CREATE UNIQUE INDEX IF NOT EXISTS
          |    routes_without_ride_share_route_from_to_index
          |ON
          |    routes_without_ride_share(`from`, `to`)
          """.trimMargin(), 0)
      driver.execute(null, """
          |CREATE INDEX IF NOT EXISTS
          |    routes_without_ride_share_travel_data_ffk_idx
          |ON
          |    routes_without_ride_share(`from`)
          """.trimMargin(), 0)
      driver.execute(null, """
          |CREATE INDEX IF NOT EXISTS
          |    routes_without_ride_share_travel_data_rffk_idx
          |ON
          |    routes_without_ride_share(`from`)
          """.trimMargin(), 0)
      driver.execute(null, """
          |CREATE INDEX IF NOT EXISTS
          |    routes_without_ride_share_travel_data_rtfk_idx
          |ON
          |    routes_without_ride_share(`to`)
          """.trimMargin(), 0)
      driver.execute(null, """
          |CREATE INDEX IF NOT EXISTS
          |    routes_without_ride_share_travel_data_tfk_idx
          |ON
          |    routes_without_ride_share(`to`)
          """.trimMargin(), 0)
      driver.execute(null, """
          |CREATE UNIQUE INDEX IF NOT EXISTS
          |    fixed_routes_without_ride_share_route_from_to_index
          |ON
          |    fixed_routes_without_ride_share(`from`, `to`)
          """.trimMargin(), 0)
      driver.execute(null, """
          |CREATE INDEX IF NOT EXISTS
          |    fixed_routes_without_ride_share_travel_data_flrffk_idx
          |ON
          |    fixed_routes_without_ride_share(`from`)
          """.trimMargin(), 0)
      driver.execute(null, """
          |CREATE INDEX IF NOT EXISTS
          |    fixed_routes_without_ride_share_travel_data_flrtfk_idx
          |ON
          |    fixed_routes_without_ride_share(`to`)
          """.trimMargin(), 0)
      driver.execute(null, """
          |CREATE INDEX IF NOT EXISTS
          |    fixed_routes_without_ride_share_travel_data_frffk_idx
          |ON
          |    fixed_routes_without_ride_share(`from`)
          """.trimMargin(), 0)
      driver.execute(null, """
          |CREATE INDEX IF NOT EXISTS
          |    fixed_routes_without_ride_share_travel_data_frtfk_idx
          |ON
          |    fixed_routes_without_ride_share(`to`)
          """.trimMargin(), 0)
    }

    override fun migrate(
      driver: SqlDriver,
      oldVersion: Int,
      newVersion: Int
    ) {
    }
  }
}

private class FullDbQueriesImpl(
  private val database: FullDbImpl,
  private val driver: SqlDriver
) : TransacterImpl(driver), FullDbQueries {
  internal val selectLocationsByName: MutableList<Query<*>> = copyOnWriteList()

  internal val selectLocationNamesById: MutableList<Query<*>> = copyOnWriteList()

  internal val selectDirectPaths: MutableList<Query<*>> = copyOnWriteList()

  internal val selectPathsByIds: MutableList<Query<*>> = copyOnWriteList()

  internal val selectFromIdsOfRoutes: MutableList<Query<*>> = copyOnWriteList()

  internal val selectToIdsOfRoutes: MutableList<Query<*>> = copyOnWriteList()

  internal val selectMixedRoutes: MutableList<Query<*>> = copyOnWriteList()

  internal val selectFlyingRoutes: MutableList<Query<*>> = copyOnWriteList()

  internal val selectFixedRoutes: MutableList<Query<*>> = copyOnWriteList()

  internal val selectRoutesWithoutRideShare: MutableList<Query<*>> = copyOnWriteList()

  internal val selectFixedRoutesWithoutRideShare: MutableList<Query<*>> = copyOnWriteList()

  internal val selectTransportationTypeNameById: MutableList<Query<*>> = copyOnWriteList()

  override fun <T : Any> selectLocationsByName(needle: String, mapper: (
    id: Int,
    name: String,
    name_ru: String
  ) -> T): Query<T> = SelectLocationsByNameQuery(needle) { cursor ->
    mapper(
      cursor.getLong(0)!!.toInt(),
      cursor.getString(1)!!,
      cursor.getString(2)!!
    )
  }

  override fun selectLocationsByName(needle: String): Query<SelectLocationsByName> =
      selectLocationsByName(needle) { id, name, name_ru ->
    SelectLocationsByName(
      id,
      name,
      name_ru
    )
  }

  override fun <T : Any> selectLocationNamesById(id: Int, mapper: (name: String, name_ru: String) ->
      T): Query<T> = SelectLocationNamesByIdQuery(id) { cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getString(1)!!
    )
  }

  override fun selectLocationNamesById(id: Int): Query<SelectLocationNamesById> =
      selectLocationNamesById(id) { name, name_ru ->
    SelectLocationNamesById(
      name,
      name_ru
    )
  }

  override fun <T : Any> selectDirectPaths(
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
  ): Query<T> = SelectDirectPathsQuery(from, to) { cursor ->
    mapper(
      cursor.getLong(0)!!.toInt(),
      cursor.getLong(1)!!.toInt(),
      cursor.getLong(2)!!.toInt(),
      cursor.getLong(3)?.toInt(),
      cursor.getDouble(4)!!.toFloat(),
      cursor.getLong(5)?.toInt()
    )
  }

  override fun selectDirectPaths(from: Int, to: Int): Query<Travel_data> = selectDirectPaths(from,
      to) { id, from_, to_, transportation_type, euro_price, time_in_minutes ->
    Travel_data(
      id,
      from_,
      to_,
      transportation_type,
      euro_price,
      time_in_minutes
    )
  }

  override fun <T : Any> selectPathsByIds(pathIds: Collection<Int>, mapper: (
    id: Int,
    from: Int,
    to: Int,
    transportation_type: Int?,
    euro_price: Float,
    time_in_minutes: Int?
  ) -> T): Query<T> = SelectPathsByIdsQuery(pathIds) { cursor ->
    mapper(
      cursor.getLong(0)!!.toInt(),
      cursor.getLong(1)!!.toInt(),
      cursor.getLong(2)!!.toInt(),
      cursor.getLong(3)?.toInt(),
      cursor.getDouble(4)!!.toFloat(),
      cursor.getLong(5)?.toInt()
    )
  }

  override fun selectPathsByIds(pathIds: Collection<Int>): Query<Travel_data> =
      selectPathsByIds(pathIds) { id, from, to, transportation_type, euro_price, time_in_minutes ->
    Travel_data(
      id,
      from,
      to,
      transportation_type,
      euro_price,
      time_in_minutes
    )
  }

  override fun selectFromIdsOfRoutes(): Query<Int> = Query(-1715687230, selectFromIdsOfRoutes,
      driver, "FullDb.sq", "selectFromIdsOfRoutes", """
  |SELECT DISTINCT
  |    `from`
  |FROM
  |    routes
  """.trimMargin()) { cursor ->
    cursor.getLong(0)!!.toInt()
  }

  override fun selectToIdsOfRoutes(): Query<Int> = Query(-968204783, selectToIdsOfRoutes, driver,
      "FullDb.sq", "selectToIdsOfRoutes", """
  |SELECT DISTINCT
  |    `to`
  |FROM
  |    routes
  """.trimMargin()) { cursor ->
    cursor.getLong(0)!!.toInt()
  }

  override fun <T : Any> selectMixedRoutes(
    from: Int,
    to: Int,
    mapper: (
      id: Int,
      from: Int,
      to: Int,
      euro_price: Float,
      travel_data: String
    ) -> T
  ): Query<T> = SelectMixedRoutesQuery(from, to) { cursor ->
    mapper(
      cursor.getLong(0)!!.toInt(),
      cursor.getLong(1)!!.toInt(),
      cursor.getLong(2)!!.toInt(),
      cursor.getDouble(3)!!.toFloat(),
      cursor.getString(4)!!
    )
  }

  override fun selectMixedRoutes(from: Int, to: Int): Query<Routes> = selectMixedRoutes(from, to) {
      id, from_, to_, euro_price, travel_data ->
    Routes(
      id,
      from_,
      to_,
      euro_price,
      travel_data
    )
  }

  override fun <T : Any> selectFlyingRoutes(
    from: Int,
    to: Int,
    mapper: (
      id: Int,
      from: Int,
      to: Int,
      euro_price: Float,
      travel_data: String
    ) -> T
  ): Query<T> = SelectFlyingRoutesQuery(from, to) { cursor ->
    mapper(
      cursor.getLong(0)!!.toInt(),
      cursor.getLong(1)!!.toInt(),
      cursor.getLong(2)!!.toInt(),
      cursor.getDouble(3)!!.toFloat(),
      cursor.getString(4)!!
    )
  }

  override fun selectFlyingRoutes(from: Int, to: Int): Query<Flying_routes> =
      selectFlyingRoutes(from, to) { id, from_, to_, euro_price, travel_data ->
    Flying_routes(
      id,
      from_,
      to_,
      euro_price,
      travel_data
    )
  }

  override fun <T : Any> selectFixedRoutes(
    from: Int,
    to: Int,
    mapper: (
      id: Int,
      from: Int,
      to: Int,
      euro_price: Float,
      travel_data: String
    ) -> T
  ): Query<T> = SelectFixedRoutesQuery(from, to) { cursor ->
    mapper(
      cursor.getLong(0)!!.toInt(),
      cursor.getLong(1)!!.toInt(),
      cursor.getLong(2)!!.toInt(),
      cursor.getDouble(3)!!.toFloat(),
      cursor.getString(4)!!
    )
  }

  override fun selectFixedRoutes(from: Int, to: Int): Query<Fixed_routes> = selectFixedRoutes(from,
      to) { id, from_, to_, euro_price, travel_data ->
    Fixed_routes(
      id,
      from_,
      to_,
      euro_price,
      travel_data
    )
  }

  override fun <T : Any> selectRoutesWithoutRideShare(
    from: Int,
    to: Int,
    mapper: (
      id: Int,
      from: Int,
      to: Int,
      euro_price: Float,
      travel_data: String
    ) -> T
  ): Query<T> = SelectRoutesWithoutRideShareQuery(from, to) { cursor ->
    mapper(
      cursor.getLong(0)!!.toInt(),
      cursor.getLong(1)!!.toInt(),
      cursor.getLong(2)!!.toInt(),
      cursor.getDouble(3)!!.toFloat(),
      cursor.getString(4)!!
    )
  }

  override fun selectRoutesWithoutRideShare(from: Int, to: Int): Query<Routes_without_ride_share> =
      selectRoutesWithoutRideShare(from, to) { id, from_, to_, euro_price, travel_data ->
    Routes_without_ride_share(
      id,
      from_,
      to_,
      euro_price,
      travel_data
    )
  }

  override fun <T : Any> selectFixedRoutesWithoutRideShare(
    from: Int,
    to: Int,
    mapper: (
      id: Int,
      from: Int,
      to: Int,
      euro_price: Float,
      travel_data: String
    ) -> T
  ): Query<T> = SelectFixedRoutesWithoutRideShareQuery(from, to) { cursor ->
    mapper(
      cursor.getLong(0)!!.toInt(),
      cursor.getLong(1)!!.toInt(),
      cursor.getLong(2)!!.toInt(),
      cursor.getDouble(3)!!.toFloat(),
      cursor.getString(4)!!
    )
  }

  override fun selectFixedRoutesWithoutRideShare(from: Int, to: Int):
      Query<Fixed_routes_without_ride_share> = selectFixedRoutesWithoutRideShare(from, to) { id,
      from_, to_, euro_price, travel_data ->
    Fixed_routes_without_ride_share(
      id,
      from_,
      to_,
      euro_price,
      travel_data
    )
  }

  override fun selectTransportationTypeNameById(id: Int): Query<String> =
      SelectTransportationTypeNameByIdQuery(id) { cursor ->
    cursor.getString(0)!!
  }

  private inner class SelectLocationsByNameQuery<out T : Any>(
    @JvmField
    val needle: String,
    mapper: (SqlCursor) -> T
  ) : Query<T>(selectLocationsByName, mapper) {
    override fun execute(): SqlCursor = driver.executeQuery(616174707, """
    |SELECT
    |    id, name, name_ru
    |FROM
    |    locations
    |WHERE
    |    name LIKE ('%' || ? || '%')
    |  OR
    |    name_ru LIKE ('%' || ? || '%')
    """.trimMargin(), 2) {
      bindString(1, needle)
      bindString(2, needle)
    }

    override fun toString(): String = "FullDb.sq:selectLocationsByName"
  }

  private inner class SelectLocationNamesByIdQuery<out T : Any>(
    @JvmField
    val id: Int,
    mapper: (SqlCursor) -> T
  ) : Query<T>(selectLocationNamesById, mapper) {
    override fun execute(): SqlCursor = driver.executeQuery(-1304017352, """
    |SELECT
    |    name, name_ru
    |FROM
    |    locations
    |WHERE
    |    id = ?
    """.trimMargin(), 1) {
      bindLong(1, id.toLong())
    }

    override fun toString(): String = "FullDb.sq:selectLocationNamesById"
  }

  private inner class SelectDirectPathsQuery<out T : Any>(
    @JvmField
    val from: Int,
    @JvmField
    val to: Int,
    mapper: (SqlCursor) -> T
  ) : Query<T>(selectDirectPaths, mapper) {
    override fun execute(): SqlCursor = driver.executeQuery(-657922056, """
    |SELECT
    |    *
    |FROM
    |    travel_data
    |WHERE
    |    `from` = ?
    |  AND
    |    `to` = ?
    """.trimMargin(), 2) {
      bindLong(1, from.toLong())
      bindLong(2, to.toLong())
    }

    override fun toString(): String = "FullDb.sq:selectDirectPaths"
  }

  private inner class SelectPathsByIdsQuery<out T : Any>(
    @JvmField
    val pathIds: Collection<Int>,
    mapper: (SqlCursor) -> T
  ) : Query<T>(selectPathsByIds, mapper) {
    override fun execute(): SqlCursor {
      val pathIdsIndexes = createArguments(count = pathIds.size)
      return driver.executeQuery(null, """
      |SELECT
      |    *
      |FROM
      |    travel_data
      |WHERE
      |    id IN $pathIdsIndexes
      |ORDER BY
      |    id
      """.trimMargin(), pathIds.size) {
        pathIds.forEachIndexed { index, pathIds_ ->
            bindLong(index + 1, pathIds_.toLong())
            }
      }
    }

    override fun toString(): String = "FullDb.sq:selectPathsByIds"
  }

  private inner class SelectMixedRoutesQuery<out T : Any>(
    @JvmField
    val from: Int,
    @JvmField
    val to: Int,
    mapper: (SqlCursor) -> T
  ) : Query<T>(selectMixedRoutes, mapper) {
    override fun execute(): SqlCursor = driver.executeQuery(-1962069864, """
    |SELECT
    |    *
    |FROM
    |    routes
    |WHERE
    |    `from` = ?
    |  AND
    |    `to` = ?
    """.trimMargin(), 2) {
      bindLong(1, from.toLong())
      bindLong(2, to.toLong())
    }

    override fun toString(): String = "FullDb.sq:selectMixedRoutes"
  }

  private inner class SelectFlyingRoutesQuery<out T : Any>(
    @JvmField
    val from: Int,
    @JvmField
    val to: Int,
    mapper: (SqlCursor) -> T
  ) : Query<T>(selectFlyingRoutes, mapper) {
    override fun execute(): SqlCursor = driver.executeQuery(-660286554, """
    |SELECT
    |    *
    |FROM
    |    flying_routes
    |WHERE
    |    `from` = ?
    |  AND
    |    `to` = ?
    """.trimMargin(), 2) {
      bindLong(1, from.toLong())
      bindLong(2, to.toLong())
    }

    override fun toString(): String = "FullDb.sq:selectFlyingRoutes"
  }

  private inner class SelectFixedRoutesQuery<out T : Any>(
    @JvmField
    val from: Int,
    @JvmField
    val to: Int,
    mapper: (SqlCursor) -> T
  ) : Query<T>(selectFixedRoutes, mapper) {
    override fun execute(): SqlCursor = driver.executeQuery(2026655057, """
    |SELECT
    |    *
    |FROM
    |    fixed_routes
    |WHERE
    |    `from` = ?
    |  AND
    |    `to` = ?
    """.trimMargin(), 2) {
      bindLong(1, from.toLong())
      bindLong(2, to.toLong())
    }

    override fun toString(): String = "FullDb.sq:selectFixedRoutes"
  }

  private inner class SelectRoutesWithoutRideShareQuery<out T : Any>(
    @JvmField
    val from: Int,
    @JvmField
    val to: Int,
    mapper: (SqlCursor) -> T
  ) : Query<T>(selectRoutesWithoutRideShare, mapper) {
    override fun execute(): SqlCursor = driver.executeQuery(-889538058, """
    |SELECT
    |    *
    |FROM
    |    routes_without_ride_share
    |WHERE
    |    `from` = ?
    |  AND
    |    `to` = ?
    """.trimMargin(), 2) {
      bindLong(1, from.toLong())
      bindLong(2, to.toLong())
    }

    override fun toString(): String = "FullDb.sq:selectRoutesWithoutRideShare"
  }

  private inner class SelectFixedRoutesWithoutRideShareQuery<out T : Any>(
    @JvmField
    val from: Int,
    @JvmField
    val to: Int,
    mapper: (SqlCursor) -> T
  ) : Query<T>(selectFixedRoutesWithoutRideShare, mapper) {
    override fun execute(): SqlCursor = driver.executeQuery(1203904848, """
    |SELECT
    |    *
    |FROM
    |    fixed_routes_without_ride_share
    |WHERE
    |    `from` = ?
    |  AND
    |    `to` = ?
    """.trimMargin(), 2) {
      bindLong(1, from.toLong())
      bindLong(2, to.toLong())
    }

    override fun toString(): String = "FullDb.sq:selectFixedRoutesWithoutRideShare"
  }

  private inner class SelectTransportationTypeNameByIdQuery<out T : Any>(
    @JvmField
    val id: Int,
    mapper: (SqlCursor) -> T
  ) : Query<T>(selectTransportationTypeNameById, mapper) {
    override fun execute(): SqlCursor = driver.executeQuery(-621391856, """
    |SELECT
    |    name
    |FROM
    |    transportation_types
    |WHERE
    |    id = ?
    """.trimMargin(), 1) {
      bindLong(1, id.toLong())
    }

    override fun toString(): String = "FullDb.sq:selectTransportationTypeNameById"
  }
}
