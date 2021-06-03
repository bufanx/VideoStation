package com.permissionx.clothestest.videoplay

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.permissionx.clothestest.ItemId
import com.permissionx.clothestest.R
import com.permissionx.clothestest.adapter.VideoAdapter
import kotlinx.android.synthetic.main.activity_search_video.*

class SearchVideo : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProvider(this).get(SearchVideoViewModel::class.java) }

    private lateinit var response: SearchVideoResponse
    private var videoList=ArrayList<VideoItem>()

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_video)
        //使状态栏和背景融合
        val decorView=window.decorView//拿到当前的Activity的DecorView
        decorView.systemUiVisibility= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE//表示Activity的布局会显示到状态栏上面
        window.statusBarColor= Color.TRANSPARENT//最后调用一下statusBarColor()方法将状态栏设置为透明色
        //隐藏状态栏
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        search_video.setOnClickListener {
            val query:String=search_video_edit.text.toString()
            viewModel.searchVideo(query)
            search_video_pgb.visibility=View.VISIBLE
        }
        search_video_edit.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query:String=search_video_edit.text.toString()
                viewModel.searchVideo(query)
            }
            false
        }

        viewModel.responseBodyLiveData.observe(this,{result->
            response= result.getOrNull() as SearchVideoResponse
            initVideos()
            val layoutManager= GridLayoutManager(this,1)
            video_play_rcv.layoutManager=layoutManager
            val adapter= VideoAdapter(videoList,this)
            video_play_rcv.adapter=adapter
            Log.d("videoList","$videoList")
            search_video_pgb.visibility=View.GONE
        })
        ItemId.videoId.observe(this,{
            val videoId : Int? = ItemId.videoId.value
            if (videoId!=null){
                val intent = Intent(this,ShowVideo::class.java)
                startActivity(intent)
            }
        })
    }

    private fun initVideos(){
        videoList.clear()
        videoList=response.data
    }
}