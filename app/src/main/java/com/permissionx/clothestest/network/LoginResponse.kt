package com.permissionx.clothestest.network

import com.google.gson.annotations.SerializedName

data class LoginResponse(val code:Int,@SerializedName("error_msg") val msg:String)
