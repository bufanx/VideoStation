package com.permissionx.clothestest.videoplay

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.permissionx.clothestest.ItemId
import com.permissionx.clothestest.R
import com.permissionx.clothestest.adapter.SelectVideoByTextAdapter
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_show_video.*

class ShowVideo : AppCompatActivity() {

    private val videoNumList= ArrayList<Int>()
    private val viewModel by lazy { ViewModelProvider(this).get(GetVideoUrlViewModel::class.java) }

    @SuppressLint("ResourceAsColor")
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
        //val adapter= SelectVideoAdapter(this,videoNumList)
        val adapterText=SelectVideoByTextAdapter(this,videoNumList)

        /*adapter.setOnItemClickListener(object : SelectVideoAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                ItemId.itemId = position + 1
                Log.d("!!!!!","${ItemId.itemId},${position}")
            }

            override fun onItemLongClick(view: View, position: Int) {
                Toast.makeText(this@ShowVideo,"...",Toast.LENGTH_SHORT).show()
            }

        })*/
        select_video_rcv.adapter=adapterText
        adapterText.setOnItemClickListener(object : SelectVideoByTextAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                ItemId.itemId = position + 1
                Toast.makeText(this@ShowVideo,"已选中第${ItemId.itemId}集,请稍作等待!",Toast.LENGTH_SHORT).show()
                val requestbody= GetUrlRequest(videoId,ItemId.itemId)
                viewModel.getVideoUrl(requestbody)
            }

            override fun onItemLongClick(view: View, position: Int) {
                Toast.makeText(this@ShowVideo,"...",Toast.LENGTH_SHORT).show()
            }

        })
        show_video_image.setImageURL(picUrl)
        show_video_text.text=videoDescription
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
