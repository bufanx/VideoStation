package com.permissionx.clothestest.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.permissionx.clothestest.R
import com.permissionx.clothestest.game.Game
import com.permissionx.clothestest.game.ShowGameInfo
import de.hdodenhof.circleimageview.CircleImageView

class ShowGameAdapter(val context: Context, private val gameList:List<Game>):
    RecyclerView.Adapter<ShowGameAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) :RecyclerView.ViewHolder(view){
        val gameName:TextView=view.findViewById(R.id.game_name)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(context).inflate(R.layout.game_item,parent,false)
        val holder=ViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val game=gameList[position]
        holder.gameName.text=game.name
        holder.itemView.setOnClickListener {
        val intent=Intent(context,ShowGameInfo::class.java)
            intent.putExtra("dlc",game.dlc)
            intent.putExtra("download1",game.download1)
            intent.putExtra("download2",game.download2)
            intent.putExtra("pwd",game.pwd)
            context.startActivity(intent)
        }
    }

    override fun getItemCount()=gameList.size
}