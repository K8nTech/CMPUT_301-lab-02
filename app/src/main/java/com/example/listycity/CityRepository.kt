package com.example.listycity

import androidx.compose.runtime.mutableStateListOf

class CityRepository {
    private val _cities = mutableStateListOf(
        "Edmonton", "Vancouver", "Calgary",
        "Montréal", "Toronto", "Winnipeg"
    )
    val cities: List<String>
        get() = _cities
    fun addCity(city: String) {
        _cities.add(city)
    }
    fun deleteCity(city: String) {
        _cities.remove(city)
    }
}