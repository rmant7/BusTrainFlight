/**
 * CheapTrip Mobile
 * This is mobile client for LowCostsTrip server.
 */
package ru.z8.louttsev.cheaptripmobile.shared.model

// TODO add implementation based on Ktor multi-platform client
// to package ru.z8.louttsev.cheaptripmobile.shared.infrastructure.datasource, issue #1
/**
 * Determines read-only data access logic.
 */
interface DataSource<T> {
    /**
     * Finds all data matching parameters.
     *
     * @param parameters Request specification
     * @return List of matching results (m.b. empty) or null if an error occurred
     */
    fun get(parameters: ParamsBundle): List<T>?

    /**
     * Declares a merger of isolated parameters.
     */
    class ParamsBundle {
        private val params = emptyMap<String, Any>().toMutableMap()

        /**
         * Adds parameter into bundle.
         *
         * @param key Allowable parameter's key
         * @param value Added parameter
         */
        fun put(key: Key, value: Any) {
            params[key.value] = value
        }

        /**
         * Extracts parameter from bundle.
         *
         * @param key Allowable parameter's key
         * @return Extracted parameter or null if it wasn't added early
         */
        fun get(key: Key): Any? {
            return params[key.value]
        }

        /**
         * Declares list of valid parameter keys.
         */
        enum class Key(internal val value: String) {
            NEEDLE("needle"),
            TYPE("type"),
            FROM("from"),
            TO("to"),
            LIMIT("limit"),
            LOCALE("locale");
        }
    }
}
