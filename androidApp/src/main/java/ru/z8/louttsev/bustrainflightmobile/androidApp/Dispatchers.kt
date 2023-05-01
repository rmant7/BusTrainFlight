/**
 * CheapTrip Mobile
 * This is mobile client for LowCostsTrip server.
 */
package ru.z8.louttsev.bustrainflightmobile.androidApp

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

/**
 * A platform-specific property implementation for I/O dispatcher.
 */
val ioDispatcher: CoroutineContext
    get() = Dispatchers.IO

/**
 * A platform-specific property implementation for UI dispatcher.
 */
val uiDispatcher: CoroutineContext
    get() = Dispatchers.Main
