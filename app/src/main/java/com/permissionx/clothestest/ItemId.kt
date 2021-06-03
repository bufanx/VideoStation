package com.permissionx.clothestest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object ItemId {
    var itemId: Int=0
    var _videoId = MutableLiveData<Int>()
    var videoId:LiveData<Int> = _videoId
    var picUrl:String = ""
    var videoDescription:String = ""
    var videoNum:Int=0
}