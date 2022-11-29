package com.lucian.myclock

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

/**
 * View model to connect [ClockActivity] and [ClockRepository].
 */
class ClockViewModel(private val repository: ClockRepository): ViewModel()
{
    // Fields for data binding.
    val dayValue = ObservableField<String>()
    val hourValue = ObservableField<String>()
    val minuteValue = ObservableField<String>()
    val monthValue = ObservableField<String>()
    val secondValue = ObservableField<String>()
    val switchHourBtnName = ObservableField<String>()
    val switchYearBtnName = ObservableField<String>()
    val yearValue = ObservableField<String>()


    // Called to load the current time.
    fun loadTime()
    {
        this.repository.apply {
            loadYear(::onYearLoaded)
            loadMonth(::onMonthLoaded)
            loadDay(::onDayLoaded)
            loadHour(::onHourLoaded)
            loadMinute(::onMinuteLoaded)
            loadSecond(::onSecondLoaded)
            loadYearUnit(::onYearUnitLoaded)
            loadHourUnit(::onHourUnitLoaded)
        }
    }


    // Called when day is loaded.
    private fun onDayLoaded(day: String) = this.dayValue.set(day)


    // Called when hour is loaded.
    private fun onHourLoaded(hour: String) = this.hourValue.set(hour)


    // Called when hour unit is changed
    private fun onHourUnitChanged(hour: String, hourUnit: String)
    {
        this.hourValue.set(hour)
        this.switchHourBtnName.set(hourUnit)
    }


    // Called when hour unit is loaded.
    private fun onHourUnitLoaded(hourUnit: String) = this.switchHourBtnName.set(hourUnit)


    // Called when minute is loaded.
    private fun onMinuteLoaded(minute: String) = this.minuteValue.set(minute)


    // Called when month is loaded.
    private fun onMonthLoaded(month: String) = this.monthValue.set(month)


    // Called when second is loaded.
    private fun onSecondLoaded(second: String) = this.secondValue.set(second)


    // Called to switch hour unit.
    fun onSwitchHourUnit() = this.repository.switchHourUnit(::onHourUnitChanged)


    // Called to switch year unit.
    fun onSwitchYearUnit() = this.repository.switchYearUnit(::onYearUnitChanged)


    // Called when year is loaded.
    private fun onYearLoaded(year: String) = this.yearValue.set(year)


    // Called when year unit is changed.
    private fun onYearUnitChanged(year: String, yearUnit: String)
    {
        this.yearValue.set(year)
        this.switchYearBtnName.set(yearUnit)
    }


    // Called when year unit is loaded.
    private fun onYearUnitLoaded(yearUnit: String) = this.switchYearBtnName.set(yearUnit)
}