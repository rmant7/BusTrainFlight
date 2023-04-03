/**
 * CheapTrip Mobile
 * This is mobile client for LowCostsTrip server.
 */
package ru.z8.louttsev.cheaptripmobile.shared

import dev.icerock.moko.resources.desc.StringDesc

/**
 * A platform-specific conversion implementation for common string resources.
 *
 * On androidApp side need change implementation to real using activity/application context.
 */
actual var convertToString: StringDesc.() -> String = { "" } // stub