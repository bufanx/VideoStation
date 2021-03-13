package com.permissionx.clothestest.register

import com.google.gson.annotations.SerializedName

data class RegisterResponse(val code:Int,@SerializedName("error_msg") val msg:String,val time:Int)
