/**
 * CheapTrip Mobile
 * This is mobile client for LowCostsTrip server.
 */
package com.travelapp.bustrainflightmobile.androidApp.viewmodel

import androidx.lifecycle.LiveData
import com.travelapp.bustrainflightmobile.androidApp.model.data.Route

/**
 * Determines UI actions handle logic and data for build routes button.
 *
 * @property data Items list
 * @property isReadyToBuild Correct state indicator
 */
interface GoButtonHandler {
    val data: LiveData<List<Route>>
    val isReadyToBuild: LiveData<Boolean>

    /**
     * Finds possible routes between the specified points of origin and destination.
     *
     * @param emptyResultHandler UI function called if no possible routes found
     */
    fun build(emptyResultHandler: () -> Unit, onUpdate: () -> Unit)
}