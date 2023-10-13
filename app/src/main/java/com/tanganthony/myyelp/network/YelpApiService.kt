package com.tanganthony.myyelp.network

import com.tanganthony.myyelp.API_BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface YelpApiService {

    @GET("businesses/search")
    suspend fun getRestaurants(
        @Header("Authorization") authHeader: String,
        @Query("term") searchTerm: String,
        @Query("location") location: String
    ) : YelpData

    companion object {
        private var apiService: YelpApiService? = null

        fun getInstance() : YelpApiService {
            if(apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(YelpApiService::class.java)
            }
            return apiService!!
        }
    }
}