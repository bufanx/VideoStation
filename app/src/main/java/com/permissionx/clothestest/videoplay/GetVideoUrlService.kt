package com.permissionx.clothestest.videoplay

import com.permissionx.clothestest.videoplay.GetUrlResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GetVideoUrlService {

    @GET("video/v1/play_one")
    fun getVideoUrl(@Query("video_id") video_id: Int, @Query("item_id") item_id:Int): Call<GetUrlResponse>
}