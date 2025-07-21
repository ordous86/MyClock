package com.lucian.myclock

import android.content.res.Configuration
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowInsetsController
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.WindowInsetsControllerCompat
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


    // Configuration change.
    override fun onConfigurationChanged(newConfig: Configuration)
    {
        // call super
        super.onConfigurationChanged(newConfig)

        // check UI mode
        onUiModeChange(newConfig)

        // recreate to take effect
        recreate()
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

        // check initial UI mode
        onUiModeChange(resources.configuration)
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


    // UI mode change.
    private fun onUiModeChange(config: Configuration)
    {
        // check light or dark
        val currentNightMode = (config.uiMode and Configuration.UI_MODE_NIGHT_MASK)
        val isLightTheme = when (AppCompatDelegate.getDefaultNightMode()) {
            AppCompatDelegate.MODE_NIGHT_NO -> true
            AppCompatDelegate.MODE_NIGHT_YES -> false
            else -> (currentNightMode != Configuration.UI_MODE_NIGHT_YES)
        }

        // handle by build version
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            val insetsController = WindowInsetsControllerCompat(window, window.decorView)
            insetsController.isAppearanceLightStatusBars = isLightTheme
        } else {
            window.insetsController?.setSystemBarsAppearance(
                if (isLightTheme) WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS else 0,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        }
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