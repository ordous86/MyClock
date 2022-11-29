package com.lucian.myclock

import java.lang.IllegalArgumentException
import java.util.*

/**
 * Repository of time information.
 */
class ClockRepository
{
    // Companion.
    companion object
    {
        // Constants.
        const val ROC_START_YEAR = 1911
        const val UNIT_HOUR_12 = "12 hour"
        const val UNIT_HOUR_24 = "24 hour"
        const val UNIT_YEAR_AC = "A.C. year"
        const val UNIT_YEAR_ROC = "R.O.C. year"
    }


    // Fields.
    var hourUnit = UNIT_HOUR_24
    var yearUnit = UNIT_YEAR_AC


    // Called to load day of the current time.
    inline fun loadDay(onDayLoaded: (day: String) -> Unit) = Calendar.getInstance().get(Calendar.DATE).toString().apply(onDayLoaded)


    // Called to load hour of the current time.
    inline fun loadHour(onHourLoaded: (hour: String) -> Unit)
    {
        val hour = when(this.hourUnit)
        {
            UNIT_HOUR_12 -> Calendar.getInstance().get(Calendar.HOUR).let {
                if (it == 0)
                    "12"
                else
                    it.toString()
            } + if(Calendar.getInstance().get(Calendar.AM_PM) == 0)
                    "AM"
                else
                    "PM"
            UNIT_HOUR_24 -> Calendar.getInstance().get(Calendar.HOUR_OF_DAY).toString()
            else -> throw IllegalArgumentException("Invalid hour unit")
        }
        onHourLoaded(hour)
    }


    // Called to load hour unit.
    inline fun loadHourUnit(onHourUnitLoaded: (hourUnit: String) -> Unit) = onHourUnitLoaded(this.hourUnit)


    // Called to load minute of the current time.
    inline fun loadMinute(onMinuteLoaded: (minute: String) -> Unit) = Calendar.getInstance().get(Calendar.MINUTE).toString().apply(onMinuteLoaded)


    // Called to load month of the current time.
    inline fun loadMonth(onMonthLoaded: (month: String) -> Unit) = Calendar.getInstance().getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())?.apply(onMonthLoaded)


    // Called to load second of the current time.
    inline fun loadSecond(onSecondLoaded: (second: String) -> Unit) = Calendar.getInstance().get(Calendar.SECOND).toString().apply(onSecondLoaded)


    // Called to load year of the current time.
    inline fun loadYear(onYearLoaded: (year: String) -> Unit)
    {
        val year = when(this.yearUnit)
        {
            UNIT_YEAR_AC -> Calendar.getInstance().get(Calendar.YEAR).toString()
            UNIT_YEAR_ROC -> (Calendar.getInstance().get(Calendar.YEAR) - ROC_START_YEAR).toString()
            else -> throw IllegalArgumentException("Invalid year unit")
        }
        onYearLoaded(year)
    }


    // Called to load year unit.
    inline fun loadYearUnit(onYearUnitLoaded: (yearUnit: String) -> Unit) = onYearUnitLoaded(this.yearUnit)


    // Called to switch hour unit.
    inline fun switchHourUnit(onHourUnitChanged: (hour: String, hourUnit: String) -> Unit)
    {
        val hour: String
        when(this.hourUnit)
        {
            UNIT_HOUR_12 ->
            {
                this.hourUnit = UNIT_HOUR_24
                hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY).toString()
            }
            UNIT_HOUR_24 ->
            {
                this.hourUnit = UNIT_HOUR_12
                hour = Calendar.getInstance().get(Calendar.HOUR).let {
                    if (it == 0)
                        "12"
                    else
                        it.toString()
                } + if(Calendar.getInstance().get(Calendar.AM_PM) == 0)
                        "AM"
                    else
                        "PM"
            }
            else -> throw IllegalArgumentException("Invalid hour unit")
        }
        onHourUnitChanged(hour, this.hourUnit)
    }


    // Called to switch year unit.
    inline fun switchYearUnit(onYearUnitLoaded: (year: String, yearUnit: String) -> Unit)
    {
        val year: String
        when(this.yearUnit)
        {
            UNIT_YEAR_ROC ->
            {
                this.yearUnit = UNIT_YEAR_AC
                year = Calendar.getInstance().get(Calendar.YEAR).toString()
            }
            UNIT_YEAR_AC ->
            {
                this.yearUnit = UNIT_YEAR_ROC
                year = (Calendar.getInstance().get(Calendar.YEAR) - ROC_START_YEAR).toString()
            }
            else -> throw IllegalArgumentException("Invalid year unit")
        }
        onYearUnitLoaded(year, this.yearUnit)
    }
}