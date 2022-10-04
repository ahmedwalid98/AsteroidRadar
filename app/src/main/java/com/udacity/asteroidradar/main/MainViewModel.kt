package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.ApiFilter
import com.udacity.asteroidradar.api.getSeventhDay
import com.udacity.asteroidradar.api.getToday
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AsteroidDatabase.getInstance(application)
    private val repository = AsteroidRepository(database)
    private val asteroid:LiveData<List<Asteroid>> = repository.asteroids
    private val _pictureOfTheDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
    get() = _pictureOfTheDay
    private val _navigateToDetailScreen = MutableLiveData<Asteroid>()
    val navigatToDetailScreen: LiveData<Asteroid>
    get() = _navigateToDetailScreen
    private val todayAsteroid:LiveData<List<Asteroid>> = repository.getToday
    private val weekAsteroid:LiveData<List<Asteroid>> = repository.getWeeks

    private var _asteroids = MutableLiveData<List<Asteroid>>()
    val asteroids: LiveData<List<Asteroid>>
        get() = _asteroids
    init {
        refreshData()
        getPhotoOfDay()
    }

    private fun getPhotoOfDay() {
        viewModelScope.launch {
            try {
                _pictureOfTheDay.value = repository.getPicture()
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    fun refreshData() {
        viewModelScope.launch {
            try {
                asteroid.asFlow().collect {
                    _asteroids.value = it
                }
                repository.refreshData()
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    fun setonItemClick(asteroid: Asteroid){
        _navigateToDetailScreen.value = asteroid
    }
    fun onItemClicked(){
        _navigateToDetailScreen.value = null
    }
    fun updateWeek(){
        viewModelScope.launch {
            weekAsteroid.asFlow().collect{
                _asteroids.value = it
            }
        }
    }

    fun updateToday(){
        viewModelScope.launch {
            todayAsteroid.asFlow().collect{
                _asteroids.value = it
            }

        }
    }


}