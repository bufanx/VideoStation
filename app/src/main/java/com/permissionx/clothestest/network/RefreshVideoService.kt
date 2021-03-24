package com.permissionx.clothestest.network

import com.permissionx.clothestest.videoplay.GetUrlResponse
import com.permissionx.clothestest.videoplay.RefreshVideoResponse
import com.permissionx.clothestest.videoplay.SearchVideoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RefreshVideoService {

    @GET("video/flash_cache")
    fun refreshVideo(@Query("video_id") videoId:Int):Call<RefreshVideoResponse>
}