package com.udacity.asteroidradar.app

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.work.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class BaseApplication : Application() {
    private val applicationScope = CoroutineScope(Dispatchers.Default)
    override fun onCreate() {
        super.onCreate()
        delayInit()
    }

    private fun delayInit() = applicationScope.launch {
        registerWorker()
    }

    private fun registerWorker() {
        Log.i("registerWorker: ","start " )
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .setRequiresCharging(true)
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    setRequiresDeviceIdle(true)
                }
            }.build()

        val repeated = PeriodicWorkRequestBuilder<AsteroidWorker>(
            1,
            TimeUnit.DAYS
        ).setConstraints(constraints)
            .build()

        WorkManager.getInstance().enqueueUniquePeriodicWork(
            AsteroidWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.REPLACE,
            repeated
        )
    }


}