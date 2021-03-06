package com.permissionx.clothestest.videoplay

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.FrameLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.permissionx.clothestest.R
import kotlinx.android.synthetic.main.activity_video_play_webview.*

class VideoPlayWebview :AppCompatActivity(){


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_play_webview)
        supportActionBar?.hide()
        val videoURL=intent.getStringExtra("URL")
        //使状态栏和背景融合
        val decorView=window.decorView//拿到当前的Activity的DecorView
        decorView.systemUiVisibility= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE//表示Activity的布局会显示到状态栏上面
        window.statusBarColor= Color.TRANSPARENT//最后调用一下statusBarColor()方法将状态栏设置为透明色
        //隐藏状态栏
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        wvBookPlay.settings.useWideViewPort = true
        wvBookPlay.settings.loadWithOverviewMode = true
        wvBookPlay.settings.allowFileAccess = true
        wvBookPlay.settings.setSupportZoom(true)
        wvBookPlay.settings.javaScriptCanOpenWindowsAutomatically = true
            Log.d("urlll",videoURL.toString())
        try {
            if (Build.VERSION.SDK_INT >= 16) {
                val clazz: Class<*> = wvBookPlay.settings.javaClass
                val method = clazz.getMethod("setAllowUniversalAccessFromFileURLs", Boolean::class.javaPrimitiveType)
                method.invoke(wvBookPlay.settings, true)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        wvBookPlay.settings.pluginState = WebSettings.PluginState.ON
        wvBookPlay.settings.domStorageEnabled = true // 必须保留，否则无法播放优酷视频，其他的OK
        wvBookPlay.webChromeClient = MyWebChromeClient() // 重写一下，有的时候可能会出现问题

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            wvBookPlay.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }

        val cookieManager= CookieManager.getInstance()
        val stringBuffer=StringBuffer()
        stringBuffer.append("android")
        cookieManager.setCookie(videoURL, stringBuffer.toString())
        cookieManager.setAcceptCookie(true)

        if (videoURL != null) {
            wvBookPlay.loadUrl(videoURL)
        }else{
            Toast.makeText(this,"URL ERROR",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        when(newConfig.orientation){
            Configuration.ORIENTATION_LANDSCAPE -> {
                window.clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
                window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            }
            Configuration.ORIENTATION_PORTRAIT -> {
                window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
                window.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
            }
        }
    }

    inner class MyWebChromeClient: WebChromeClient() {
        private var mCallback: CustomViewCallback? = null
        override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {

            Log.i("ToVmp", "onShowCustomView")
            fullScreen()
            wvBookPlay.visibility = View.GONE
            flVideoContainer?.visibility = View.VISIBLE
            flVideoContainer?.addView(view)
            mCallback = callback
            super.onShowCustomView(view, callback)
        }

        override fun onHideCustomView() {
            Log.i("ToVmp", "onHideCustomView")
            fullScreen()
            wvBookPlay.visibility = View.VISIBLE
            flVideoContainer?.visibility = View.GONE
            flVideoContainer?.removeAllViews()
            super.onHideCustomView()
        }
    }

    private fun fullScreen() {
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            Log.i("ToVmp", "横屏")
        } else {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            Log.i("ToVmp", "竖屏")
        }
    }

    override fun onDestroy() {
        wvBookPlay.destroy()
        super.onDestroy()
    }
}