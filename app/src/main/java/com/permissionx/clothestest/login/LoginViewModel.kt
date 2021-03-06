package com.permissionx.clothestest.login

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import com.permissionx.clothestest.Repository
import com.permissionx.clothestest.network.LoginRequest

class LoginViewModel: ViewModel() {
    val requestBodyLiveData= MutableLiveData<LoginRequest>()

    val responseBodyLiveData= Transformations.switchMap(requestBodyLiveData){ requestBodyLiveData->
        Repository.askLogin(requestBodyLiveData)

    }


    fun askLogin(loginRequest: LoginRequest){
        requestBodyLiveData.value=loginRequest
    }
}