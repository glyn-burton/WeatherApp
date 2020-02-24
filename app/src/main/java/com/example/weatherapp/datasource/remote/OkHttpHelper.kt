package com.example.weatherapp.datasource.remote

import android.util.Log
import com.example.weatherapp.model.Forecast.MyForecast
import com.example.weatherapp.model.Weather.MyWeather
import com.google.gson.Gson
import okhttp3.*
import org.greenrobot.eventbus.EventBus
import java.io.IOException

class OkHttpHelper {

    private fun getClient(): OkHttpClient{
        val okHttpClient = OkHttpClient.Builder().build()
        return okHttpClient

    }

    fun makeCurrentWeatherAPI(url:String){

        val request = Request.Builder().url(url).build()
        getClient().run {
            newCall(request).enqueue(object: Callback{

                override fun onResponse(call: Call, response: Response) {
                    val json = response.body?.string()
                    Log.d("TAG", json)
                    val userResults = Gson().fromJson<MyWeather>(json,MyWeather::class.java)
                    Log.d("TAG", userResults.toString())
                    EventBus.getDefault().post(userResults)
                }

                override fun onFailure(call: Call, e: IOException) {
                    Log.e("ERROR TAG","Error in makeAsyncAPICall ----->",e)

                }




            })

        }
    }

    fun makeCurrentForecastAPI(url:String){
        val request = Request.Builder().url(url).build()
        getClient().run {
            newCall(request).enqueue(object: Callback{

                override fun onResponse(call: Call, response: Response) {
                    val json = response.body?.string()
                    val userResults = Gson().fromJson<MyForecast>(json,MyForecast::class.java)
                    EventBus.getDefault().post(userResults)
                }

                override fun onFailure(call: Call, e: IOException) {
                    Log.e("ERROR TAG","Error in makeAsyncAPICall ----->",e)

                }




            })

        }


    }


}