package com.permissionx.clothestest.network

import com.permissionx.clothestest.videoplay.SearchVideoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface VideoService {

    @GET("video/search")
    fun searchVideos(@Query("name") query: String):Call<SearchVideoResponse>
}