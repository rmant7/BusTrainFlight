/**
 * CheapTrip Mobile
 * This is mobile client for LowCostsTrip server.
 */
package ru.z8.louttsev.cheaptripmobile.shared

import dev.icerock.moko.resources.desc.StringDesc

/**
 * A platform-specific conversion implementation for common string resources.
 */
actual var convertToString: StringDesc.() -> String = { localized() }