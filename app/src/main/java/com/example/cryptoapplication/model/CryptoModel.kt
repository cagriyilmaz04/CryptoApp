package com.example.cryptoapplication.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class CryptoModel(
    @SerializedName("currency")
       val currency:String,
    @SerializedName("price")
       val price:Double

)