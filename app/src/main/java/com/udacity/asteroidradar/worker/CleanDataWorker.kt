package com.udacity.asteroidradar.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.database.AppDatabase
import com.udacity.asteroidradar.repository.AsteroidRepository

class CleanDataWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "CleanDataWorker"
    }

    override suspend fun doWork(): Result {
        val database = AppDatabase.getInstance(applicationContext)
        val repository = AsteroidRepository(database)

        return try {
            repository.cleanExpiredAsteroids()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}