package com.travelapp.bustrainflightmobile.shared.infrastructure.persistence.shared

import com.squareup.sqldelight.Query
import com.squareup.sqldelight.TransacterImpl
import com.squareup.sqldelight.db.SqlCursor
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.internal.copyOnWriteList
import kotlin.Any
import kotlin.Float
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.collections.MutableList
import kotlin.jvm.JvmField
import kotlin.reflect.KClass
import com.travelapp.bustrainflightmobile.shared.infrastructure.persistence.LocalDb
import com.travelapp.bustrainflightmobile.shared.infrastructure.persistence.LocalDbQueries
import com.travelapp.bustrainflightmobile.shared.infrastructure.persistence.SelectLocationsByName
import com.travelapp.bustrainflightmobile.shared.infrastructure.persistence.SelectPathsByRouteId
import com.travelapp.bustrainflightmobile.shared.infrastructure.persistence.SelectRoutesByLocations

internal val KClass<LocalDb>.schema: SqlDriver.Schema
  get() = LocalDbImpl.Schema

internal fun KClass<LocalDb>.newInstance(driver: SqlDriver): LocalDb = LocalDbImpl(driver)

private class LocalDbImpl(
  driver: SqlDriver
) : TransacterImpl(driver), LocalDb {
  override val localDbQueries: LocalDbQueriesImpl = LocalDbQueriesImpl(this, driver)

  object Schema : SqlDriver.Schema {
    override val version: Int
      get() = 1

    override fun create(driver: SqlDriver) {
      driver.execute(null, """
          |CREATE TABLE IF NOT EXISTS location (
          |    id INTEGER NOT NULL,
          |    name TEXT NOT NULL,
          |    type_name TEXT NOT NULL,
          |    language_code TEXT NOT NULL,
          |    PRIMARY KEY (id, type_name, language_code)
          |)
          """.trimMargin(), 0)
      driver.execute(null, """
          |CREATE TABLE IF NOT EXISTS route (
          |    id INTEGER NOT NULL PRIMARY KEY,
          |    type_name TEXT NOT NULL,
          |    price REAL NOT NULL,
          |    duration INTEGER NOT NULL,
          |    from_location_name TEXT NOT NULL,
          |    to_location_name TEXT NOT NULL,
          |    language_code TEXT NOT NULL
          |)
          """.trimMargin(), 0)
      driver.execute(null, """
          |CREATE TABLE IF NOT EXISTS path (
          |    transportation_type_name TEXT NOT NULL,
          |    price REAL NOT NULL,
          |    duration INTEGER NOT NULL,
          |    from_location_name TEXT NOT NULL,
          |    to_location_name TEXT NOT NULL,
          |    route_id INTEGER NOT NULL,
          |    FOREIGN KEY (route_id) REFERENCES route(id)
          |)
          """.trimMargin(), 0)
      driver.execute(null, """
          |CREATE INDEX IF NOT EXISTS
          |    secondary
          |ON
          |    route(type_name, from_location_name, to_location_name, language_code)
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

private class LocalDbQueriesImpl(
  private val database: LocalDbImpl,
  private val driver: SqlDriver
) : TransacterImpl(driver), LocalDbQueries {
  internal val selectLocationsByName: MutableList<Query<*>> = copyOnWriteList()

  internal val selectRouteId: MutableList<Query<*>> = copyOnWriteList()

  internal val selectRoutesByLocations: MutableList<Query<*>> = copyOnWriteList()

  internal val selectPathsByRouteId: MutableList<Query<*>> = copyOnWriteList()

  override fun <T : Any> selectLocationsByName(
    needle: String,
    limit: Long,
    mapper: (id: Int, name: String) -> T
  ): Query<T> = SelectLocationsByNameQuery(needle, limit) { cursor ->
    mapper(
      cursor.getLong(0)!!.toInt(),
      cursor.getString(1)!!
    )
  }

  override fun selectLocationsByName(needle: String, limit: Long): Query<SelectLocationsByName> =
      selectLocationsByName(needle, limit) { id, name ->
    SelectLocationsByName(
      id,
      name
    )
  }

  override fun selectRouteId(
    typeName: String,
    fromLocationName: String,
    toLocationName: String,
    languageCode: String
  ): Query<Long> = SelectRouteIdQuery(typeName, fromLocationName, toLocationName, languageCode) {
      cursor ->
    cursor.getLong(0)!!
  }

  override fun <T : Any> selectRoutesByLocations(
    fromLocationName: String,
    toLocationName: String,
    languageCode: String,
    mapper: (
      id: Long,
      type_name: String,
      price: Float,
      duration: Int
    ) -> T
  ): Query<T> = SelectRoutesByLocationsQuery(fromLocationName, toLocationName, languageCode) {
      cursor ->
    mapper(
      cursor.getLong(0)!!,
      cursor.getString(1)!!,
      cursor.getDouble(2)!!.toFloat(),
      cursor.getLong(3)!!.toInt()
    )
  }

  override fun selectRoutesByLocations(
    fromLocationName: String,
    toLocationName: String,
    languageCode: String
  ): Query<SelectRoutesByLocations> = selectRoutesByLocations(fromLocationName, toLocationName,
      languageCode) { id, type_name, price, duration ->
    SelectRoutesByLocations(
      id,
      type_name,
      price,
      duration
    )
  }

  override fun <T : Any> selectPathsByRouteId(routeId: Long, mapper: (
    transportation_type_name: String,
    price: Float,
    duration: Int,
    from_location_name: String,
    to_location_name: String
  ) -> T): Query<T> = SelectPathsByRouteIdQuery(routeId) { cursor ->
    mapper(
      cursor.getString(0)!!,
      cursor.getDouble(1)!!.toFloat(),
      cursor.getLong(2)!!.toInt(),
      cursor.getString(3)!!,
      cursor.getString(4)!!
    )
  }

  override fun selectPathsByRouteId(routeId: Long): Query<SelectPathsByRouteId> =
      selectPathsByRouteId(routeId) { transportation_type_name, price, duration, from_location_name,
      to_location_name ->
    SelectPathsByRouteId(
      transportation_type_name,
      price,
      duration,
      from_location_name,
      to_location_name
    )
  }

  override fun deletePathsByRouteId(routeId: Long) {
    driver.execute(-1815929241, """
    |DELETE FROM
    |    path
    |WHERE
    |    route_id = ?
    """.trimMargin(), 1) {
      bindLong(1, routeId)
    }
    notifyQueries(-1815929241, {database.localDbQueries.selectPathsByRouteId})
  }

  override fun insertPath(
    transportationTypeName: String,
    price: Float,
    duration: Int,
    fromLocationName: String,
    toLocationName: String,
    routeId: Long
  ) {
    driver.execute(-1701830917, """
    |INSERT INTO
    |    path
    |    (transportation_type_name, price, duration, from_location_name, to_location_name, route_id)
    |VALUES
    |    (?, ?, ?, ?, ?, ?)
    """.trimMargin(), 6) {
      bindString(1, transportationTypeName)
      bindDouble(2, price.toDouble())
      bindLong(3, duration.toLong())
      bindString(4, fromLocationName)
      bindString(5, toLocationName)
      bindLong(6, routeId)
    }
    notifyQueries(-1701830917, {database.localDbQueries.selectPathsByRouteId})
  }

  override fun updateOrInsertLocation(
    name: String,
    id: Int,
    typeName: String,
    languageCode: String
  ) {
    driver.execute(-674551768, """
    |UPDATE
    |      location
    |  SET
    |      name = ?
    |  WHERE
    |      id = ? AND type_name = ? AND language_code = ?
    """.trimMargin(), 4) {
      bindString(1, name)
      bindLong(2, id.toLong())
      bindString(3, typeName)
      bindString(4, languageCode)
    }
    driver.execute(-674551767, """
    |INSERT OR IGNORE INTO
    |      location
    |      (id, name, type_name, language_code)
    |  VALUES
    |      (?, ?, ?, ?)
    """.trimMargin(), 4) {
      bindLong(1, id.toLong())
      bindString(2, name)
      bindString(3, typeName)
      bindString(4, languageCode)
    }
    notifyQueries(1107676727, {database.localDbQueries.selectLocationsByName})
  }

  override fun updateOrInsertRoute(
    price: Float,
    duration: Int,
    typeName: String,
    fromLocationName: String,
    toLocationName: String,
    languageCode: String
  ) {
    driver.execute(251676920, """
    |UPDATE
    |        route
    |    SET
    |        price = ?,
    |        duration = ?
    |    WHERE
    |        type_name = ?
    |      AND
    |        from_location_name = ?
    |      AND
    |        to_location_name = ?
    |      AND
    |        language_code = ?
    """.trimMargin(), 6) {
      bindDouble(1, price.toDouble())
      bindLong(2, duration.toLong())
      bindString(3, typeName)
      bindString(4, fromLocationName)
      bindString(5, toLocationName)
      bindString(6, languageCode)
    }
    driver.execute(251676921, """
    |INSERT OR IGNORE INTO
    |        route
    |        (type_name, price, duration, from_location_name, to_location_name, language_code)
    |    VALUES
    |        (?, ?, ?, ?, ?, ?)
    """.trimMargin(), 6) {
      bindString(1, typeName)
      bindDouble(2, price.toDouble())
      bindLong(3, duration.toLong())
      bindString(4, fromLocationName)
      bindString(5, toLocationName)
      bindString(6, languageCode)
    }
    notifyQueries(111993607, {database.localDbQueries.selectRouteId +
        database.localDbQueries.selectRoutesByLocations})
  }

  private inner class SelectLocationsByNameQuery<out T : Any>(
    @JvmField
    val needle: String,
    @JvmField
    val limit: Long,
    mapper: (SqlCursor) -> T
  ) : Query<T>(selectLocationsByName, mapper) {
    override fun execute(): SqlCursor = driver.executeQuery(1054302023, """
    |SELECT
    |    id, name
    |FROM
    |    location
    |WHERE
    |    name LIKE ('%' || ? || '%')
    |LIMIT
    |    ?
    """.trimMargin(), 2) {
      bindString(1, needle)
      bindLong(2, limit)
    }

    override fun toString(): String = "LocalDb.sq:selectLocationsByName"
  }

  private inner class SelectRouteIdQuery<out T : Any>(
    @JvmField
    val typeName: String,
    @JvmField
    val fromLocationName: String,
    @JvmField
    val toLocationName: String,
    @JvmField
    val languageCode: String,
    mapper: (SqlCursor) -> T
  ) : Query<T>(selectRouteId, mapper) {
    override fun execute(): SqlCursor = driver.executeQuery(1961273099, """
    |SELECT
    |    id
    |FROM
    |    route
    |WHERE
    |    type_name = ?
    |  AND
    |    from_location_name = ?
    |  AND
    |    to_location_name = ?
    |  AND
    |    language_code = ?
    """.trimMargin(), 4) {
      bindString(1, typeName)
      bindString(2, fromLocationName)
      bindString(3, toLocationName)
      bindString(4, languageCode)
    }

    override fun toString(): String = "LocalDb.sq:selectRouteId"
  }

  private inner class SelectRoutesByLocationsQuery<out T : Any>(
    @JvmField
    val fromLocationName: String,
    @JvmField
    val toLocationName: String,
    @JvmField
    val languageCode: String,
    mapper: (SqlCursor) -> T
  ) : Query<T>(selectRoutesByLocations, mapper) {
    override fun execute(): SqlCursor = driver.executeQuery(-949491676, """
    |SELECT
    |    id, type_name, price, duration
    |FROM
    |    route
    |WHERE
    |    from_location_name = ?
    |  AND
    |    to_location_name = ?
    |  AND
    |    language_code = ?
    """.trimMargin(), 3) {
      bindString(1, fromLocationName)
      bindString(2, toLocationName)
      bindString(3, languageCode)
    }

    override fun toString(): String = "LocalDb.sq:selectRoutesByLocations"
  }

  private inner class SelectPathsByRouteIdQuery<out T : Any>(
    @JvmField
    val routeId: Long,
    mapper: (SqlCursor) -> T
  ) : Query<T>(selectPathsByRouteId, mapper) {
    override fun execute(): SqlCursor = driver.executeQuery(1869229048, """
    |SELECT
    |    transportation_type_name, price, duration, from_location_name, to_location_name
    |FROM
    |    path
    |WHERE
    |    route_id = ?
    """.trimMargin(), 1) {
      bindLong(1, routeId)
    }

    override fun toString(): String = "LocalDb.sq:selectPathsByRouteId"
  }
}
