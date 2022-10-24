package com.lucian.myclock

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

/**
 * Factory to build [ClockViewModel].
 */
@Suppress("UNCHECKED_CAST")
class ClockViewModelFactory(private val repository: ClockRepository): ViewModelProvider.Factory
{
    // Create view model.
    override fun <T: ViewModel> create(modelClass: Class<T>): T = when
    {
        // create
        modelClass.isAssignableFrom(ClockViewModel::class.java) -> ClockViewModel(repository) as T

        // otherwise
        else -> throw IllegalArgumentException("Unknown ViewModel")
    }
}