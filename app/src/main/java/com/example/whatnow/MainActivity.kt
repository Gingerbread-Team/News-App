package com.example.whatnow

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.whatnow.databinding.ActivityMainBinding

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    //https://newsapi.org
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize FirebaseAuth
        auth = Firebase.auth




        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadNews()

        binding.swipeRefresh.setOnRefreshListener {
            loadNews()
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun loadNews() {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC)

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://newsapi.org/")
            .client(client) // Fix applied here
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val newsCallable = retrofit.create(NewsCallable::class.java)

        newsCallable.getNews().enqueue(object : Callback<News> {
            override fun onResponse(p0: Call<News>, response: Response<News>) {
                val news = response.body()
                val articles = news?.articles
                // Log.d("trace", "articles : $articles")
                if (articles != null) {
                    showNews(articles)
                }
                binding.progress.isVisible = false
            }

            override fun onFailure(p0: Call<News>, p1: Throwable) {
                Log.d("trace", "Error: ${p1.message}") // More useful error logging
                binding.progress.isVisible = false
            }
        })
    }

    private fun showNews(articles: List<Article>) {
        val adapter = NewsAdapter(this, ArrayList(articles))
        binding.newsList.adapter = adapter
    }
}
