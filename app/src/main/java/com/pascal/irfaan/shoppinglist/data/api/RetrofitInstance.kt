package com.pascal.irfaan.shoppinglist.data.api

import com.pascal.irfaan.shoppinglist.utils.AppConstant
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val retrofit = Retrofit.Builder()
        .baseUrl(AppConstant.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val shoppingApi : ShoppingApi by lazy {
        retrofit.create(ShoppingApi::class.java)
    }
}