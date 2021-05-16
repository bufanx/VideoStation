package com.permissionx.clothestest.videoplay

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.permissionx.clothestest.URL
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


enum class PlayerStatus{
    Playing,Paused,Completed,NotReady
}
//enum class LockStatus{
//    Locked,Unlock
//}
enum class SpeedStatus{
    One,Two
}
class VideoViewModel : ViewModel() {
    private var controllerShowTime = 0L
    val mediaPlayer = MyMediaPlayer()
    private val _progressBarVisibility = MutableLiveData(View.VISIBLE)
    private var url = URL.TEST_URL
    val progressBarVisibility : LiveData<Int> = _progressBarVisibility
    private val _videoResolution = MutableLiveData(Pair(0,0))
    val videoSolution : LiveData<Pair<Int,Int>> = _videoResolution
    private val _controllerFrameVisibility = MutableLiveData(View.INVISIBLE)
    var controllerFrameVisibility:LiveData<Int> = _controllerFrameVisibility
    private val _bufferPercent = MutableLiveData(0)
    val bufferPercent:LiveData<Int> = _bufferPercent
    private val _playerStatus = MutableLiveData(PlayerStatus.NotReady)
    val playerStatus:LiveData<PlayerStatus> = _playerStatus
    val _speedStatus = MutableLiveData(SpeedStatus.One)
    val speedStatus :LiveData<SpeedStatus> = _speedStatus
//    private val _lockStatus = MutableLiveData(LockStatus.Unlock)
//    val lockStatus : LiveData<LockStatus> = _lockStatus

    init {
        loadVideo(URL.isPlayingURL)
    }

    fun loadVideo(url:String){
        mediaPlayer.apply {
            _progressBarVisibility.value = View.VISIBLE
            //重置不同的url
            reset()
            setDataSource(url)
            _playerStatus.value = PlayerStatus.NotReady
            setOnPreparedListener{
              _progressBarVisibility.value = View.INVISIBLE
                it.start()
                _playerStatus.value = PlayerStatus.Playing
            }
            setOnErrorListener { mp, what, extra ->
                Log.d("video_error","${what}")
                false
            }
            setOnVideoSizeChangedListener { _, width, height ->
                _videoResolution.value = Pair(width,height)
            }
            setOnBufferingUpdateListener { mp, percent ->
                _bufferPercent.value = percent
            }
            setOnCompletionListener {
                _playerStatus.value = PlayerStatus.Completed
            }
            prepareAsync()
            setOnSeekCompleteListener {
                mediaPlayer.start()
                _progressBarVisibility.value = View.INVISIBLE
            }
        }
    }

//    fun toggleSpeedStatus(){
//        when(_speedStatus.value){
//            SpeedStatus.One -> _speedStatus.value = SpeedStatus.Two
//            SpeedStatus.Two -> _speedStatus.value = SpeedStatus.One
//        }
//    }
    fun togglePlayerStatus(){
        when(_playerStatus.value){
            PlayerStatus.Playing -> {
                mediaPlayer.pausePlayer()
                _playerStatus.value = PlayerStatus.Paused
            }
            PlayerStatus.Paused -> {
                mediaPlayer.resumePlayer()
                _playerStatus.value = PlayerStatus.Playing
            }
            PlayerStatus.Completed ->{
                mediaPlayer.start()
                _playerStatus.value = PlayerStatus.Playing
            }
            else -> return
        }
    }
    fun toggleControllerVisibility(){
        if (_controllerFrameVisibility.value == View.INVISIBLE){
            _controllerFrameVisibility.value = View.VISIBLE
            controllerShowTime = System.currentTimeMillis()
            viewModelScope.launch {
                delay(3000)
                //超过三秒关闭进度条 检查最后一次点击是否过了三秒
                if(System.currentTimeMillis() - controllerShowTime > 3000){
                    _controllerFrameVisibility.value = View.INVISIBLE
                }
            }
        }else{
            _controllerFrameVisibility.value = View.INVISIBLE
        }
    }
//    fun toggleLockStatus(){
//        when(_lockStatus.value){
//            LockStatus.Unlock -> _lockStatus.value=LockStatus.Locked
//            LockStatus.Locked -> _lockStatus.value = LockStatus.Unlock
//            else -> return
//        }
//    }
    fun emmitVideoResolution(){
        _videoResolution.value = _videoResolution.value
    }

    fun playerSeekToProgress(progress:Int){
        _progressBarVisibility.value = View.VISIBLE
        mediaPlayer.seekTo(progress)
    }
    override fun onCleared() {
        super.onCleared()
        mediaPlayer.release()
    }
}