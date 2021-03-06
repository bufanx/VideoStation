package com.permissionx.clothestest.adapter

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.permissionx.clothestest.R
import com.permissionx.clothestest.forum.UserComment
import de.hdodenhof.circleimageview.CircleImageView

class UserCommentAdapter(val context:Context, val userCommentList:List<UserComment>):
        RecyclerView.Adapter<UserCommentAdapter.ViewHolder>(){
    inner class ViewHolder(view: View) :RecyclerView.ViewHolder(view){
        val userIcon:CircleImageView=view.findViewById(R.id.userComment_icon)
        val userCommentName:TextView=view.findViewById(R.id.user_id)
        val userComment:TextView=view.findViewById(R.id.user_comment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.user_commtent,parent,false)
        val holder=ViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val userComment=userCommentList[position]
        holder.userCommentName.text=userComment.userName
        holder.userComment.text=userComment.comment
        //val bitmap:Bitmap= getHttpBitmap(此处填url)
        //holder.userIcon.setImageBitmap(bitmap)
        Glide.with(context).load(userComment.userCommentIcon).into(holder.userIcon)
    }

    override fun getItemCount()=userCommentList.size
}