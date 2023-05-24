package com.example.demo_instaforfood.Fragments.DownloadFragment

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.demo_instaforfood.MainActivity
import com.example.demo_instaforfood.R
import com.example.demo_instaforfood.Utils.Constants.CHANNEL_DESC
import com.example.demo_instaforfood.Utils.Constants.CHANNEL_NAME
import com.example.demo_instaforfood.Utils.Constants.KEY_FILE_NAME
import com.example.demo_instaforfood.Utils.Constants.KEY_FILE_TYPE
import com.example.demo_instaforfood.Utils.Constants.KEY_FILE_URI
import com.example.demo_instaforfood.Utils.Constants.KEY_FILE_URL
import com.example.demo_instaforfood.Utils.Constants.NOTIFICATION_ID
import java.io.File
import java.io.FileOutputStream
import java.net.URL

class FileDownloadWorker(private val context:Context,private val workerParameters: WorkerParameters):Worker(context,workerParameters) {

    override fun doWork(): Result {


        val mimeType = when(workerParameters.inputData.getString(KEY_FILE_TYPE)){
            "PDF" -> "application/pdf"
            "PNG" -> "image/png"
            "MP4" -> "video/mp4"
            "JPEG"-> "image/jpeg"
            else -> ""
        }
        val filename = workerParameters.inputData.getString(KEY_FILE_NAME)
        val url = workerParameters.inputData.getString(KEY_FILE_URL)
        url?.let {
            return try {
                val uri = downloadFileFromUri(url,mimeType,filename )
                uri?.let {

                }
                // Show download Complete notifcation even if the app is closed
                displayNotifcation("Download Complete","File downloaded successfully")
                Result.success(workDataOf(KEY_FILE_URI to uri.toString()))
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("UsmanError",e.message?:"e is null")
                displayNotifcation("Download Failed","Unable to Download File")
                Result.failure()
            }

        }

        return Result.failure()
    }



    private fun downloadFileFromUri(url: String,mimeType: String, filename: String?): Uri? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
            }

            val resolver = context.contentResolver
            val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
            return if (uri != null) {
                URL(url).openStream().use { input ->
                    resolver.openOutputStream(uri).use { output ->
                        input.copyTo(output!!, DEFAULT_BUFFER_SIZE)
                    }
                }
                uri
            } else {
                null
            }

        } else {

            val target = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                filename
            )
            URL(url).openStream().use { input ->
                FileOutputStream(target).use { output ->
                    input.copyTo(output)
                }
            }

            return target.toUri()
        }
    }

    private fun displayNotifcation(title: String,text:String) {
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "download_channel"
            val channelName = "Download Channel"
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        val notificationBuilder = NotificationCompat.Builder(applicationContext, "download_channel")
            .setContentTitle(title)
            .setContentText(text)
            .setSmallIcon(R.drawable.ic_chat)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        val intent = Intent(applicationContext, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        notificationBuilder.setContentIntent(pendingIntent)

        notificationManager.notify(0, notificationBuilder.build())
    }

}