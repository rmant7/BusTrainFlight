/**
 * CheapTrip Mobile
 * This is mobile client for LowCostsTrip server.
 */
package ru.z8.louttsev.cheaptripmobile.shared

import com.squareup.sqldelight.db.SqlDriver

/**
 * A platform-specific implementation of the SQLite driver factory.
 */
actual class DatabaseDriverFactory {
    actual fun createDriver(schema: SqlDriver.Schema, fileName: String): SqlDriver {
        TODO("Not yet implemented, issue #25")
    }

    // TODO mark deprecate and/or remove, issue #1
    actual fun getDriver(schema: SqlDriver.Schema, fileName: String): SqlDriver {
        TODO("Not yet implemented, issue #25")
    }

}