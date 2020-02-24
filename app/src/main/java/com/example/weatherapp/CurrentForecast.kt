package com.example.weatherapp

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.datasource.remote.OkHttpHelper
import com.example.weatherapp.model.Forecast.MyForecast
import com.example.weatherapp.model.Forecast.Weather
import com.example.weatherapp.view.ForecastAdapter
import kotlinx.android.synthetic.main.fragment_current_forecast.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [CurrentForecast.OnFragmentInteractionListener] interface
 * to handle interaction events.
 */
class CurrentForecast : Fragment() {
    private var zipCode: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val preferences = this.activity!!.getSharedPreferences("shared_pref", Context.MODE_PRIVATE)
        val currentValue = preferences.getString(ZIP_CODE, "")
        zipCode = currentValue
        val forecastApi =  "https://api.openweathermap.org/data/2.5/forecast?zip=$zipCode,us&appid=$API_KEY"
        val okHttpHelper = OkHttpHelper()
        okHttpHelper.makeCurrentForecastAPI(forecastApi)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_current_forecast, container, false)

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onUserResponse(userResponse : MyForecast){

        rvForecast.layoutManager = LinearLayoutManager(this.context,RecyclerView.HORIZONTAL,false)
        val itemDecoration = DividerItemDecoration(this.context,DividerItemDecoration.HORIZONTAL)
        rvForecast.addItemDecoration(itemDecoration)
        rvForecast.adapter = ForecastAdapter(userResponse.list)
        (rvForecast.adapter as ForecastAdapter).updateList()

    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }







    override fun onDetach() {
        super.onDetach()

    }



    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
            CurrentForecast().apply {
                arguments = Bundle().apply {
                    putString(ZIP_CODE, param1)

                }
            }
    }

}
