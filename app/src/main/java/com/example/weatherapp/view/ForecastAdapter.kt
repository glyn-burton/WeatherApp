package com.example.weatherapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.model.Forecast.X
import kotlinx.android.synthetic.main.weather_item.view.*

class ForecastAdapter (var weatherList : List<X>)
    : RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.weather_item, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.populatePersonItem(weatherList[position])

    override fun getItemCount() = weatherList.size

    fun updateList() {

        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun populatePersonItem(weather : X) {
            var hours = weather.dt_txt.split(" ")
            itemView.tvHour.text = hours[0] + "\n" + hours[1]
            itemView.tvWeatherDecript.text = weather.weather[0].description
            itemView.tvHighLow.text = weather.main.temp_max.toInt().toString() + " / "+weather.main.temp_min.toInt().toString()
            when(weather.weather[0].main){

                "Clear" -> {
                    itemView.imWeatherPic.setImageResource(R.drawable.sunny)
                }

                "Rain" -> {
                    itemView.imWeatherPic.setImageResource(R.drawable.rain)
                }

                "Snow" -> {
                    itemView.imWeatherPic.setImageResource(R.drawable.snow)
                }

                "Fog" -> {
                    itemView.imWeatherPic.setImageResource(R.drawable.fog)
                }

                "Drizzle" -> {
                    itemView.imWeatherPic.setImageResource(R.drawable.partlycloudy)
                }

                "Clouds" -> {
                    itemView.imWeatherPic.setImageResource(R.drawable.cloudy)
                }
                "Thunderstorm" -> {
                    itemView.imWeatherPic.setImageResource(R.drawable.storm)

                }




            }
        }
    }

}