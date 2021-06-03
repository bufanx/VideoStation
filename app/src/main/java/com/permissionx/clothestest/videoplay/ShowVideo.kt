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
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.permissionx.clothestest.ItemId
import com.permissionx.clothestest.ItemId.videoNum
import com.permissionx.clothestest.R
import com.permissionx.clothestest.URL
import com.permissionx.clothestest.adapter.SelectVideoByTextAdapter
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_show_video.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ShowVideo : AppCompatActivity() {

    private val videoNumList= ArrayList<Int>()
    private val viewModel by lazy { ViewModelProvider(this).get(GetVideoUrlViewModel::class.java) }
    private val refreshViewModel by lazy {ViewModelProvider(this).get(RefreshVideoViewModel::class.java)}
    private val getVideoNumViewModel by lazy { ViewModelProvider(this).get(GetVideoNumViewModel::class.java) }
    private lateinit var adapterText:SelectVideoByTextAdapter
    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_video)
        val picUrl:String=ItemId.picUrl
        val videoDescription:String=ItemId.videoDescription
        val videoId: Int = ItemId.videoId.value!!
        showvideo_progressBar.visibility = View.VISIBLE
        getVideoNumViewModel.getVideoNum(videoId)
        getVideoNumViewModel.responseBodyLiveData.observe(this,{ result->
            val response = result.getOrNull() as GetUrlResponse
            val videoNum = response.data.toInt()
            if (videoNum == 0){
                Toast.makeText(this,"集数获取失败",Toast.LENGTH_SHORT).show()
            }else{
                videoNumList.clear()
                for (i in (0 until videoNum)){
                    videoNumList.add(i+1)
                }
                if (videoNumList.isEmpty()){
                    videoNumList.add(0)
                    videoNumList.add(0)
                    videoNumList.add(0)
                }
                Log.d("videoNumList", videoNumList.toString())
                val layoutManager= GridLayoutManager(this,3)
                select_video_rcv.layoutManager=layoutManager
                adapterText=SelectVideoByTextAdapter(this,videoNumList)
                select_video_rcv.adapter=adapterText
                showvideo_progressBar.visibility = View.INVISIBLE
                ItemId.videoNum = 0
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
            }
        })
        textCardView.setOnClickListener {
            val intent=Intent(this,ShowAllText::class.java)
            intent.putExtra("video_description",videoDescription)
            startActivity(intent)
        }
//        select_video_rcv.adapter=adapterText
        /*adapter.setOnItemClickListener(object : SelectVideoAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                ItemId.itemId = position + 1
                Log.d("!!!!!","${ItemId.itemId},${position}")
            }

            override fun onItemLongClick(view: View, position: Int) {
                Toast.makeText(this@ShowVideo,"...",Toast.LENGTH_SHORT).show()
            }

        })*/
        swipeRefresh.setOnRefreshListener {
            refreshViewModel.refreshVideo(videoId)
        }
        refreshViewModel.responseBodyLiveData.observe(this,{result ->
            val response=result.getOrNull() as RefreshVideoResponse
            if (response.code==200){
                videoNumList.clear()
                for (i in (0 until response.data.toInt())){
                    videoNumList.add(i+1)
                }
                if (videoNumList.isEmpty()){
                    videoNumList.add(0)
                    videoNumList.add(0)
                    videoNumList.add(0)
                }
//                val layoutManager= GridLayoutManager(this,3)
//                select_video_rcv.layoutManager=layoutManager
//                //val adapter= SelectVideoAdapter(this,videoNumList)
//                val adapterText=SelectVideoByTextAdapter(this,videoNumList)
//                select_video_rcv.adapter=adapterText
                swipeRefresh.isRefreshing=false
            }else{
                //错误的话就返回后端传回来的错误信息
                Toast.makeText(this,response!!.msg,Toast.LENGTH_SHORT).show()
            }
        })
        show_video_image.setImageURL(picUrl)
        show_video_text.text=videoDescription
        viewModel.responseBodyLiveData.observe(this,{ result->
            val response=result.getOrNull() as GetUrlResponse
            Log.d("URL",response.data)
            if (response.code==200){
                //此处打开播放器
                val intent=Intent(this, SystemMediaVideoPlay::class.java)
                URL.isPlayingURL=response.data
                //intent.putExtra("URL",response.data)
                startActivity(intent)
            }else{
                //错误的话就返回后端传回来的错误信息
                Toast.makeText(this,response!!.msg,Toast.LENGTH_SHORT).show()
            }
        })
    }
}


