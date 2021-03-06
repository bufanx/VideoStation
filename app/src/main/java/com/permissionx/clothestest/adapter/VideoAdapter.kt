package com.permissionx.clothestest.adapter

import android.annotation.SuppressLint
import com.permissionx.clothestest.R
import com.permissionx.clothestest.resource.MyImageView
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.permissionx.clothestest.ItemId
import com.permissionx.clothestest.videoplay.ShowVideo
import com.permissionx.clothestest.videoplay.VideoItem
import com.permissionx.clothestest.videoplay.VideoPlayWebview


class VideoAdapter(private val videoList:List<VideoItem>, private val context: Context) :RecyclerView.Adapter<VideoAdapter.ViewHolder>(){
    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        val itemimg: MyImageView =view.findViewById(R.id.video_itimg_img)
        val itemtitle:TextView=view.findViewById(R.id.video_ittitle_img)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.video_item,parent,false)

        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val video=videoList[position]
        holder.itemView.setOnClickListener {
            val position=holder.adapterPosition
            Log.d("Adapter","${position},${videoList}")
            val video=videoList[position]
            //点击事件
            val intent= Intent(context, ShowVideo::class.java)
            intent.putExtra("pic_url",video.pic_url)
            intent.putExtra("video_description",video.description)
            intent.putExtra("video_num",video.num)
            intent.putExtra("video_id",video.video_id)
            context.startActivity(intent)
        }
        holder.itemimg.setImageURL(video.pic_url)
        holder.itemtitle.text = "剧名:${video.title}\n" +
                "类型:${video.video_type}\n"+
                "集数:${video.num.toString()}集"
    }

    override fun getItemCount(): Int {
        return videoList.size
    }


}