package com.permissionx.clothestest.videoplay

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GetVideoNumService {
    @GET("video/v1/get_num")
    fun getVideoNum(@Query("video_id") videoId:Int):Call<GetUrlResponse>
}