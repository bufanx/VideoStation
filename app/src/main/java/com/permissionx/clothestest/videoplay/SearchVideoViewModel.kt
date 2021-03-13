package com.permissionx.clothestest.videoplay

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.permissionx.clothestest.Repository

class SearchVideoViewModel:ViewModel() {
    private val requestBodyLiveData= MutableLiveData<String>()

    val responseBodyLiveData= Transformations.switchMap(requestBodyLiveData){ requestBodyLiveData->
        Log.d("调用ViewModel","searchVideo")
        Repository.searchVideo(requestBodyLiveData)
    }

    fun searchVideo(query: String){
        Log.d("调用仓库层","searchVideo")
        requestBodyLiveData.value=query
    }
}