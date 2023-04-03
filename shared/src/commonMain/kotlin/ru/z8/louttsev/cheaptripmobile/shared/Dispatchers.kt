/**
 * CheapTrip Mobile
 * This is mobile client for LowCostsTrip server.
 */
package ru.z8.louttsev.cheaptripmobile.shared

import kotlin.coroutines.CoroutineContext

/**
 * A platform-specific property declaration for I/O dispatcher.
 */
expect val ioDispatcher: CoroutineContext

/**
 * A platform-specific property declaration for UI dispatcher.
 */
expect val uiDispatcher: CoroutineContext
