package com.permissionx.clothestest.game

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
import com.permissionx.clothestest.R
import com.permissionx.clothestest.adapter.ShowGameAdapter
import com.permissionx.clothestest.videoplay.SearchVideoResponse
import com.permissionx.clothestest.videoplay.VideoItem
import kotlinx.android.synthetic.main.activity_search_game.*

class SearchGame : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProvider(this).get(SearchGameViewModel::class.java) }
    private lateinit var response: GameSearchResponse
    private var gameList=ArrayList<Game>()

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_game)
        //使状态栏和背景融合
        val decorView=window.decorView//拿到当前的Activity的DecorView
        decorView.systemUiVisibility= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE//表示Activity的布局会显示到状态栏上面
        window.statusBarColor= Color.TRANSPARENT//最后调用一下statusBarColor()方法将状态栏设置为透明色
        //隐藏状态栏
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        search_game.setOnClickListener {
            val name:String=game_input.text.toString()
            Log.d("game_name",name)
            viewModel.getGame(name)
            search_game_pgb.visibility=View.VISIBLE
        }
        viewModel.responseBodyLiveData.observe(this,{ result->
            response=result.getOrNull() as GameSearchResponse
            Log.d("game_response",response.toString())
            if (response.code==200) {
                initGames()
                val layoutManager=GridLayoutManager(this,1)
                show_game_rcv.layoutManager=layoutManager
                val adapter=ShowGameAdapter(this,gameList)
                show_game_rcv.adapter=adapter
            }else{
                Toast.makeText(this,"response null",Toast.LENGTH_SHORT).show()
            }
            search_game_pgb.visibility=View.GONE
        })
    }

    private fun initGames(){
        gameList.clear()
        gameList= response.data as ArrayList<Game>
    }

}