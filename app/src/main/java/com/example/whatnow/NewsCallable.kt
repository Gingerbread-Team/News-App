package com.example.whatnow


import retrofit2.Call
import retrofit2.http.GET

interface NewsCallable {
    @GET("v2/top-headlines?country=us&category=general&apiKey=5582c75d3bfa4ff39029f84b9fac7b89&pageSize=30")
fun getNews(): Call<News>
}