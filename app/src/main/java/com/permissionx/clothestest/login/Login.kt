package com.permissionx.clothestest.login

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
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
import com.permissionx.clothestest.URL
import com.permissionx.clothestest.register.Register
import com.permissionx.clothestest.update.UpdateViewModel
import com.permissionx.clothestest.update.Utils
import com.permissionx.clothestest.update.Version
import kotlinx.android.synthetic.main.activity_login.*
import java.lang.RuntimeException
import kotlin.properties.Delegates

class Login : AppCompatActivity() {

    companion object{
        private lateinit var appNowVersionName:String
        private var appNowVersion by Delegates.notNull<Int>()
        private var beginUpdateNumber by Delegates.notNull<Int>()
        private lateinit var appNewVersionName:String
        private lateinit var updateMsg:String
    }
    private val updateViewModel by lazy { ViewModelProvider(this).get(UpdateViewModel::class.java) }
    private val viewModel by lazy { ViewModelProvider(this).get(LoginViewModel::class.java) }
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        beginUpdateNumber= (0..666).random()
        updateViewModel.getAppVersion(beginUpdateNumber)
        appNowVersion= Utils.getVersionCode(this)
        appNowVersionName=Version.versionName
        updateViewModel.responseBodyLiveData.observe(this, Observer { result->
            val response=result.getOrNull()
            if (response!=null){
                appNewVersionName=response.data
                updateMsg=response.msg
                Log.d("version","new:${appNewVersionName}\nold:${Version.versionName}")
                if (appNowVersionName == appNewVersionName) {
                    Toast.makeText(this,"????????????\n???????????????:VideoStation V.${appNowVersionName}",Toast.LENGTH_SHORT).show()
                }else{
                    val temDialog = AlertDialog.Builder(this).setCancelable(false)
                        .setTitle("?????????????????????").setMessage("???????????????????\n????????????:${updateMsg}\n????????????:${appNewVersionName}")
                        .setPositiveButton("??????"){
                                _,_ ->
//                                val request=DownloadManager.Request(Uri.parse(URL.NEW_APP_URL))
//                                request.setTitle("??????")
//                                request.setDescription("Downloading Profile")
//                                request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, "videoStation.apk")
//                                val downloadManager:DownloadManager= this.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
//                                downloadManager.enqueue(request)
                            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(URL.NEW_APP_URL)))
                            //Utils.DownNew()//????????????
                            //Utils.Tos("?????????????????????????????????")

                        }.setNegativeButton("??????"){
                                _,_ ->
                        }.create()
                    temDialog!!.show()
                }
            }else{
                    Toast.makeText(this,"????????????",Toast.LENGTH_SHORT).show()
                    //throw RuntimeException("????????????!")
            }
        })
        setContentView(R.layout.activity_login)
        //setSupportActionBar(findViewById(R.id.toolbar))
        //???????????????????????????
        val decorView=window.decorView//???????????????Activity???DecorView
        decorView.systemUiVisibility= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE//??????Activity????????????????????????????????????
        window.statusBarColor= Color.TRANSPARENT//??????????????????statusBarColor()????????????????????????????????????
        //???????????????
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
            val username=user_name.text.toString()
            val pwd=password.text.toString()
            if (username.isNotEmpty()&&pwd.isNotEmpty()){
                    val requestbody= LoginRequest(username,pwd)
                    viewModel.askLogin(requestbody)
                    login_pgb.visibility= View.VISIBLE
            }else{
                Toast.makeText(this,"???????????????????????????",Toast.LENGTH_SHORT).show()
            }
        }
        //????????????responseBodyLiveData?????????
        viewModel.responseBodyLiveData.observe(this, Observer { result->
            val response=result.getOrNull()
            if (response!=null){
                if (response.code==200){
                    val intent=Intent(this, MainActivity::class.java)
                    intent.putExtra("user_name",user_name.text.toString())
                    intent.putExtra("_pwd",password.text.toString())
                    startActivity(intent)
                }else{
                    //???????????????????????????????????????????????????
                    Toast.makeText(this,response!!.msg,Toast.LENGTH_SHORT).show()
                    showExitDialog01(response!!.msg)
                }
            }else{
                Toast.makeText(this,"???????????????",Toast.LENGTH_SHORT).show()
            }
            Log.d("end","ProgressBar gone")
            login_pgb.visibility=View.GONE
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
            .setTitle("????????????")
            .setMessage(message)
            .setPositiveButton("??????", null)
            .show()
    }
}