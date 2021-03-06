package com.permissionx.clothestest.register

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.permissionx.clothestest.Repository
import com.permissionx.clothestest.network.RegisterRequest

class RegisterViewModel:ViewModel() {
    private val requestBodyLiveData= MutableLiveData<RegisterRequest>()

    val responseBodyLiveData= Transformations.switchMap(requestBodyLiveData){ requestBodyLiveData->
        Log.d("调用ViewModel","register")
        Repository.askRegister(requestBodyLiveData)
    }

    fun register(registerRequest: RegisterRequest){
        Log.d("调用仓库层","register")
        requestBodyLiveData.value=registerRequest
    }
}