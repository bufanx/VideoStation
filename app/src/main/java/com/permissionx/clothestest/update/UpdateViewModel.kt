package com.permissionx.clothestest.update

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.permissionx.clothestest.Repository
import com.permissionx.clothestest.register.RegisterRequest

class UpdateViewModel:ViewModel() {

    private val requestBodyLiveData= MutableLiveData<Int>()

    val responseBodyLiveData= Transformations.switchMap(requestBodyLiveData){
        Log.d("调用ViewModel","getAppVersion")
        Repository.getAppVersion()
    }

    fun getAppVersion(registerRequest: Int){
        Log.d("调用仓库层","register")
        requestBodyLiveData.value=registerRequest
    }
}