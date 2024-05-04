package com.udacity.asteroidradar

import android.app.Application
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.udacity.asteroidradar.worker.CleanDataWorker
import com.udacity.asteroidradar.worker.RefreshDataWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class App : Application() {
    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        delayedInit()
    }

    private fun delayedInit() {
        applicationScope.launch {
            setupRefreshDataWork()
        }
    }

    private fun setupRefreshDataWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(true)
            .build()

        val repeatRefreshRequest =
            PeriodicWorkRequestBuilder<RefreshDataWorker>(1, TimeUnit.DAYS)
                .setConstraints(constraints)
                .build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            RefreshDataWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatRefreshRequest
        )

        val repeatCleanRequest =
            PeriodicWorkRequestBuilder<CleanDataWorker>(1, TimeUnit.DAYS)
                .setConstraints(constraints)
                .build()
        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            CleanDataWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatCleanRequest
        )
    }
}