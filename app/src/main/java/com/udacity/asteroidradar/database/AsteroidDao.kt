package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.database.entity.AsteroidEntity

@Dao
interface AsteroidDao {
    @Query("SELECT * FROM Asteroid ORDER BY closeApproachDate ASC")
    fun getAll(): LiveData<List<AsteroidEntity>>

    @Query("SELECT * FROM Asteroid WHERE closeApproachDate = :today")
    fun getToday(today: String): LiveData<List<AsteroidEntity>>

    @Query("SELECT * FROM Asteroid WHERE closeApproachDate BETWEEN :today AND :weekDate ORDER BY closeApproachDate ASC")
    fun getWeek(today: String, weekDate: String): LiveData<List<AsteroidEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(data: List<AsteroidEntity>)

    @Query("DELETE FROM Asteroid WHERE closeApproachDate < :today")
    fun deleteAllExpiredData(today: String)
}