package com.example.workoutgenerator

import android.os.AsyncTask
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.Math.round
import java.net.HttpURLConnection
import java.net.URL
import kotlin.math.roundToInt

class WeatherAPI {

    companion object {
        const val API_KEY: String = "f9649f8b582b368959ad95e8662a664b"
        const val DEFAULT_CITY: String = "Dayton"
    }

    // fetch current weather
    fun getCurrentWeather(city: String = DEFAULT_CITY, callback: (Double?) -> Unit) {
        FetchWeatherTask(callback).execute(city)
    }

    // fetch current humidity
    fun getCurrentHumidity(city: String = DEFAULT_CITY, callback: (Double?) -> Unit) {
        FetchHumidityTask(callback).execute(city)
    }

    // fetch current "feels like" temperature
    fun getCurrentFeelsLikeTemperature(city: String = DEFAULT_CITY, callback: (Double?) -> Unit) {
        FetchFeelsLikeTemperatureTask(callback).execute(city)
    }

    // fetch current low temperature
    fun getCurrentLowTemperature(city: String = DEFAULT_CITY, callback: (Double?) -> Unit) {
        FetchLowTemperatureTask(callback).execute(city)
    }

    // fetch current high temperature
    fun getCurrentHighTemperature(city: String = DEFAULT_CITY, callback: (Double?) -> Unit) {
        FetchHighTemperatureTask(callback).execute(city)
    }

    // fetch current weather type
    fun getCurrentWeatherType(city: String = DEFAULT_CITY, callback: (String?) -> Unit) {
        FetchWeatherTypeTask(callback).execute(city)
    }

    // fetch current visibility
    fun getCurrentVisibility(city: String = DEFAULT_CITY, callback: (Double?) -> Unit) {
        FetchVisibilityTask(callback).execute(city)
    }

    // fetch current UV index
    fun getCurrentUVIndex(city: String = DEFAULT_CITY, callback: (Double?) -> Unit) {
        FetchUVIndexTask(city, callback).execute()
    }

    private inner class FetchWeatherTask(private val callback: (Double?) -> Unit) : AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg params: String?): String? {
            var response: String? = null

            try {
                val city = params[0] ?: DEFAULT_CITY
                val url = URL("https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$API_KEY")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"

                val bufferedReader = BufferedReader(InputStreamReader(connection.inputStream))
                response = bufferedReader.use(BufferedReader::readText)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return response
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            // Parse JSON response and handle weather data
            result?.let {
                val jsonObject = JSONObject(it)
                val main = jsonObject.optJSONObject("main")
                val temperature = main?.optDouble("temp")

                // convert
                val fahrenheit = temperature?.let { kelvinToFahrenheit(it) }

                callback(fahrenheit)
            }
        }
    }

    // Humidity
    private inner class FetchHumidityTask(private val callback: (Double?) -> Unit) : AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg params: String?): String? {
            var response: String? = null

            try {
                val city = params[0] ?: DEFAULT_CITY
                val url = URL("https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$API_KEY")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"

                val bufferedReader = BufferedReader(InputStreamReader(connection.inputStream))
                response = bufferedReader.use(BufferedReader::readText)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return response
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            // Parse JSON response and handle weather data
            result?.let {
                val jsonObject = JSONObject(it)
                val main = jsonObject.optJSONObject("main")
                val humidity = main?.optDouble("humidity")

                callback(humidity)
            }
        }
    }

    // Fetch "feels like" temperature
    private inner class FetchFeelsLikeTemperatureTask(private val callback: (Double?) -> Unit) : AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg params: String?): String? {
            var response: String? = null

            try {
                val city = params[0] ?: DEFAULT_CITY
                val url = URL("https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$API_KEY")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"

                val bufferedReader = BufferedReader(InputStreamReader(connection.inputStream))
                response = bufferedReader.use(BufferedReader::readText)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return response
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            // Parse JSON response and handle weather data
            result?.let {
                val jsonObject = JSONObject(it)
                val main = jsonObject.optJSONObject("main")
                val feelsLike = main?.optDouble("feels_like")

                // Convert feels like temperature to Fahrenheit
                val fahrenheit = feelsLike?.let { kelvinToFahrenheit(it) }

                callback(fahrenheit)
            }
        }
    }

    // fetch current low temperature
    private inner class FetchLowTemperatureTask(private val callback: (Double?) -> Unit) : AsyncTask<String, Void, Double?>() {
        override fun doInBackground(vararg params: String?): Double? {
            var lowTemperature: Double? = null

            try {
                val city = params[0] ?: DEFAULT_CITY
                val url = URL("https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$API_KEY")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"

                val bufferedReader = BufferedReader(InputStreamReader(connection.inputStream))
                val response = bufferedReader.use(BufferedReader::readText)

                val jsonObject = JSONObject(response)
                val main = jsonObject.optJSONObject("main")
                lowTemperature = main?.optDouble("temp_min")

                if (lowTemperature != null) {
                    // Convert temperature from Kelvin to Fahrenheit
                    lowTemperature = kelvinToFahrenheit(lowTemperature)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return lowTemperature
        }

        override fun onPostExecute(result: Double?) {
            super.onPostExecute(result)
            callback(result)
        }
    }

    // fetch current high temperature
    private inner class FetchHighTemperatureTask(private val callback: (Double?) -> Unit) : AsyncTask<String, Void, Double?>() {
        override fun doInBackground(vararg params: String?): Double? {
            var highTemperature: Double? = null

            try {
                val city = params[0] ?: DEFAULT_CITY
                val url = URL("https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$API_KEY")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"

                val bufferedReader = BufferedReader(InputStreamReader(connection.inputStream))
                val response = bufferedReader.use(BufferedReader::readText)

                val jsonObject = JSONObject(response)
                val main = jsonObject.optJSONObject("main")
                highTemperature = main?.optDouble("temp_max")

                if (highTemperature != null) {
                    // Convert temperature from Kelvin to Fahrenheit
                    highTemperature = kelvinToFahrenheit(highTemperature)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return highTemperature
        }

        override fun onPostExecute(result: Double?) {
            super.onPostExecute(result)
            callback(result)
        }
    }

    // fetch current weather type
    private inner class FetchWeatherTypeTask(private val callback: (String?) -> Unit) : AsyncTask<String, Void, String?>() {
        override fun doInBackground(vararg params: String?): String? {
            var weatherType: String? = null

            try {
                val city = params[0] ?: DEFAULT_CITY
                val url = URL("https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$API_KEY")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"

                val bufferedReader = BufferedReader(InputStreamReader(connection.inputStream))
                val response = bufferedReader.use(BufferedReader::readText)

                val jsonObject = JSONObject(response)
                val weatherArray = jsonObject.optJSONArray("weather")
                val weatherObject = weatherArray?.optJSONObject(0)
                weatherType = weatherObject?.optString("main")
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return weatherType
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            callback(result)
        }
    }

    // fetch current visibility
    private inner class FetchVisibilityTask(private val callback: (Double?) -> Unit) : AsyncTask<String, Void, Double?>() {
        override fun doInBackground(vararg params: String?): Double? {
            var visibility: Double? = null

            try {
                val city = params[0] ?: DEFAULT_CITY
                val url = URL("https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$API_KEY")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"

                val bufferedReader = BufferedReader(InputStreamReader(connection.inputStream))
                val response = bufferedReader.use(BufferedReader::readText)

                val jsonObject = JSONObject(response)
                visibility = jsonObject.optDouble("visibility")

                // Convert visibility from meters to miles and round to one decimal place
                if (visibility != null) {
                    visibility = (visibility / 1609.34).roundTo(1)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return visibility
        }

        override fun onPostExecute(result: Double?) {
            super.onPostExecute(result)
            callback(result)
        }
    }

    // fetch current UV index
    private inner class FetchUVIndexTask(private val city: String, private val callback: (Double?) -> Unit) : AsyncTask<Void, Void, Double?>() {
        override fun doInBackground(vararg params: Void?): Double? {
            var uvIndex: Double? = null

            try {
                val url = URL("https://api.openweathermap.org/data/2.5/uvi?lat=${getLat(city)}&lon=${getLon(city)}&appid=$API_KEY")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"

                val bufferedReader = BufferedReader(InputStreamReader(connection.inputStream))
                val response = bufferedReader.use(BufferedReader::readText)

                val jsonObject = JSONObject(response)
                uvIndex = jsonObject.optDouble("value")
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return uvIndex
        }

        override fun onPostExecute(result: Double?) {
            super.onPostExecute(result)
            callback(result)
        }

        // Get latitude of the city
        private fun getLat(city: String): Double {
            return try {
                val url = URL("https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$API_KEY")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"

                val bufferedReader = BufferedReader(InputStreamReader(connection.inputStream))
                val response = bufferedReader.use(BufferedReader::readText)

                val jsonObject = JSONObject(response)
                val coord = jsonObject.optJSONObject("coord")
                coord?.optDouble("lat") ?: 0.0
            } catch (e: Exception) {
                e.printStackTrace()
                0.0
            }
        }

        // Get longitude of the city
        private fun getLon(city: String): Double {
            return try {
                val url = URL("https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$API_KEY")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"

                val bufferedReader = BufferedReader(InputStreamReader(connection.inputStream))
                val response = bufferedReader.use(BufferedReader::readText)

                val jsonObject = JSONObject(response)
                val coord = jsonObject.optJSONObject("coord")
                coord?.optDouble("lon") ?: 0.0
            } catch (e: Exception) {
                e.printStackTrace()
                0.0
            }
        }
    }


    // Round double to specified decimal places
    private fun Double.roundTo(decimals: Int): Double {
        return "%.${decimals}f".format(this).toDouble()
    }

    // convert kelvin to fahrenheit
    private fun kelvinToFahrenheit(kelvin: Double): Double {
        val temp = (kelvin - 273.15) * 9 / 5 + 32
        return (temp * 10.0).roundToInt() / 10.0
    }
}

