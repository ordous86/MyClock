package com.lucian.myclock

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.lucian.myclock.databinding.ActivityClockBinding

/**
 * View for displaying time information.
 */
class ClockActivity: AppCompatActivity()
{
    // Companion.
    companion object
    {
        // Constants.
        private const val REPEAT_INTERVAL = 1000L
    }


    // Fields.
    private val handler: Handler by lazy {
        Handler(Looper.getMainLooper())
    }
    private val viewModel: ClockViewModel by lazy {
        ViewModelProvider(this, ClockViewModelFactory(ClockRepository()))[ClockViewModel::class.java]
    }


    // Create.
    override fun onCreate(savedInstanceState: Bundle?)
    {
        // call super
        super.onCreate(savedInstanceState)

        // initialize data binding
        DataBindingUtil.setContentView<ActivityClockBinding>(this, R.layout.activity_clock).let {
            it.clockViewModel = this.viewModel
            it.lifecycleOwner = this
        }
    }


    // Start.
    override fun onStart()
    {
        // call super
        super.onStart()

        // repeat loading
        this.handler.post {
            this.repeatLoading()
        }
    }


    // Stop.
    override fun onStop()
    {
        // call super
        super.onStop()

        // stop repeating
        this.handler.removeCallbacksAndMessages(null)
    }


    // Repeat loading data from view model.
    private fun repeatLoading()
    {
        // load time
        this.viewModel.loadTime()

        // schedule next loop
        this.handler.postDelayed(
            { this.repeatLoading() }, REPEAT_INTERVAL)
    }
}