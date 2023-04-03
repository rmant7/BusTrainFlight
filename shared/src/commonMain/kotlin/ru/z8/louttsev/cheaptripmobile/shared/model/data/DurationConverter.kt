/**
 * CheapTrip Mobile
 * This is mobile client for LowCostsTrip server.
 */
package ru.z8.louttsev.cheaptripmobile.shared.model.data

import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.format
import ru.z8.louttsev.cheaptripmobile.MR
import ru.z8.louttsev.cheaptripmobile.shared.convertToString

/**
 * Provides a duration conversion to string representations.
 */
class DurationConverter {
    companion object {
        /**
         * Converts duration in minutes to string representation based on days, hours and minutes.
         *
         * @param value Time period in minutes
         */
        fun minutesToTimeComponents(value: Int): String {
            fun divmod(dividend: Int, divider: Int): Pair<Int, Int> =
                dividend / divider to dividend % divider

            var (days, residue) = divmod(value, 1440)
            var (hours, minutes) = divmod(residue, 60)

            // reason: display no more than two groups together
            // solution: if there are already days and hours, round up minutes
            // and correct the values of hours and days if necessary
            if (days > 0 && hours != 0) {
                hours += minutes / 30
                minutes = 0
                days += hours / 24
                hours %= 24
            }

            return listOf(
                days.format(MR.strings.formatted_days_time_component),
                hours.format(MR.strings.formatted_hours_time_component),
                minutes.format(MR.strings.formatted_minutes_time_component)
            ).filter { it.isNotEmpty() }.joinToString(" ")
        }

        private fun Int.format(resource: StringResource): String =
            if (this != 0) resource.format(this).convertToString() else ""
    }
}