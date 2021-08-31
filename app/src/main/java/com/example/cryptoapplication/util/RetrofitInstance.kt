package com.example.cryptoapplication.util

import com.example.cryptoapplication.util.Constant.BASE_URL
import com.example.cryptoapplication.service.CryptoService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
   private val retrofit=Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
       .build()

    val api= retrofit.create(CryptoService::class.java)


}