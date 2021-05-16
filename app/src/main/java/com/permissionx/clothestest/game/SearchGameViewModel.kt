package com.permissionx.clothestest.game

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.permissionx.clothestest.network.Repository

class SearchGameViewModel:ViewModel() {

    private val requestBodyLiveData= MutableLiveData<String>()

    val responseBodyLiveData= Transformations.switchMap(requestBodyLiveData){ requestBodyLiveData->
        Repository.getGame(requestBodyLiveData)

    }

    fun getGame(name: String){
        requestBodyLiveData.value=name
    }
}