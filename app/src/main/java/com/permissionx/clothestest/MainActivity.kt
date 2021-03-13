package com.permissionx.clothestest

import android.annotation.SuppressLint
import android.content.Context
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
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.permissionx.clothestest.adapter.UserCommentAdapter
import com.permissionx.clothestest.databinding.ActivityMainBinding
import com.permissionx.clothestest.forum.UserComment
import com.permissionx.clothestest.game.SearchGame
import com.permissionx.clothestest.videoplay.SearchVideo
import com.permissionx.clothestest.videoplay.VideoPlayWebview
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import com.youth.banner.listener.OnBannerListener
import com.youth.banner.loader.ImageLoader
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.nav_header.*
import kotlinx.android.synthetic.main.nav_header.view.*

class MainActivity : AppCompatActivity() ,OnBannerListener {
    //放图片地址的集合
    //注：在本地也可以，只不过适配器类型为Int，之后在add中直接输入例如R.drawable.dog即可
    private val listPath:ArrayList<String> = ArrayList()
    //放标题的集合
    private val listTitle:ArrayList<String> =ArrayList()

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
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
        //加载轮播效果
        initView()

        //看番
        begin_cartoon.setOnClickListener {
            val intent=Intent(this,SearchVideo::class.java)
            startActivity(intent)
        }

        //下载盗版游戏
        begin_game.setOnClickListener {
            val intent=Intent(this,SearchGame::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.backup->Toast.makeText(this,"...",Toast.LENGTH_SHORT).show()
            R.id.delete->Toast.makeText(this,"...",Toast.LENGTH_SHORT).show()
            R.id.setting->Toast.makeText(this,"...",Toast.LENGTH_SHORT).show()
            android.R.id.home->drawLayout.openDrawer(GravityCompat.START)
        }
        return true
    }
    //实现适配banner的适配器
    private fun initView() {

        listPath.add("https://th.bing.com/th/id/Rc8062fc07c54523752db60f0aa57beb3?rik=hPrzC%2fGePCUxRA&riu=http%3a%2f%2fimg.jingdianju.com%2fuploads%2fmeinv%2f1438639794%2f10.jpg&ehk=vQuqgEs4ILvLbp2oKFQLpZ%2b9MhK75ZqXIHKbe9nFVaM%3d&risl=&pid=ImgRaw")
        listPath.add("https://th.bing.com/th/id/OIP.l9tChLiRIb1nBRl0zIbOrgHaEK?w=314&h=180&c=7&o=5&dpr=1.25&pid=1.7")
        listPath.add("https://th.bing.com/th/id/OIP.Ifa26Vrlc_XH9zMXYdMz4gHaE8?w=283&h=188&c=7&o=5&dpr=1.25&pid=1.7")
        listPath.add("https://th.bing.com/th/id/OIP.XypqbZ0i71yCniZNH2aLSwHaFA?w=192&h=130&c=7&o=5&dpr=1.25&pid=1.7")
        listTitle.add("看番")
        listTitle.add("游戏")
        listTitle.add("听歌")
        listTitle.add("电影")

        val myLoader=GlideImageLoader()
        //设置内置样式，共有六种可以点入方法内逐一体验使用。
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
        //设置图片加载器，图片加载器在下方
        banner.setImageLoader(myLoader)
        //设置图片网址或地址的集合
        banner.setImages(listPath)
        //设置轮播的动画效果，内含多种特效，可点入方法内查找后内逐一体验
        banner.setBannerAnimation(Transformer.Default)
        //设置轮播图的标题集合
        banner.setBannerTitles(listTitle)
        //设置轮播间隔时间
        banner.setDelayTime(3000)
        //设置是否为自动轮播，默认是“是”。
        banner.isAutoPlay(true)
        //设置指示器的位置，小点点，左中右。
        banner.setIndicatorGravity(BannerConfig.CENTER)
            //以上内容都可写成链式布局，这是轮播图的监听。比较重要。方法在下面。
            .setOnBannerListener(this)
            //必须最后调用的方法，启动轮播图。
            .start()

    }
    //图片轮播加载
    class GlideImageLoader : ImageLoader() {
        override fun displayImage(context: Context?, path: Any, imageView: ImageView?) {
            //Glide 加载图片，Fresco也好、加载本地图片也好，这个类功能就是加载图片
            Glide.with(context!!).load(path).into(imageView!!)
        }
    }
    override fun OnBannerClick(position: Int) {
        Toast.makeText(this, listTitle[position]+":"+listPath[position],Toast.LENGTH_SHORT).show()
    }

}
