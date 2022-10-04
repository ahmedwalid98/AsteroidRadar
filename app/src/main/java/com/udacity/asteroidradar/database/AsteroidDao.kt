package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.DatabaseConstants
import kotlinx.coroutines.selects.select

@Dao
interface AsteroidDao {

    @Query("SELECT * FROM ${DatabaseConstants.TABLE_NAME} order by closeApproachDate")
    fun getAsteroids(): LiveData<List<AsteroidEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertToDatabase(asteroids: List<AsteroidEntity>)

    @Query("SELECT * FROM ${DatabaseConstants.TABLE_NAME} WHERE closeApproachDate BETWEEN :today AND :endWeek ORDER BY closeApproachDate")
    fun getWeekAsteroids(today: String, endWeek: String): LiveData<List<AsteroidEntity>>

    @Query("SELECT * FROM ${DatabaseConstants.TABLE_NAME} WHERE closeApproachDate = :string ORDER BY closeApproachDate")
    fun getTodayAsteroids(string: String): LiveData<List<AsteroidEntity>>
}