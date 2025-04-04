package com.example.whatnow

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.whatnow.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //in Main ACtivity
        //sharedPreferences = getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        //in load news function
        //val selectedCountry = sharedPreferences.getString("selected_country", "us") ?: "us"
        //
        //newsCallable.getNews(country = selectedCountry).enqueue(object : Callback<News> {
        //            override fun onResponse(call: Call<News>, response: Response<News>) {
        //                val news = response.body()
        //                val articles = news?.articles


        sharedPreferences = getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)

        // Countries available for selection
        val countries = listOf("United States", "United Kingdom", "Germany", "France", "Egypt")
        val countryCodes = listOf("us", "gb", "de", "fr", "eg")

//
//        // Load saved country selection
//        val savedCountryCode = sharedPreferences.getString("selected_country", "us")
//        val savedIndex = countryCodes.indexOf(savedCountryCode)
//        if (savedIndex != -1) {
//            binding.countrySpinner.setSelection(savedIndex)
//        }
//

    }
}
