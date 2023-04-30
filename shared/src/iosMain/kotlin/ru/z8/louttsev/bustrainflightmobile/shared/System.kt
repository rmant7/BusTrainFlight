/**
 * CheapTrip Mobile
 * This is mobile client for LowCostsTrip server.
 */
package ru.z8.louttsev.bustrainflightmobile.androidApp

import ru.z8.louttsev.bustrainflightmobile.androidApp.model.data.Locale

/**
 * A platform-specific property implementation for default language.
 */
actual val currentLocale: Locale
    // TODO change implementation to use the saved user select as the current locale, issue #13
    get() = TODO("Not yet implemented, issue#25")

// TODO implement val currentCountry, issue #3