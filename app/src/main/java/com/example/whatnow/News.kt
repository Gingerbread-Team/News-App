package com.example.whatnow

data class News(
    val status:String,
    val totalResults:Int,
    val articles:List<Article>
)
data class Article(
    val title : String,
   // @SerializedName("url")
    val url:String,//should be uniform resource locator if we have used Serilized annotation
    val urlToImage:String,
)
