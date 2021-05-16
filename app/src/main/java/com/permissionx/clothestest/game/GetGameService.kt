package com.permissionx.clothestest.game

import com.permissionx.clothestest.game.GameSearchResponse
import com.permissionx.clothestest.videoplay.GetUrlResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GetGameService {

    @GET("game")
    fun getGame(@Query("name") name:String): Call<GameSearchResponse>
}