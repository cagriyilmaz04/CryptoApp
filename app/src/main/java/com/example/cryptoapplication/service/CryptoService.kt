package com.example.cryptoapplication.service

import com.example.cryptoapplication.util.Constant.SECOND_PART
import com.example.cryptoapplication.model.CryptoModel
import retrofit2.Response
import retrofit2.http.GET

interface CryptoService {
    @GET(SECOND_PART)
    suspend fun getData():Response<List<CryptoModel>>


}