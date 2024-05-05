// WeatherFragment.kt
package com.example.workoutgenerator

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.workoutgenerator.databinding.FragmentProfileBinding
import kotlin.math.roundToInt

class WeatherFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val weatherAPI = WeatherAPI()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root

        // displaySetCurrStats()

        // Set the values of the name, lastname and username text views
        val currWeatherView = binding.currentWeather
        val currHumidityView = binding.currentHumidity
        val currFeelsLikeView = binding.currentFeelsLike
        val currLowView = binding.currentLow
        val currHighView = binding.currentHigh
        val currWeatherTypeView = binding.weatherType
        val currUVindexView = binding.currentUVindex
        val currVisibilityView = binding.currentVisibility

        // fetch current weather
        weatherAPI.getCurrentWeather { temp ->
            temp?.let {
                currWeatherView.text = "$temp\u2109"
            }
        }

        // fetch current feels like temp
        weatherAPI.getCurrentFeelsLikeTemperature { temp ->
            temp?.let {
                currFeelsLikeView.text = "Feels Like: $temp\u2109"
            }
        }

        // fetch current humidity
        weatherAPI.getCurrentHumidity { humidity ->
            humidity?.let {
                currHumidityView.text = "Humidity: ${humidity.roundToInt()}%"
            }
        }

        // fetch current low
        weatherAPI.getCurrentLowTemperature { low ->
            low?.let {
                currLowView.text = "L: $low\u2109"
            }
        }

        // fetch current high
        weatherAPI.getCurrentHighTemperature { high ->
            high?.let {
                currHighView.text = "H: $high\u2109"
            }
        }

        // fetch current weather type
        weatherAPI.getCurrentWeatherType { type ->
            type?.let {
                var realType = ""
                if (type == "Clouds") {
                    realType = "Cloudy"
                }
                currWeatherTypeView.text = "$realType"
            }
        }

        // fetch current UVIndex
        weatherAPI.getCurrentUVIndex{ index ->
            index?.let {
                currUVindexView.text = "UV Index: ${index?.roundToInt()}"
            }
        }

        // fetch current visibility
        weatherAPI.getCurrentVisibility { visibility ->
            visibility?.let {
                currVisibilityView.text = "Visibility: ${visibility?.roundToInt()} mi"
            }
        }

        return view
    }
}
