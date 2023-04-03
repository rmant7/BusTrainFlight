/**
 * CheapTrip Mobile
 * This is mobile client for LowCostsTrip server.
 */
package ru.z8.louttsev.cheaptripmobile.shared

import com.squareup.sqldelight.db.SqlDriver

/**
 * A platform-specific declaration of the SQLite driver factory.
 */
expect class DatabaseDriverFactory {
    /**
     * Provides implementation of platform-specific SQL driver for new database.
     *
     * @param schema Database schema
     * @param fileName Local file for database storage
     * @return Object to create database and run statements on it
     */
    fun createDriver(schema: SqlDriver.Schema, fileName: String): SqlDriver

    // TODO mark deprecate and/or remove, issue #1
    /**
     * Provides implementation of platform-specific SQL driver for pre-populated database.
     *
     * @param schema Database schema
     * @param fileName Local file for database storage
     * @return Object to create database and run statements on it
     */
    fun getDriver(schema: SqlDriver.Schema, fileName: String): SqlDriver
}