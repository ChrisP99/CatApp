package com.chris.catgif

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface CatApiInterface {
    @Headers("x-api-key: 1e6ddf72-b00f-4df2-93a3-8731b901130c")
    @GET("breeds")
    fun getAllBreeds(): Call<List<Cat>>
}