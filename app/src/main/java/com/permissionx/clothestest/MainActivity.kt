package com.permissionx.clothestest

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.permissionx.clothestest.adapter.UserCommentAdapter
import com.permissionx.clothestest.databinding.ActivityMainBinding
import com.permissionx.clothestest.forum.UserComment
import com.permissionx.clothestest.videoplay.SearchVideo
import com.permissionx.clothestest.videoplay.VideoPlayWebview
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.nav_header.*
import kotlinx.android.synthetic.main.nav_header.view.*

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("SetTextI18n")
    val userComments= mutableListOf<UserComment>(UserComment("bufanx","aaaaaaaaaaaaaaaaaaaaa",R.drawable.pineapple),
            UserComment("yanlc","bbb",R.drawable.pineapple),
            UserComment("baga","aaaaaaaaaaaaaaaaaaaaacccccccccccccccccccccccccccc",R.drawable.pineapple),
            UserComment("zbdx","aaaaaaaaaaaaaaaaaaaaa",R.drawable.pineapple),
            UserComment("bufanx","aaaaaaaaaaaaaaaaaaaaa",R.drawable.pineapple),
            UserComment("bufanx","aaaaaaaaaaaaaaaaaaaaa",R.drawable.pineapple))
    val userCommentList=ArrayList<UserComment>()
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolBar)
        //使状态栏和背景融合
        val decorView=window.decorView//拿到当前的Activity的DecorView
        decorView.systemUiVisibility= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE//表示Activity的布局会显示到状态栏上面
        window.statusBarColor= Color.TRANSPARENT//最后调用一下statusBarColor()方法将状态栏设置为透明色
        //隐藏状态栏
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_menu)
        }
        val username=intent.getStringExtra("user_name")
        val pwd=intent.getStringExtra("_pwd")
        Log.d("userName","$username")
        runOnUiThread {
             navView.getHeaderView(0).userText.text=username
            navView.getHeaderView(0).mailText.text= "$pwd@qq.com"
        }
        swipeRefresh.setColorSchemeColors(R.color.design_default_color_on_primary)
        swipeRefresh.setOnRefreshListener {
            initUserComment()
            val layoutManager=GridLayoutManager(this,1)
            recyclerView.layoutManager=layoutManager
            val adapter=UserCommentAdapter(this,userCommentList)
            recyclerView.adapter=adapter
            swipeRefresh.isRefreshing=false
        }
    }

    private fun initUserComment(){
        userCommentList.clear()
        repeat(10){
            val index=(0 until userComments.size).random()
            userCommentList.add(userComments[index])
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.backup->{
                val intent=Intent(this,SearchVideo::class.java)
                startActivity(intent)
            }
            R.id.delete->Toast.makeText(this,"...",Toast.LENGTH_SHORT).show()
            R.id.setting->Toast.makeText(this,"...",Toast.LENGTH_SHORT).show()
            android.R.id.home->drawLayout.openDrawer(GravityCompat.START)
        }
        return true
    }
}