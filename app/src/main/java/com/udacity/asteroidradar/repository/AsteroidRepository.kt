package com.udacity.asteroidradar.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.AsteroidAPI
import com.udacity.asteroidradar.api.asAsteroidEntity
import com.udacity.asteroidradar.api.getSeventhDay
import com.udacity.asteroidradar.api.getToday
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.asAsteroids

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AsteroidRepository(private val database: AsteroidDatabase) {

    val asteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.dao.getAsteroids()) {
            it.asAsteroids()
        }
    private val today = getToday()
    private val sevenDays = getSeventhDay()
    suspend fun refreshData(){
        withContext(Dispatchers.IO){
            val asteroids = AsteroidAPI.getAsteroids()
            database.dao.insertToDatabase(asteroids.asAsteroidEntity())
        }
    }

    suspend fun getPicture():PictureOfDay{
        lateinit var pictureOfDay: PictureOfDay
        withContext(Dispatchers.IO){
            pictureOfDay = AsteroidAPI.getPicture()
        }
        return pictureOfDay
    }
    val getWeeks = Transformations.map(database.dao.getWeekAsteroids(today,sevenDays)){
        it.asAsteroids()
    }

    val getToday = Transformations.map(database.dao.getTodayAsteroids(getToday())){
        it.asAsteroids()
    }

}