package com.permissionx.clothestest.game

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.annotation.RequiresApi
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.permissionx.clothestest.R
import kotlinx.android.synthetic.main.activity_show_game_info.*

class ShowGameInfo : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_game_info)
        //使状态栏和背景融合
        val decorView=window.decorView//拿到当前的Activity的DecorView
        decorView.systemUiVisibility= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE//表示Activity的布局会显示到状态栏上面
        window.statusBarColor= Color.TRANSPARENT//最后调用一下statusBarColor()方法将状态栏设置为透明色
        //隐藏状态栏
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        val dlc=intent.getStringExtra("dlc")
        val download1=intent.getStringExtra("download1")
        val download2=intent.getStringExtra("download2")
        val pwd=intent.getStringExtra("pwd")
        attach_game_thing.text=dlc
        download_url1.text=download1
        download_url2.text=download2
        game_pwd.text=pwd
        attach_game_thing.setOnClickListener {
             val intent=Intent(this,ShowGameWebView::class.java)
             intent.putExtra("url",dlc)
             startActivity(intent)
        }
        download_url1.setOnClickListener {
            val intent=Intent(this,ShowGameWebView::class.java)
            intent.putExtra("url",download1)
            startActivity(intent)
        }
        download_url2.setOnClickListener {
            val intent=Intent(this,ShowGameWebView::class.java)
            intent.putExtra("url",download2)
            startActivity(intent)
        }
    }
}