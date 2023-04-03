/**
 * CheapTrip Mobile
 * This is mobile client for LowCostsTrip server.
 */
package ru.z8.louttsev.cheaptripmobile.shared.model.data

/**
 * Declares supported language.
 *
 * @property languageCode Two-letter code accordingly ISO 639-1
 */
enum class Locale(val languageCode: String) {
    RU("ru"), EN("en");

    override fun toString(): String {
        return languageCode
    }

    companion object {
        /**
         * Gives the locale by its string representation.
         *
         * @param languageCode Two-letter code accordingly ISO 639-1
         * @return Supported Locale or EN as default
         */
        fun fromLanguageCode(languageCode: String): Locale {
            return values().find { it.languageCode == languageCode } ?: EN
        }
    }
}
