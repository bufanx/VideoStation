package com.permissionx.clothestest.network

import com.permissionx.clothestest.URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UpdateServiceCreator {

    private const val BASE_URL=URL.BASE_URL

    private val retrofit= Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    fun<T> create (serviceClass: Class<T>):T= retrofit.create(serviceClass)

    inline fun <reified T> create():T=create(T::class.java)
}