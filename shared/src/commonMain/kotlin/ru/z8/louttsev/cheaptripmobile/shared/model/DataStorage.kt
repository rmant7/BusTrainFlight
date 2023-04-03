/**
 * CheapTrip Mobile
 * This is mobile client for LowCostsTrip server.
 */
package ru.z8.louttsev.cheaptripmobile.shared.model

import ru.z8.louttsev.cheaptripmobile.shared.model.DataSource.ParamsBundle

/**
 * Determines full data access logic.
 */
interface DataStorage<T> : DataSource<T> {
    /**
     * Saves a data.
     *
     * @param data List of saving data (m.b. empty)
     * @param parameters Source request specification
     */
    fun put(data: List<T>, parameters: ParamsBundle)

    /**
     * Narrows data source result to nonnull value, excluding internal errors.
     */
    override fun get(parameters: ParamsBundle): List<T>
}
