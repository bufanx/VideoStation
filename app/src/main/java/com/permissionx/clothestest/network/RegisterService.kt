package com.permissionx.clothestest.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterService {
    @POST("register/")
    fun askRegister(@Body loginRequest:RegisterRequest): Call<RegisterResponse>
}