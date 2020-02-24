package com.example.weatherapp

import android.content.Context
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ZipCodeEntry.OnFragmentInteractionListener, View.OnClickListener,
    CurrentWeather.OnFragmentInteractionListener{



    val entryFragment by lazy{ZipCodeEntry()}
    val weatherFragment by lazy { CurrentWeather() }
    var currentWeather = CurrentWeather()
    var hourlyWeather = CurrentForecast()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (getValueFromSharedPref() == ""){
            supportFragmentManager
                .beginTransaction()
                .add(R.id.frmZipCodeEntry, entryFragment)
                .commit()


        }
        else {
            tvZipCode.text = getValueFromSharedPref()
            val userZip = getValueFromSharedPref()
            currentWeather = CurrentWeather.newInstance(userZip!!)
            supportFragmentManager
                .beginTransaction()
                .add(R.id.frmCurrentWeather,currentWeather)
                .addToBackStack("WEATHER_FRAG")
                .commit()

            hourlyWeather = CurrentForecast.newInstance(userZip!!)
            supportFragmentManager
                .beginTransaction()
                .add(R.id.frmHourlyWeather,hourlyWeather)
                .addToBackStack("HOURLY_FRAG")
                .commit()
        }
        val changeZip = findViewById(R.id.tvZipCode) as TextView
        // set on-click listener
        changeZip.setOnClickListener {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.frmZipCodeEntry, entryFragment)
                .commit()
            val userZip = getValueFromSharedPref()

            supportFragmentManager.popBackStack("WEATHER_FRAG", FragmentManager.POP_BACK_STACK_INCLUSIVE)

            currentWeather = CurrentWeather.newInstance(userZip!!)
            supportFragmentManager
                .beginTransaction()
                .add(R.id.frmCurrentWeather,currentWeather)
                .addToBackStack("WEATHER_FRAG")
                .commit()

            hourlyWeather = CurrentForecast.newInstance(userZip!!)
            supportFragmentManager
                .beginTransaction()
                .add(R.id.frmHourlyWeather,hourlyWeather)
                .addToBackStack("HOURLY_FRAG")
                .commit()
        }
    }

    fun getValueFromSharedPref():String? {
        val sharedPref = getSharedPreferences("shared_pref", Context.MODE_PRIVATE)
        val currentValue = sharedPref.getString(ZIP_CODE, "")
        return currentValue
    }
    override fun onFragmentInteraction(string: String) {
        Toast.makeText(this, "Zip Code set to $string", Toast.LENGTH_LONG).show()
        val sharedPref = getSharedPreferences("shared_pref", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString(ZIP_CODE, string)
        editor.apply()
        tvZipCode.text = string

        supportFragmentManager
            .beginTransaction()
            .remove(entryFragment)
            .commit()

        supportFragmentManager.popBackStack("WEATHER_FRAG", FragmentManager.POP_BACK_STACK_INCLUSIVE)

        val userZip = getValueFromSharedPref()
        currentWeather = CurrentWeather.newInstance(userZip!!)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.frmCurrentWeather,currentWeather)
            .addToBackStack("WEATHER_FRAG")
            .commit()

        hourlyWeather = CurrentForecast.newInstance(userZip!!)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.frmHourlyWeather,hourlyWeather)
            .addToBackStack("HOURLY_FRAG")
            .commit()

    }

    override fun onClick(v: View?) {

    }






}
