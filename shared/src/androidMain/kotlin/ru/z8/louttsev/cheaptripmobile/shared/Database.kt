/**
 * CheapTrip Mobile
 * This is mobile client for LowCostsTrip server.
 */
package ru.z8.louttsev.cheaptripmobile.shared

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import ru.z8.louttsev.cheaptripmobile.MR
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

/**
 * A platform-specific implementation of the SQLite driver factory.
 */
actual class DatabaseDriverFactory(private val context: Context) {
    actual fun createDriver(schema: SqlDriver.Schema, fileName: String): SqlDriver {
        return AndroidSqliteDriver(schema, context, fileName)
    }

    // TODO mark deprecate and/or remove, issue #1
    actual fun getDriver(schema: SqlDriver.Schema, fileName: String): SqlDriver {
        val database: File = context.getDatabasePath(fileName)
        val checkCode = BuildConfig.DB_FILE_CHECK_CODE

        if (!database.exists()) {
            deployDatabase(database)
            saveDbCheckCodePreference(checkCode)
        } else {
            if (checkCode != loadDbCheckCodePreference()) {
                database.delete()
                deployDatabase(database)
                saveDbCheckCodePreference(checkCode)
            }
        }

        return AndroidSqliteDriver(schema, context, fileName)
    }

    // TODO mark deprecate and/or remove, issue #1
    private fun deployDatabase(database: File) {
        fun createDirectoryIfNotExist(database: File) {
            val directory = File(database.absolutePath.substringBeforeLast('/'))

            if (!directory.exists()) {
                directory.mkdir()
            }
        }

        val inputStream = context.resources.openRawResource(MR.files.fullDb.rawResId)

        createDirectoryIfNotExist(database)
        val outputStream = FileOutputStream(database.absolutePath)

        inputStream.use { input: InputStream ->
            outputStream.use { output: FileOutputStream ->
                input.copyTo(output)
            }
        }
    }

    // TODO mark deprecate and/or remove, issue #1
    @Suppress("SameParameterValue")
    private fun saveDbCheckCodePreference(checkCode: String) {
        context.getSharedPreferences("application-settings", Context.MODE_PRIVATE)
            .edit()
            .putString("db-file-check-code", checkCode)
            .apply()
    }

    // TODO mark deprecate and/or remove, issue #1
    private fun loadDbCheckCodePreference() =
        context.getSharedPreferences("application-settings", Context.MODE_PRIVATE)
            .getString("db-file-check-code", "")
}