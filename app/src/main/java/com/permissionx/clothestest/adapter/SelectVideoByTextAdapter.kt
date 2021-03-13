package com.permissionx.clothestest.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.permissionx.clothestest.ItemId
import com.permissionx.clothestest.R
import kotlinx.android.synthetic.main.select_btn_item.view.*

class SelectVideoByTextAdapter(private var context: Context, private var videoNumList: ArrayList<Int>) :
        RecyclerView.Adapter<SelectVideoByTextAdapter.ViewHolder>() {

    private lateinit var onItemClickListener: OnItemClickListener


    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val selectText: TextView =view.findViewById(R.id.select_text)
    }



    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val selectText=videoNumList[position]
        //holder.selectText.setTextColor(R.color.red)
        if (position<ItemId.itemId){
            holder.selectText.setTextColor(R.color.red)
        }
        holder.selectText.text=selectText.toString()
        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClick(holder.itemView, position)
            Log.d("qwe","postion:${position} itemid:${ItemId.itemId}")
            ItemId.itemId=selectText
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.card_select_btn,parent,false)
        val holder=ViewHolder(view)
        return holder
    }

    override fun getItemCount()=videoNumList.size

    interface OnItemClickListener{
        fun onItemClick(view: View, position: Int)
        fun onItemLongClick(view: View, position: Int)
    }


}