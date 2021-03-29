package com.permissionx.clothestest

import android.util.Log
import androidx.lifecycle.liveData
import com.permissionx.clothestest.game.GameSearchResponse
import com.permissionx.clothestest.login.LoginRequest
import com.permissionx.clothestest.login.LoginResponse
import com.permissionx.clothestest.network.NetWork
import com.permissionx.clothestest.register.RegisterRequest
import com.permissionx.clothestest.register.RegisterResponse
import com.permissionx.clothestest.update.GithubRelease
import com.permissionx.clothestest.videoplay.GetUrlResponse
import com.permissionx.clothestest.videoplay.RefreshVideoResponse
import com.permissionx.clothestest.videoplay.SearchVideoResponse
import kotlinx.coroutines.Dispatchers
import kotlin.RuntimeException

object Repository {
    //登录
    fun askLogin(loginRequest: LoginRequest)= liveData(Dispatchers.IO) {
        val result=try {
            Log.d("仓库层","调用askLogin")
            val loginResponse=NetWork.askLogin(loginRequest)
            Log.d("仓库层","调用askLogin成功")
            if(loginResponse.code==200){
                Result.success(loginResponse)
            }else{
                Result.failure(RuntimeException("网络超时"))
            }
        }catch (e:Exception){
            Log.d("错误信息E：",e.toString())
            Result.failure<LoginResponse>(e)
        }
        emit(result)
    }

    //注册
    fun askRegister(registerRequest: RegisterRequest)= liveData(Dispatchers.IO){
        val result=try {
            Log.d("仓库层","调用askRegister")
            val registerResponse=NetWork.askRegister(registerRequest)
            Log.d("仓库层","调用askRegister成功")
            if(registerResponse.code==200){
                Result.success(registerResponse)
            }else{
                Result.failure(RuntimeException("网络超时"))
            }
        }catch (e:Exception){
            Log.d("错误信息E：",e.toString())
            Result.failure<RegisterResponse>(e)
        }
        emit(result)
    }

    //搜索视频
    fun searchVideo(query:String)= liveData(Dispatchers.IO) {
        val result=try {
            Log.d("仓库层","调用searchVideo")
            val videoResponse=NetWork.searchVideo(query)
            Log.d("仓库层","调用searchVideo成功")
            if(videoResponse.code==200){
                Result.success(videoResponse)
            }else{
                Result.failure(RuntimeException("网络超时"))
            }
        }catch (e:Exception){
            Log.d("错误信息E：",e.toString())
            Result.failure<SearchVideoResponse>(e)
        }
        emit(result)
    }

    //获得视频url
    fun getVideoUrl(video_id:Int,item_id:Int)= liveData(Dispatchers.IO){
        val result=try {
            Log.d("仓库层","调用getVideoUrl")
            val videoUrlResponse=NetWork.getVideoUrl(video_id, item_id)
            Log.d("仓库层","调用getVideoUrl成功")
            if(videoUrlResponse.code==200){
                Result.success(videoUrlResponse)
            }else{
                Result.failure(RuntimeException("网络超时"))
            }
        }catch (e:Exception){
            Log.d("错误信息E：",e.toString())
            Result.failure<GetUrlResponse>(e)
        }
        emit(result)
    }

    //搜索游戏
    fun getGame(name:String)= liveData(Dispatchers.IO) {
        val result=try {
            Log.d("仓库层","调用getGame")
            val gameResponse=NetWork.getGame(name)
            Log.d("仓库层","调用getGame成功")
            if (gameResponse.code==200){
                Result.success(gameResponse)
            }else{
                Result.failure(RuntimeException("网络超时"))
            }
        }catch (e:Exception){
            Log.d("错误信息e:",e.toString())
            Result.failure<GameSearchResponse>(e)
        }
        emit(result)
    }

    //刷新视频
    fun refreshVideo(video_id: Int)= liveData(Dispatchers.IO){
        val result = try {
            Log.d("仓库层","调用refreshVideo")
            val refreshVideoResponse=NetWork.refreshVideo(video_id)
            Log.d("仓库层","调用refreshVideo成功")
            if (refreshVideoResponse.code==200){
                Result.success(refreshVideoResponse)
            }else{
                Result.failure(RuntimeException("网络超时"))
            }
        }catch (e:Exception){
            Log.d("错误信息e:",e.toString())
            Result.failure<RefreshVideoResponse>(e)
        }
        emit(result)
    }

    //获取App最新版本号
    fun getAppVersion()= liveData(Dispatchers.IO) {
            val result = try {
                val appVersionResponse = NetWork.getAppVersion()
                if (appVersionResponse.author.login=="bufanx"){
                    Result.success(appVersionResponse)
                }else {
                    Result.failure(RuntimeException("网络超时"))
                }
        }catch (e:Exception){
            Result.failure<GithubRelease>(e)
        }
            emit(result)
        }
}