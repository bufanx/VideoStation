package com.permissionx.clothestest.login

import com.google.gson.annotations.SerializedName

data class LoginResponse(val code:Int,@SerializedName("error_msg") val msg:String)
