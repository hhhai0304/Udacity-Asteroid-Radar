package com.udacity.asteroidradar.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.database.AppDatabase
import com.udacity.asteroidradar.database.entity.toModels
import com.udacity.asteroidradar.model.Asteroid
import com.udacity.asteroidradar.model.PictureOfDay
import com.udacity.asteroidradar.network.ApiClient
import com.udacity.asteroidradar.repository.AsteroidRepository
import com.udacity.asteroidradar.util.DateTimeUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getInstance(application)
    private val repository = AsteroidRepository(database)

    private val _filter = MutableLiveData<Int>()

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    var asteroids = _filter.switchMap { thisFilter ->
        return@switchMap when (thisFilter) {
            R.id.show_rent_menu -> database.asteroidDao.getToday(DateTimeUtils.getToday())
                .map { it.toModels() }

            R.id.show_buy_menu -> database.asteroidDao.getWeek(
                DateTimeUtils.getToday(),
                DateTimeUtils.getWeekDate()
            )
                .map { it.toModels() }

            else -> database.asteroidDao.getAll().map { it.toModels() }
        }
    }

    private val _currentAsteroid = MutableLiveData<Asteroid?>()
    val currentAsteroid: LiveData<Asteroid?>
        get() = _currentAsteroid

    init {
        viewModelScope.launch {
            try {
                repository.refreshAsteroids()
            } catch (e: Exception) {
                Log.e("MainViewModel", "$e")
            }
            _filter.value = 0
            getPictureOfDay()
        }
    }

    fun onItemSelected(selected: Asteroid) {
        _currentAsteroid.value = selected
        _currentAsteroid.value = null
    }

    fun onFilterChanged(newFilter: Int) {
        _filter.value = newFilter
    }

    private suspend fun getPictureOfDay() {
        withContext(Dispatchers.IO) {
            try {
                _pictureOfDay.postValue(ApiClient.asteroidService.getPictureOfDay())
            } catch (e: Exception) {
                Log.e("MainViewModel", "$e")
            }
        }
    }
}