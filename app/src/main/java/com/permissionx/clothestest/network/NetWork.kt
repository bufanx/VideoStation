package com.permissionx.clothestest.network

import android.util.Log
import com.permissionx.clothestest.game.GetGameService
import com.permissionx.clothestest.login.LoginRequest
import com.permissionx.clothestest.login.LoginService
import com.permissionx.clothestest.music.MusicService
import com.permissionx.clothestest.register.RegisterRequest
import com.permissionx.clothestest.register.RegisterService
import com.permissionx.clothestest.update.GetAppVersionService
import com.permissionx.clothestest.videoplay.GetVideoNumService
import com.permissionx.clothestest.videoplay.GetVideoUrlService
import com.permissionx.clothestest.videoplay.RefreshVideoService
import com.permissionx.clothestest.videoplay.VideoService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object NetWork {
    //登录
    private val loginService=ServiceCreator.create(LoginService::class.java)
    suspend fun askLogin(loginRequest: LoginRequest)= loginService.askLogin(loginRequest).await()

    //注册
    private val registerService=ServiceCreator.create(RegisterService::class.java)
    suspend fun askRegister(registerRequest: RegisterRequest)= registerService.askRegister(registerRequest).await()

    //搜索视频
    private val searchVideoService=ServiceCreator.create(VideoService::class.java)
    suspend fun searchVideo(query: String)= searchVideoService.searchVideos(query).await()

    //获得视频url
    private val getVideoUrlService=ServiceCreator.create(GetVideoUrlService::class.java)
    suspend fun getVideoUrl(video_id:Int,item_id:Int)= getVideoUrlService.getVideoUrl(video_id, item_id).await()

    //搜索游戏
    private val getGameService=GameServiceCreator.create(GetGameService::class.java)
    suspend fun getGame(name:String)= getGameService.getGame(name).await()

    //刷新视频
    private val refreshVideoService=ServiceCreator.create(RefreshVideoService::class.java)
    suspend fun refreshVideo(video_id: Int) = refreshVideoService.refreshVideo(video_id).await()

    //获得最新APP版本号
    private val getAppVersionService = UpdateServiceCreator.create(GetAppVersionService::class.java)
    suspend fun getAppVersion() = getAppVersionService.getAppVersion().await()

    //获取音乐信息
    private val getMusicService = ServiceCreator.create(MusicService::class.java)
    suspend fun getMusic(query: String) = getMusicService.getMusic(query).await()

    //获取集数
    private val getVideoNumService = ServiceCreator.create(GetVideoNumService::class.java)
    suspend fun getVideoNum(videoId:Int) = getVideoNumService.getVideoNum(videoId).await()

    private suspend fun <T> Call<T>.await():T{
        //Log.d("执行挂起函数","挂起")
        return suspendCoroutine {continuation ->
            //Log.d("返回数据前","调用回调函数")
            enqueue(object: Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    Log.d("返回数据前","调用回调函数")
                    val body=response.body()
                    Log.d("返回数据", body.toString())
                    if (body!=null)continuation.resume(body)
                    else continuation.resumeWithException(
                            RuntimeException("response body is null"))
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }

}