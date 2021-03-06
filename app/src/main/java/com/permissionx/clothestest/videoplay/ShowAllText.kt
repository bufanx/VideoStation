package com.permissionx.clothestest.videoplay

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.annotation.RequiresApi
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.permissionx.clothestest.R
import kotlinx.android.synthetic.main.activity_show_all_text.*

class ShowAllText : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_all_text)

        val videoDescription=intent.getStringExtra("video_description")

        show_all_text.text=videoDescription
    }
}