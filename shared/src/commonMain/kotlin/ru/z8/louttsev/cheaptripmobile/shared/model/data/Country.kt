/**
 * CheapTrip Mobile
 * This is mobile client for LowCostsTrip server.
 */
package ru.z8.louttsev.cheaptripmobile.shared.model.data

/**
 * Declares countries relevant to affiliate programs.
 *
 * @property countryCode Alpha-2 code accordingly ISO 3166-1
 */
enum class Country(val countryCode: String) {
    RUSSIA("RU"), BELARUS("BY"), UKRAINE("UA"), INDIA ("IN"), INDEFINITE("");

    companion object {
        /**
         * Gives the country by its string representation.
         *
         * @param countryCode countryCode Alpha-2 code accordingly ISO 3166-1
         * @return Supported country or undefined as default
         */
        fun fromCountryCode(countryCode: String): Country {
            return values().find { it.countryCode == countryCode } ?: INDEFINITE
        }
    }
}