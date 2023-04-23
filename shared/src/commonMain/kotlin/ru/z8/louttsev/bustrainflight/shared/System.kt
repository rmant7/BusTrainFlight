/**
 * CheapTrip Mobile
 * This is mobile client for LowCostsTrip server.
 */
package ru.z8.louttsev.bustrainflight.shared

import ru.z8.louttsev.bustrainflight.shared.model.data.Locale

/**
 * A platform-specific property declaration for default language.
 */
expect val currentLocale: Locale

// TODO declare val currentCountry, issue #3