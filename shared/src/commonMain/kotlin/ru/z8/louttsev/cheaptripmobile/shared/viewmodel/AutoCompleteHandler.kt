/**
 * CheapTrip Mobile
 * This is mobile client for LowCostsTrip server.
 */
package ru.z8.louttsev.cheaptripmobile.shared.viewmodel

import dev.icerock.moko.mvvm.livedata.LiveData
import ru.z8.louttsev.cheaptripmobile.shared.model.data.Locale

/**
 * Determines UI actions handle logic and data for autocomplete input field.
 *
 * @property data Items list
 * @property isBeingUpdated Model update indicator
 */
interface AutoCompleteHandler<T> {
    val data: LiveData<List<T>>
    var isBeingUpdated: Boolean
    var isBeingBackspaced: Boolean
    var wasSelected: Boolean

    /**
     * Updates data accordingly search pattern.
     *
     * @param text Typed part of autocomplete input field
     */
    fun onTextChanged(text: String, locale: Locale, emptyResultHandler: () -> Unit)

    /**
     * Sets selected item.
     *
     * @param item Selected item into autocomplete input field
     */
    fun onItemSelected(item: T, invalidSelectionHandler: () -> Unit)

    /**
     * Resets the previously selected item.
     */
    fun onItemReset()

    /**
     * Checks item selection.
     *
     * @return true if item was selected earlier
     */
    fun isItemSelected(): Boolean
}