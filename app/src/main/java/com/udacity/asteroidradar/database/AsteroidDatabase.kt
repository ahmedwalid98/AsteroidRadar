package com.udacity.asteroidradar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.udacity.asteroidradar.DatabaseConstants

@Database(entities = [AsteroidEntity::class], version = 1, exportSchema = false)
abstract class AsteroidDatabase: RoomDatabase() {
    abstract val dao:AsteroidDao
    companion object{
        private lateinit var INSTANCE: AsteroidDatabase
        fun getInstance(context: Context): AsteroidDatabase{
            synchronized(AsteroidDatabase::class.java){
                if (!::INSTANCE.isInitialized){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AsteroidDatabase::class.java,
                        DatabaseConstants.DATABASE_FILE_NAME
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}