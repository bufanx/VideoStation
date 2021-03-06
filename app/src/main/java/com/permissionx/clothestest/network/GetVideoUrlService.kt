package com.permissionx.clothestest.network

import com.permissionx.clothestest.videoplay.SearchVideoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GetVideoUrlService {

    @GET("video/play_one")
    fun getVideoUrl(@Query("video_id") video_id: Int, @Query("item_id") item_id:Int): Call<GetUrlResponse>
}