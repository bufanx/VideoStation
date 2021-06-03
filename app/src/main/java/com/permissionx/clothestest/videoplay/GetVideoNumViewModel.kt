package com.permissionx.clothestest.videoplay

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.permissionx.clothestest.network.Repository

class GetVideoNumViewModel:ViewModel() {
    private val requestBody = MutableLiveData<Int>()

    val responseBodyLiveData= Transformations.switchMap(requestBody){ requestBody->
        Log.d("调用ViewModel","searchVideoNum")
        Repository.getVideoNum(requestBody)
    }
    fun getVideoNum(videoId: Int){
        Log.d("调用仓库层","searchVideo")
        requestBody.value = videoId
    }
}