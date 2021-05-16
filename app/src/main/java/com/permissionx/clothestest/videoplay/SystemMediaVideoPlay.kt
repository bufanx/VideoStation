package com.permissionx.clothestest.videoplay

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.media.PlaybackParams
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.SurfaceHolder
import android.view.View
import android.view.View.SYSTEM_UI_FLAG_FULLSCREEN
import android.widget.FrameLayout
import android.widget.SeekBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.permissionx.clothestest.R
import kotlinx.android.synthetic.main.activity_system_media_video_play.*
import kotlinx.android.synthetic.main.controller_layout_first.*
import kotlinx.android.synthetic.main.controller_layout_second.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
class SystemMediaVideoPlay : AppCompatActivity() {
    private lateinit var videoViewModel: VideoViewModel
    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_system_media_video_play)
        updatePlayerProgress()
        val videoURL=intent.getStringExtra("URL")
        videoViewModel = ViewModelProvider(this).get(VideoViewModel::class.java).apply {
            progressBarVisibility.observe(this@SystemMediaVideoPlay, Observer {
                mediaVideoProgressBar.visibility = it
            })
            videoSolution.observe(this@SystemMediaVideoPlay, Observer {
                //视频时长
                landOrProChange({
                    seekBar3.max = mediaPlayer.duration
                },{
                    seekBar2.max = mediaPlayer.duration
                })
                playerFrame.post{
                    resizePlayer(it.first,it.second)
                }
            })
            speedStatus.observe(this@SystemMediaVideoPlay, Observer {
                when(it){
                    SpeedStatus.One -> {
                        videoViewModel.mediaPlayer.playbackParams = PlaybackParams().apply {
                            speed = 1f
                            pitch = 1f
                        }
                    }
                    SpeedStatus.Two-> {
                        videoViewModel.mediaPlayer.playbackParams = PlaybackParams().apply {
                            speed = 2f
                            //pitch = 2f //音高
                        }
                    }
                }
                lifecycleScope.launch {
                    delay(500)
                    if (videoViewModel.mediaPlayer.isPlaying)
                        landOrProChange({big_screen_pause.setImageResource(R.drawable.ic_baseline_pause_24)},
                            {small_screen_pause.setImageResource(R.drawable.ic_baseline_pause_24)})
                }
            })
            controllerFrameVisibility.observe(this@SystemMediaVideoPlay, Observer {
                landOrProChange({
                    controllerFrameSecond.visibility = it
                    //unlock_land?.visibility = it
                },{
                    controllerFrameFirst.visibility = it
                    //unlock_pro?.visibility = it
                })
            })
            bufferPercent.observe(this@SystemMediaVideoPlay, Observer {
                landOrProChange({
                    seekBar3.secondaryProgress = seekBar3.max * it/100
                },{
                    seekBar2.secondaryProgress = seekBar2.max * it/100
                })
            })
            playerStatus.observe(this@SystemMediaVideoPlay, Observer {
                landOrProChange({
                    big_screen_pause.isClickable = true
                },{
                    small_screen_pause.isClickable = true
                })
                when(it){
                    PlayerStatus.Paused -> landOrProChange({
                        big_screen_pause.setImageResource(R.drawable.ic_baseline_play_arrow_24)
                    },{
                        small_screen_pause.setImageResource(R.drawable.ic_baseline_play_arrow_24)
                    })
                    PlayerStatus.Completed -> landOrProChange({
                        big_screen_pause.setImageResource(R.drawable.ic_baseline_replay_24)
                    },{
                        small_screen_pause.setImageResource(R.drawable.ic_baseline_replay_24)
                    })
                    PlayerStatus.NotReady -> landOrProChange({
                        big_screen_pause.isClickable = false
                    },{
                        small_screen_pause.isClickable = false
                    })
                    else -> landOrProChange({
                        big_screen_pause.setImageResource(R.drawable.ic_baseline_pause_24)
                    },{
                        small_screen_pause.setImageResource(R.drawable.ic_baseline_pause_24)
                    })
                }
            })
//            lockStatus.observe(this@SystemMediaVideoPlay, Observer {
//                when(it){
//                    LockStatus.Unlock -> {
//                        landOrProChange({
//                            unlock_land?.setImageResource(R.drawable.unlock)
//                            controllerFrameSecond.isClickable = true
//                        },{
//                            unlock_pro?.setImageResource(R.drawable.unlock)
//                            controllerFrameFirst.isClickable = true
//                        })
//                    }
//                    LockStatus.Locked ->{
//                        landOrProChange({
//                            unlock_land?.setImageResource(R.drawable.lock)
//                            controllerFrameSecond.isClickable = false
//                        },{
//                            unlock_pro?.setImageResource(R.drawable.lock)
//                            controllerFrameFirst.isClickable = false
//                        })
//                    }
//                }
//            })
            lifecycleScope.launch {
                while (true){
                    val totalTime = mediaPlayer.duration
                    val beginTime = mediaPlayer.currentPosition
                    val beginShowTime:String = changeTime(beginTime)
                    val showTime:String = changeTime(totalTime)
                    landOrProChange({
                        end_time.text = showTime
                        start_time.text = beginShowTime
                    },{
                        total_time.text="${beginShowTime}/${showTime}"
                    })
                    delay(500)
                }
            }
        }
        playerFrame.setOnLongClickListener {
            videoViewModel.togglePlayerStatus()
            false
        }
        landOrProChange({
            big_screen_pause.setOnClickListener {
                videoViewModel.togglePlayerStatus()
            }
            speed.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked){
                    videoViewModel._speedStatus.value = SpeedStatus.Two
                }else{
                    videoViewModel._speedStatus.value = SpeedStatus.One
                }
            }
        },{
            small_screen_pause.setOnClickListener {
                videoViewModel.togglePlayerStatus()
            }
        })
        playerFrame.setOnClickListener {
            videoViewModel.toggleControllerVisibility()
        }
//        landOrProChange({
//            unlock_land?.setOnClickListener {
//                videoViewModel.toggleLockStatus()
//            }
//        },{
//            unlock_pro?.setOnClickListener {
//                videoViewModel.toggleLockStatus()
//            }
//        })
        landOrProChange({
            seekBar3.setOnSeekBarChangeListener(object:SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    if (fromUser){
                        videoViewModel.playerSeekToProgress(progress)
                        Log.d("progress!!",progress.toString())
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    //TODO("Not yet implemented")
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    //TODO("Not yet implemented")
                }

            })
        },{
            seekBar2.setOnSeekBarChangeListener(object:SeekBar.OnSeekBarChangeListener{
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    if (fromUser){
                        videoViewModel.playerSeekToProgress(progress)
                        Log.d("progress!!",progress.toString())
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    //TODO("Not yet implemented")
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    //TODO("Not yet implemented")
                }

            })
        })
        lifecycle.addObserver(videoViewModel.mediaPlayer)
        surfaceView.holder.addCallback(object:SurfaceHolder.Callback{
            override fun surfaceCreated(holder: SurfaceHolder) {
                //TODO("Not yet implemented")
            }
            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
                videoViewModel.mediaPlayer.setDisplay(holder)
                videoViewModel.mediaPlayer.setScreenOnWhilePlaying(true)
                playerFrame.post {
                    resizePlayer(width,height)
                }
            }
            override fun surfaceDestroyed(holder: SurfaceHolder) {
                //TODO("Not yet implemented")
            }

        })

    }


    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            hideSystemUI()
            videoViewModel.emmitVideoResolution()
            to_small_screen.setOnClickListener {
                this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
        }else if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            to_big_screen.setOnClickListener {
                this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            }
        }
    }

    //适配横屏宽高比
    private fun resizePlayer(width:Int,height:Int){
        if (width == 0 || height == 0) return
        surfaceView.layoutParams = FrameLayout.LayoutParams(
            playerFrame.height*width/height,
            FrameLayout.LayoutParams.MATCH_PARENT,
            Gravity.CENTER
        )
    }
    private fun hideSystemUI(){
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or SYSTEM_UI_FLAG_FULLSCREEN)
    }
    private fun landOrProChange(blockLand:()->Unit,blockPro: () -> Unit){
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            blockLand()
        }else if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            blockPro()
        }
    }
    private fun updatePlayerProgress(){
        lifecycleScope.launch {
            while (true){
                delay(500)
                landOrProChange({
                    seekBar3.progress = videoViewModel.mediaPlayer.currentPosition
                },{
                    seekBar2.progress = videoViewModel.mediaPlayer.currentPosition
                })
            }
        }
    }
    private fun changeTime(time:Int):String{
        val second :Int= time/1000
        if (second>3600){
            val hours:Int = second/60/60
            val minutes:Int = second/60%60
            val seconds:Int = second%60
            return "${formatTime(hours)}:${formatTime(minutes)}:${formatTime(seconds)}"
        }else{
            val minute:Int = second/60
            val seconds:Int = second%60
            return "${formatTime(minute)}:${formatTime(seconds)}"
        }
    }
    private fun formatTime(time:Int):String{
        return when {
            time<=0 -> {
                "00"
            }
            time in 1..9 -> {
                "0${time}"
            }
            else -> {
                time.toString()
            }
        }
    }
}