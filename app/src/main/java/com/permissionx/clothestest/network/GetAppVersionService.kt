package com.permissionx.clothestest.network

import com.permissionx.clothestest.update.GithubRelease
import com.permissionx.clothestest.videoplay.RefreshVideoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GetAppVersionService {

    @GET("repos/bufanx/VideoStation/releases/latest")
    fun getAppVersion(): Call<GithubRelease>
}