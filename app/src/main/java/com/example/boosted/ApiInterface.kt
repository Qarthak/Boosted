package com.example.boosted

import retrofit2.Call
import retrofit2.http.GET

//Data class matches the structure of the JSON object we're getting from the API endpoint
data class ApiItem(
    val body: String,
    val id: Int,
    val title: String,
    val userId: Int
)

interface ApiInterface {
    @GET(value = BASE_URL)
    fun getData(): Call<List<ApiItem>>
}