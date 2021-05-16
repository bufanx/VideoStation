package com.permissionx.clothestest.videoplay

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.permissionx.clothestest.network.Repository

class GetVideoUrlViewModel:ViewModel() {

    private val getUrlLiveData= MutableLiveData<GetUrlRequest>()

    val responseBodyLiveData= Transformations.switchMap(getUrlLiveData){ getUrlLiveData->
        Log.d("调用ViewModel","searchVideo")
        Repository.getVideoUrl(getUrlLiveData.video_id,getUrlLiveData.item_id)
    }

    fun getVideoUrl(query: GetUrlRequest){
        Log.d("调用仓库层","searchVideo")
        getUrlLiveData.value=query
    }
}