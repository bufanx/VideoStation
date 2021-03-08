package com.permissionx.clothestest.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.permissionx.clothestest.ItemId
import com.permissionx.clothestest.R
import kotlinx.android.synthetic.main.select_btn_item.view.*

class SelectVideoAdapter:
    RecyclerView.Adapter<SelectVideoAdapter.ViewHolder> {

    private var context: Context
    private lateinit var videoNumList: ArrayList<Int>
    private lateinit var onItemClickListener: OnItemClickListener

    constructor(context: Context, videoNumList: ArrayList<Int>) : super() {
        this.context = context
        this.videoNumList = videoNumList
    }


    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

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
        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(holder.itemView, position)
        }
        holder.itemView.select_video_btn.setOnClickListener {
            ItemId.itemId=selectBtn
            Log.d("position!!","${selectBtn},${ItemId.itemId}")
        }
        holder.itemView.setOnClickListener{
            ItemId.itemId=selectBtn
            Log.d("position!","${selectBtn},${ItemId.itemId}")
        }
    }

    override fun getItemCount()=videoNumList.size

    interface OnItemClickListener{
        fun onItemClick(view: View, position: Int)
        fun onItemLongClick(view: View, position: Int)
    }
}