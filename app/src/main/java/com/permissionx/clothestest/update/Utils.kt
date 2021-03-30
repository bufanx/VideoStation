package com.permissionx.clothestest.update

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.util.Log
import com.permissionx.clothestest.network.GetAppVersionService
import com.permissionx.clothestest.network.UpdateServiceCreator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object Utils {


    fun getVersionCode(context: Context): Int {
        val pm = context.packageManager
        val packageInfo: PackageInfo
        var versionCode: Int =0
        try {
            packageInfo = pm.getPackageInfo(context.packageName, 0)
            versionCode = packageInfo.versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return versionCode
    }

    fun getVersionName(context: Context): String {
        val pm = context.packageManager
        val packageInfo: PackageInfo
        var versionName: String = ""
        try {
            packageInfo = pm.getPackageInfo(context.packageName, 0)
            versionName = packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return versionName
    }

    fun getAppVersion(){
        var versionName=""
        val getAppVersionService=UpdateServiceCreator.create(GetAppVersionService::class.java)
        getAppVersionService.getAppVersion().enqueue(object : Callback<LatestVersionResponse> {
            override fun onFailure(call: Call<LatestVersionResponse>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<LatestVersionResponse>, response: Response<LatestVersionResponse>) {
                val responseBody = response.body()
                if (responseBody != null) {
                    versionName = responseBody.data
                    Log.d("AppVersion", versionName)
                } else throw RuntimeException("连接超时")
            }
        })
    }
//    fun DownNew():Long{
//
//
//        val  request = DownloadManager.Request(Uri.parse(newAppURL))
//        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
//        request.setDestinationInExternalFilesDir(Login.getContext(), Environment.DIRECTORY_DOWNLOADS,"wd.apk")
//        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
//        // 设置 Notification 信息
//        request.setTitle("正在下载xx更新")
//        request.setDescription("下载完成后请点击打开")
//        request.setVisibleInDownloadsUi(true)
//        request.allowScanningByMediaScanner()
//        request.setMimeType("application/vnd.android.package-archive")
//
//        // 实例化DownloadManager 对象
//        val downloadManager = Login.context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
//        return downloadManager.enqueue(request)
//    }

    fun getFileName(urlName: String): String? {
        val start = urlName.lastIndexOf("/")
        val end = urlName.length
        return if (start != -1 && end != -1) {
            urlName.substring(start + 1, end)
        } else {
            null
        }
    }

}