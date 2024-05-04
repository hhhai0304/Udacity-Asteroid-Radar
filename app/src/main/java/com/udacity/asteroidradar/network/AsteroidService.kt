package com.udacity.asteroidradar.network

import com.udacity.asteroidradar.model.PictureOfDay
import retrofit2.http.GET
import retrofit2.http.Query

interface AsteroidService {
    @GET("neo/rest/v1/feed")
    suspend fun getFeed(@Query("start_date") startDate: String): String

    @GET("planetary/apod")
    suspend fun getPictureOfDay(): PictureOfDay
}