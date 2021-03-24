package com.permissionx.clothestest.videoplay

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.permissionx.clothestest.Repository

class RefreshVideoViewModel:ViewModel() {
    private val requestLiveData= MutableLiveData<Int>()

    val responseBodyLiveData= Transformations.switchMap(requestLiveData){ requestLiveData->
        Log.d("调用ViewModel","refreshVideo")
        Repository.refreshVideo(requestLiveData)
    }

    fun refreshVideo(videoId:Int){
        Log.d("调用仓库层","refreshVideo")
        requestLiveData.value=videoId
    }
}