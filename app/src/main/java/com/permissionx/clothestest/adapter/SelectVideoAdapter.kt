package com.permissionx.clothestest.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.permissionx.clothestest.ItemId
import com.permissionx.clothestest.R

class SelectVideoAdapter(val context:Context, private val videoNumList:List<Int>) :
    RecyclerView.Adapter<SelectVideoAdapter.ViewHolder>() {

   inner class ViewHolder(view:View):RecyclerView.ViewHolder(view) {
        val selectBtn=view.findViewById<Button>(R.id.select_video_btn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.select_btn_item,parent,false)
        val holder=ViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val selectBtn=videoNumList[position]
        holder.selectBtn.text=selectBtn.toString()
        holder.itemView.setOnClickListener{
            val videoPosition=holder.adapterPosition
            ItemId.itemId=videoPosition+1
            Log.d("position!","${videoPosition},${ItemId.itemId}")
        }
    }

    override fun getItemCount()=videoNumList.size

}