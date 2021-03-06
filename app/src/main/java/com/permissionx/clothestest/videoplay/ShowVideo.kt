package com.permissionx.clothestest.videoplay

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
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.permissionx.clothestest.ItemId
import com.permissionx.clothestest.MainActivity
import com.permissionx.clothestest.R
import com.permissionx.clothestest.adapter.SelectVideoAdapter
import com.permissionx.clothestest.login.LoginViewModel
import com.permissionx.clothestest.network.GetUrlRequest
import com.permissionx.clothestest.network.GetUrlResponse
import com.permissionx.clothestest.network.LoginRequest
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_show_video.*
import java.time.temporal.ValueRange
import kotlin.math.log
import kotlin.text.toInt as toInt1

class ShowVideo : AppCompatActivity() {

    private val videoNumList= ArrayList<Int>()
    private val viewModel by lazy { ViewModelProvider(this).get(GetVideoUrlViewModel::class.java) }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_video)
        val picUrl=intent.getStringExtra("pic_url")
        val videoDescription=intent.getStringExtra("video_description")
        val videoNum=intent.getIntExtra("video_num",1)
        val videoId=intent.getIntExtra("video_id",414)
        videoNumList.clear()
        for (i in (0 until videoNum)){
            videoNumList.add(i+1)
        }
        if (videoNumList.isEmpty()){
            videoNumList.add(0)
            videoNumList.add(0)
            videoNumList.add(0)
        }
        textCardView.setOnClickListener {
            val intent=Intent(this,ShowAllText::class.java)
            intent.putExtra("video_description",videoDescription)
            startActivity(intent)
        }
        Log.d("videoNumList", videoNumList.toString())
        val layoutManager= GridLayoutManager(this,3)
        select_video_rcv.layoutManager=layoutManager
        val adapter= SelectVideoAdapter(this,videoNumList)
        select_video_rcv.adapter=adapter
        show_video_image.setImageURL(picUrl)
        show_video_text.text=videoDescription
        beginPlay.setOnClickListener {
            val requestbody=GetUrlRequest(videoId,ItemId.itemId)
            Log.d("iddd","videoID:${videoId} itemId:${ItemId.itemId}")
            viewModel.getVideoUrl(requestbody)
        }
        viewModel.responseBodyLiveData.observe(this,{ result->
            val response=result.getOrNull() as GetUrlResponse
            Log.d("URL",response.data)
            if (response.code==200){
                val intent=Intent(this, VideoPlayWebview::class.java)
                intent.putExtra("URL",response.data)
                startActivity(intent)
            }else{
                //错误的话就返回后端传回来的错误信息
                Toast.makeText(this,response!!.msg,Toast.LENGTH_SHORT).show()
            }
        })
    }
}
