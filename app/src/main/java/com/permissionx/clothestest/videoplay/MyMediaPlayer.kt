package com.permissionx.clothestest.videoplay

import android.media.MediaPlayer
import androidx.lifecycle.*

class MyMediaPlayer:MediaPlayer(), LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun pausePlayer(){
        pause()
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun resumePlayer(){
        start()
    }
}