package com.permissionx.clothestest.login

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.permissionx.clothestest.MainActivity
import com.permissionx.clothestest.R
import com.permissionx.clothestest.register.Register
import com.permissionx.clothestest.videoplay.VideoPlayWebview
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProvider(this).get(LoginViewModel::class.java) }
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //setSupportActionBar(findViewById(R.id.toolbar))
        //使状态栏和背景融合
        val decorView=window.decorView//拿到当前的Activity的DecorView
        decorView.systemUiVisibility= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE//表示Activity的布局会显示到状态栏上面
        window.statusBarColor= Color.TRANSPARENT//最后调用一下statusBarColor()方法将状态栏设置为透明色
        //隐藏状态栏
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        val userName=intent.getStringExtra("user_name")
        val pwd=intent.getStringExtra("pwd")
        if (userName!=null&&pwd!=null){
            Log.d("registerUserName",userName)
            Log.d("registerPwd",pwd)
           //user_name.setText(userName)
            //password.setText(pwd)
        }
        user_name.addTextChangedListener {
            password.text = null
        }
        login.setOnClickListener {
            user_name.isEnabled=false
            password.isEnabled=false
            login.isEnabled=false
            val username=user_name.text.toString()
            val pwd=password.text.toString()
            if (username.isNotEmpty()&&pwd.isNotEmpty()){
                    val requestbody= LoginRequest(username,pwd)
                    viewModel.askLogin(requestbody)
                    login_pgb.visibility= View.VISIBLE
            }else{
                Toast.makeText(this,"未输入用户名或密码",Toast.LENGTH_SHORT).show()
            }
        }
        //用于监听responseBodyLiveData的变化
        viewModel.responseBodyLiveData.observe(this, Observer { result->
            val response=result.getOrNull()
            if (response!=null){
                if (response.code==200){
                    val intent=Intent(this, MainActivity::class.java)
                    intent.putExtra("user_name",user_name.text.toString())
                    intent.putExtra("_pwd",password.text.toString())
                    startActivity(intent)
                }else{
                    //错误的话就返回后端传回来的错误信息
                    Toast.makeText(this,response!!.msg,Toast.LENGTH_SHORT).show()
                    showExitDialog01(response!!.msg)
                }
            }else{
                Toast.makeText(this,"无网络连接",Toast.LENGTH_SHORT).show()
            }
            Log.d("end","ProgressBar gone")
            login_pgb.visibility=View.GONE
            user_name.isEnabled=true
            password.isEnabled=true
            login.isEnabled=true
        })

        beginResiter.setOnClickListener {
            val intent=Intent(this,Register::class.java)
            startActivity(intent)
        }
        find_pwd.setOnClickListener {
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showExitDialog01(message:String) {
        AlertDialog.Builder(this)
            .setTitle("登陆失败")
            .setMessage(message)
            .setPositiveButton("确定", null)
            .show()
    }
}