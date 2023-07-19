package com.example.myapplication
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class WeatherActivity : AppCompatActivity() {
    private var url = "api.openweathermap.org/data/2.5/weather?q={city name}&appid={your api key}"
    private var apikey = "fcd2c236893559071f53147e2c72132f"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        val et = findViewById<EditText>(R.id.et)
        val tv = findViewById<TextView>(R.id.tv)
        val btn = findViewById<Button>(R.id.btnWeather)
        val humidityTv = findViewById<TextView>(R.id.humidity)
        val tempMaxTv = findViewById<TextView>(R.id.temp_max)
        val tempMinTv = findViewById<TextView>(R.id.temp_min)
        val pressureTv = findViewById<TextView>(R.id.pressure)
        val windDegTv = findViewById<TextView>(R.id.wind)
        val weatherStatusTv = findViewById<TextView>(R.id.status)
        val sunsetTv = findViewById<TextView>(R.id.sunset)
        val sunriseTv = findViewById<TextView>(R.id.sunrise)
        val weatherImg = findViewById<ImageView>(R.id.imageView2)

        val city = intent.getStringExtra(MainActivity.KEY)

        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getweather(city.toString().trim(),apikey)

        retrofitData.enqueue(object : Callback<MyData?> {
            override fun onResponse(call: Call<MyData?>, response: Response<MyData?>) {
                if (response.code() == 404) {
                    Toast.makeText(this@WeatherActivity, "Please Enter a valid City", Toast.LENGTH_LONG).show()
                    return
                }
                else if (!response.isSuccessful) {
                    Toast.makeText(this@WeatherActivity, response.code().toString(), Toast.LENGTH_LONG).show()
                    return
                }
                val responseBody = response.body()
                val mainProperties = responseBody?.main!!
                val weatherProperties = responseBody.weather
                val windProperties = responseBody.wind
                val systemProperties = responseBody.sys

                val temp: Double = mainProperties.temp
                val temperature = (temp-273.15).toInt()
                tv.text = temperature.toString()+"°C"

                val humidity: Int = mainProperties.humidity
                humidityTv.text = humidity.toString()+"%"

                val tempMax = mainProperties.temp_max
                val temperatureMax = (tempMax-273.15).toInt()
                tempMaxTv.text = temperatureMax.toString()+"°C"

                val tempMin = mainProperties.temp_min
                val temperatureMin = (tempMin-273.15).toInt()
                tempMinTv.text = temperatureMin.toString()+"°C"

                val pressure = mainProperties.pressure
                pressureTv.text = pressure.toString()+" Pa"

                val windDeg = windProperties.deg
                windDegTv.text = windDeg.toString()+"°"

                val collectDataInSB = StringBuilder()

                for(myWeatherData in weatherProperties){
                    collectDataInSB.append(myWeatherData.description)
                    Picasso.get().load("https://openweathermap.org/img/wn/${myWeatherData.icon}@2x.png").into(weatherImg)
                    weatherImg.alpha = 1f

                }
                weatherStatusTv.text = collectDataInSB

                val sunset = systemProperties.sunset.toLong()
                sunsetTv.text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunset*1000))

                val sunrise = systemProperties.sunrise.toLong()
                sunriseTv.text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunrise*1000))
            }

            override fun onFailure(call: Call<MyData?>, t: Throwable) {
                Toast.makeText(this@WeatherActivity, t.message.toString(), Toast.LENGTH_LONG).show()
            }
        })

        btn.setOnClickListener{
            val retrofitBuilder = Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiInterface::class.java)

            val retrofitData = retrofitBuilder.getweather(et.text.toString().trim(),apikey)

            retrofitData.enqueue(object : Callback<MyData?> {
                override fun onResponse(call: Call<MyData?>, response: Response<MyData?>) {
                    if (response.code() == 404) {
                        Toast.makeText(this@WeatherActivity, "Please Enter a valid City", Toast.LENGTH_LONG).show()
                        return
                    }
                    else if (!response.isSuccessful) {
                        Toast.makeText(this@WeatherActivity, response.code().toString(), Toast.LENGTH_LONG).show()
                        return
                    }
                    val responseBody = response.body()
                    val mainProperties = responseBody?.main!!
                    val weatherProperties = responseBody.weather
                    val windProperties = responseBody.wind
                    val systemProperties = responseBody.sys

                    val temp: Double = mainProperties.temp
                    val temperature = (temp-273.15).toInt()
                    tv.text = temperature.toString()+"°C"

                    val humidity: Int = mainProperties.humidity
                    humidityTv.text = humidity.toString()+"%"

                    val tempMax = mainProperties.temp_max
                    val temperatureMax = (tempMax-273.15).toInt()
                    tempMaxTv.text = temperatureMax.toString()+"°C"

                    val tempMin = mainProperties.temp_min
                    val temperatureMin = (tempMin-273.15).toInt()
                    tempMinTv.text = temperatureMin.toString()+"°C"

                    val pressure = mainProperties.pressure
                    pressureTv.text = pressure.toString()+" Pa"

                    val windDeg = windProperties.deg
                    windDegTv.text = windDeg.toString()+"°"

                    val collectDataInSB = StringBuilder()

                    for(myWeatherData in weatherProperties){
                        collectDataInSB.append(myWeatherData.description)
                        Picasso.get().load("https://openweathermap.org/img/wn/${myWeatherData.icon}@2x.png").into(weatherImg)
                        weatherImg.alpha = 1f

                    }
                    weatherStatusTv.text = collectDataInSB

                    val sunset = systemProperties.sunset.toLong()
                    sunsetTv.text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunset*1000))

                    val sunrise = systemProperties.sunrise.toLong()
                    sunriseTv.text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunrise*1000))
                }

                override fun onFailure(call: Call<MyData?>, t: Throwable) {
                    Toast.makeText(this@WeatherActivity, t.message.toString(), Toast.LENGTH_LONG).show()
                }
            })

        }

    }
}