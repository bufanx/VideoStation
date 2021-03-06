package com.permissionx.clothestest.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.permissionx.clothestest.R
import com.permissionx.clothestest.login.Login
import com.permissionx.clothestest.login.LoginViewModel
import com.permissionx.clothestest.network.RegisterRequest
import com.permissionx.clothestest.network.RegisterResponse
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_register.*

class Register : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProvider(this).get(RegisterViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        setSupportActionBar(toolBar)
        //监听register
        register.setOnClickListener {
            val userName=register_user_name.text.toString()
            val firstPwd=register_first_pwd.text.toString()
            val secondPwd=register_second_pwd.text.toString()
            if (userName.isNotEmpty()&&firstPwd.isNotEmpty()&&secondPwd.isNotEmpty()){
                if (firstPwd!=secondPwd){
                    Toast.makeText(this,"第一次密码与第二次密码不一样,请重新输入密码",Toast.LENGTH_SHORT).show()
                }else{
                    val requestBody=RegisterRequest(userName,firstPwd)
                    viewModel.register(requestBody)
                    register_pgb.visibility=View.VISIBLE
                }
            }else{
                Toast.makeText(this,"未输入用户名或密码",Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.responseBodyLiveData.observe(this,{ result->
            val response=result.getOrNull() as RegisterResponse
            if (response!=null){
                if (response.code==201){
                    Toast.makeText(this,"该账号已被注册,请换一个账号",Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this,"注册成功!",Toast.LENGTH_SHORT).show()
                    val intent=Intent(this,Login::class.java)
                    intent.putExtra("user_name",register_user_name.text.toString())
                    intent.putExtra("pwd",register_first_pwd.text.toString())
                    startActivity(intent)
                }
            }else{
                Toast.makeText(this,"网络连接超时",Toast.LENGTH_SHORT).show()
            }
            register_pgb.visibility=View.GONE
        })
    }

}