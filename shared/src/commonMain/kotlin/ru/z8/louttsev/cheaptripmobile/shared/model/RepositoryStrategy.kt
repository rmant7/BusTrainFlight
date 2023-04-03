/**
 * CheapTrip Mobile
 * This is mobile client for LowCostsTrip server.
 */
package ru.z8.louttsev.cheaptripmobile.shared.model

import ru.z8.louttsev.cheaptripmobile.shared.model.DataSource.ParamsBundle

/**
 * Declares data sources usage logic.
 */
enum class RepositoryStrategy {
    /**
     * Strategy implementation that receives data from main (read-only) source if it is available,
     * and saves result to reserve (full access) storage. When main source isn't available,
     * data will be received from reserve source.
     */
    BACKUP {
        override fun <T> combineLoaderFrom(
            dataSource: DataSource<T>,
            dataStorage: DataStorage<T>
        ): (ParamsBundle) -> List<T> = { params: ParamsBundle ->
            val result = dataSource.get(params)
            result?.also {
                dataStorage.put(result, params)
            } ?: dataStorage.get(params)
        }
    },

    /**
     * Strategy implementation that receives data from reserve (cache) source first. If result
     * is empty, data will be received from main (read-only) source and cached to reserve (full
     * access) storage.
     */
    CACHING {
        override fun <T> combineLoaderFrom(
            dataSource: DataSource<T>,
            dataStorage: DataStorage<T>
        ): (ParamsBundle) -> List<T> = { params: ParamsBundle ->
            val cachedResult = dataStorage.get(params)

            if (cachedResult.isEmpty()) {
                val result = dataSource.get(params)
                result?.also {
                    dataStorage.put(result, params)
                } ?: emptyList()
            } else {
                cachedResult
            }
        }
    },
    DIRECT_READ {
        /**
         * Strategy implementation that receives data from main (read-only) source only.
         */
        override fun <T> combineLoaderFrom(
            dataSource: DataSource<T>,
            dataStorage: DataStorage<T>
        ): (ParamsBundle) -> List<T> = { params: ParamsBundle ->
            dataSource.get(params)!!
        }
    };

    /**
     * Creates loader function according specified logic.
     *
     * @param dataSource Read-only data access source
     * @param dataStorage Full data access source
     * @return Function that encapsulates data sources interaction
     */
    abstract fun <T> combineLoaderFrom(
        dataSource: DataSource<T>,
        dataStorage: DataStorage<T>
    ): (ParamsBundle) -> List<T>
}
