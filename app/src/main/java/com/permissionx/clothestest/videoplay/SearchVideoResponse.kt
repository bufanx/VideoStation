package com.permissionx.clothestest.videoplay

import android.telephony.MbmsGroupCallSession
import androidx.lifecycle.MutableLiveData

data class SearchVideoResponse(val code:Int,
                               val data: ArrayList<VideoItem>,
                               val msg:String)