package com.example.workoutgenerator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlin.math.roundToInt

class WeatherFragment : Fragment() {
    private val weatherAPI = WeatherAPI()

    private lateinit var currWeatherView: TextView
    private lateinit var currHumidityView: TextView
    private lateinit var currFeelsLikeView: TextView
    private lateinit var currLowView: TextView
    private lateinit var currHighView: TextView
    private lateinit var currWeatherTypeView: TextView
    private lateinit var currUVindexView: TextView
    private lateinit var currVisibilityView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.weathertab_layout, container, false)

        // Find views
        currWeatherView = view.findViewById(R.id.current_weather)
        currHumidityView = view.findViewById(R.id.current_humidity)
        currFeelsLikeView = view.findViewById(R.id.current_feelsLike)
        currLowView = view.findViewById(R.id.current_low)
        currHighView = view.findViewById(R.id.current_high)
        currWeatherTypeView = view.findViewById(R.id.weather_type)
        currUVindexView = view.findViewById(R.id.current_UVindex)
        currVisibilityView = view.findViewById(R.id.current_visibility)

        return view
    }

    override fun onResume() {
        super.onResume()

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
    }
}
