package com.example.whatnow

import android.content.Context
import android.content.Intent
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

        val intent = Intent(this, MainActivity::class.java)
        binding.sportsBtn.setOnClickListener {
            intent.putExtra("category", "sports") // passing the category
            startActivity(intent)
        }
        binding.businessBtn.setOnClickListener {
            intent.putExtra("category", "business") // passing the category
            startActivity(intent)
        }
        binding.entertainmentBtn.setOnClickListener {
            intent.putExtra("category", "entertainment") // passing the category
            startActivity(intent)
        }
        //in Main Activity
        //sharedPreferences = getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        //in load news function
        //val selectedCountry = sharedPreferences.getString("selected_country", "us") ?: "us"
        //        Log.d(TAG, "loadNews: Fetching news for country: $selectedCountry")
        //
        //newsCallable.getNews(country = selectedCountry).enqueue(object : Callback<News> {
        //            override fun onResponse(call: Call<News>, response: Response<News>) {
        //                val news = response.body()
        //                val articles = news?.articles


        sharedPreferences = getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)

        // Countries available for selection
        val countries = listOf("United States", "United Kingdom", "Germany", "France", "Egypt")
        val countryCodes = listOf("us", "gb", "de", "fr", "eg")

//        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, countries)
//        binding.countrySpinner.adapter = adapter
//
//        // Load saved country selection
//        val savedCountryCode = sharedPreferences.getString("selected_country", "us")
//        val savedIndex = countryCodes.indexOf(savedCountryCode)
//        if (savedIndex != -1) {
//            binding.countrySpinner.setSelection(savedIndex)
//        }
//
//        binding.saveButton.setOnClickListener {
//            val selectedPosition = binding.countrySpinner.selectedItemPosition
//            val selectedCountryCode = countryCodes[selectedPosition]
//
//            // Save selection to SharedPreferences
//            sharedPreferences.edit().putString("selected_country", selectedCountryCode).apply()
//
//            Toast.makeText(this, "Country saved: ${countries[selectedPosition]}", Toast.LENGTH_SHORT).show()
//            finish() // Go back to MainActivity
//        }
    }
}
