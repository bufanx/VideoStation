package com.permissionx.clothestest.music

import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

interface MusicService {
    @POST("music/search")
    fun getMusic(@Query("song_name") query:String):Call<GetMusicResponse>
}