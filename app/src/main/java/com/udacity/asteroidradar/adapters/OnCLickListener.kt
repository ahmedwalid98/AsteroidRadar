package com.udacity.asteroidradar.adapters

import com.udacity.asteroidradar.Asteroid

class OnCLickListener(val onCLickListener: (Asteroid) -> Unit) {
    fun onClick(asteroid: Asteroid) =  onCLickListener(asteroid)
}