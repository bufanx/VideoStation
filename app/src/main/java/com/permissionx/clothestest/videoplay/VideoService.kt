package com.permissionx.clothestest.videoplay

import com.permissionx.clothestest.videoplay.SearchVideoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface VideoService {

    @GET("video/v2/search")
    fun searchVideos(@Query("name") query: String):Call<SearchVideoResponse>
}