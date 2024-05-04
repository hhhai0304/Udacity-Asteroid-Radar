package com.udacity.asteroidradar.repository

import com.udacity.asteroidradar.database.AppDatabase
import com.udacity.asteroidradar.model.toEntities
import com.udacity.asteroidradar.network.ApiClient
import com.udacity.asteroidradar.network.parseAsteroidsJsonResult
import com.udacity.asteroidradar.util.DateTimeUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AsteroidRepository(private val database: AppDatabase) {
    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            val rawData = ApiClient.asteroidService.getFeed(DateTimeUtils.getToday())
            val json = JSONObject(rawData)
            database.asteroidDao.insertAll(parseAsteroidsJsonResult(json).toEntities())
        }
    }

    suspend fun cleanExpiredAsteroids() {
        withContext(Dispatchers.IO) {
            database.asteroidDao.deleteAllExpiredData(DateTimeUtils.getToday())
        }
    }
}