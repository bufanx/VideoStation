package com.permissionx.clothestest.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("login/")
    fun askLogin(@Body loginRequest:LoginRequest): Call<LoginResponse>
}