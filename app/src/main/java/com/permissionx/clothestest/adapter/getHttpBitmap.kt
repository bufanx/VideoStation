package com.permissionx.clothestest.adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import de.hdodenhof.circleimageview.CircleImageView
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL


//fun getHttpBitmap(url: CircleImageView): Bitmap {
//    var myFileUrl: URL? = null
//    var bitmap: Bitmap? = null
//    try {
//        myFileUrl = URL(url)
//    } catch (e: MalformedURLException) {
//        e.printStackTrace()
//    }
//    try {
//        val conn: HttpURLConnection = myFileUrl?.openConnection() as HttpURLConnection
//        conn.setConnectTimeout(0)
//        conn.setDoInput(true)
//        conn.connect()
//        val input: InputStream = conn.getInputStream()
//        bitmap = BitmapFactory.decodeStream(input)
//        input.close()
//    } catch (e: IOException) {
//        e.printStackTrace()
//    }
//    return bitmap
//}