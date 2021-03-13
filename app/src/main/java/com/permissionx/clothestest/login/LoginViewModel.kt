package com.permissionx.clothestest.login

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import com.permissionx.clothestest.Repository

class LoginViewModel: ViewModel() {
    private val requestBodyLiveData= MutableLiveData<LoginRequest>()

    val responseBodyLiveData= Transformations.switchMap(requestBodyLiveData){ requestBodyLiveData->
        Repository.askLogin(requestBodyLiveData)

    }


    fun askLogin(loginRequest: LoginRequest){
        requestBodyLiveData.value=loginRequest
    }
}