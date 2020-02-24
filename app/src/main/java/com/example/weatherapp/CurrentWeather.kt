package com.example.weatherapp

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weatherapp.datasource.remote.OkHttpHelper
import com.example.weatherapp.model.Forecast.Weather
import com.example.weatherapp.model.Weather.MyWeather
import kotlinx.android.synthetic.main.fragment_current_weather.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import kotlin.concurrent.thread
import android.content.SharedPreferences
import android.util.Log


private const val ARG_PARAM1 = "param1"



class CurrentWeather : Fragment() {
    // TODO: Rename and change types of parameters
    private var zipCode: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private var cel = true
    private var kelvinTemp = 273.15

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val preferences = this.activity!!.getSharedPreferences("shared_pref", Context.MODE_PRIVATE)
            val currentValue = preferences.getString(ZIP_CODE, "")
            zipCode = currentValue
        }
        Log.d("TAG",zipCode.toString())
        val weatherApi =  "https://api.openweathermap.org/data/2.5/weather?zip=$zipCode,us&appid=$API_KEY"
        val okHttpHelper = OkHttpHelper()
        okHttpHelper.makeCurrentWeatherAPI(weatherApi)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onUserResponse(userResponse : MyWeather){
        tvCityName.text = userResponse.name
        tvDegrees.text = "${userResponse.main.temp}"
        tvCurrentTime.text = "${userResponse.main.feels_like}"

        if (cel){
            var oldTemp = "${userResponse.main.temp}"
            var oldFeel = "${userResponse.main.feels_like}"
            tvDegrees.text = (oldTemp.toFloat() - kelvinTemp).toInt().toString()+" C"
            tvCurrentTime.text = "Real-feel"+(oldFeel.toFloat() - kelvinTemp).toInt().toString()+" C"
        }
        else{
            var oldTemp = "${userResponse.main.temp}"
            tvDegrees.text = (1.8*(oldTemp.toFloat() - 273)+32).toInt().toString()+"F"
        }
        when(userResponse.weather[0].main){

            "Clear" -> {
                imWeatherIcon.setImageResource(R.drawable.sunny)
            }

            "Rain" -> {
                imWeatherIcon.setImageResource(R.drawable.rain)
            }

            "Snow" -> {
                imWeatherIcon.setImageResource(R.drawable.snow)
            }

            "Fog" -> {
                imWeatherIcon.setImageResource(R.drawable.fog)
            }

            "Drizzle" -> {
                imWeatherIcon.setImageResource(R.drawable.partlycloudy)
            }

            "Clouds" -> {
                imWeatherIcon.setImageResource(R.drawable.cloudy)
            }
            "Thunderstorm" -> {
                imWeatherIcon.setImageResource(R.drawable.storm)

            }




        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_current_weather, container, false)
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }





    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(string:String)
    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
            CurrentWeather().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)

                }
            }
    }
}
