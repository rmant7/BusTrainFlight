/**
 * CheapTrip Mobile
 * This is mobile client for LowCostsTrip server.
 */
package ru.z8.louttsev.bustrainflightmobile.androidApp.model.data

import android.content.Context
import ru.z8.louttsev.bustrainflightmobile.androidApp.R

/**
 * Provides a duration conversion to string representations.
 */
class DurationConverter(private val context: Context) {
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
            days.format(R.string.formatted_days_time_component),
            hours.format(R.string.formatted_hours_time_component),
            minutes.format(R.string.formatted_minutes_time_component)
        ).filter { it.isNotEmpty() }.joinToString(" ")
    }

    private fun Int.format(resourceId: Int): String =
        if (this != 0) context.resources.getString(resourceId).format(this) else ""

}