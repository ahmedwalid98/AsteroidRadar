package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.NetworkConstants
import com.udacity.asteroidradar.PictureOfDay
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create


object AsteroidAPI {
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(NetworkConstants.BASE_URL)
        .build()

    private val retrofitInstance: AsteroidService by lazy {
        retrofit.create(AsteroidService::class.java)
    }

    suspend fun getAsteroids(): List<Asteroid>{
        val asteroids =retrofitInstance.getAsteroids(getToday(), getSeventhDay(),NetworkConstants.API_KEY)
        val jsonObject = JSONObject(asteroids)

        return parseAsteroidsJsonResult(jsonObject)
    }

    suspend fun getPicture(): PictureOfDay = retrofitInstance.getPictureToday(NetworkConstants.API_KEY)
}