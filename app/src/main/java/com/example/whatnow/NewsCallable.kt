package com.example.whatnow


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsCallable {
    @GET("v2/top-headlines?pageSize=30")
fun getNews(
    @Query("country") country:String="us",
    @Query("category") category: String="general",
    @Query("apiKey")apiKey:String="5582c75d3bfa4ff39029f84b9fac7b89"
): Call<News>
}