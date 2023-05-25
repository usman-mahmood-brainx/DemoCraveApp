package com.example.demo_instaforfood.Fragments.DownloadFragment

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
import androidx.core.net.toUri
import androidx.work.ForegroundInfo
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.demo_instaforfood.MainActivity
import com.example.demo_instaforfood.R
import com.example.demo_instaforfood.Utils.Constants.KEY_FILE_NAME
import com.example.demo_instaforfood.Utils.Constants.KEY_FILE_TYPE
import com.example.demo_instaforfood.Utils.Constants.KEY_FILE_URI
import com.example.demo_instaforfood.Utils.Constants.KEY_FILE_URL
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

class FileDownloadWorker(private val context:Context,private val workerParameters: WorkerParameters):Worker(context,workerParameters) {

    companion object{
        const val PROGRESS_NOTIFICATION_ID = 0
        const val COMPLETE_NOTIFICATION_ID = 1
        const val CHANNEL_ID = "download"
        const val CHANNEL_NAME = "Download Channel"
    }
    lateinit var  notificationManager: NotificationManager


    override fun doWork(): Result {
        notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

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

                showCompletionNotification("Download Complete","File downloaded successfully")
                Result.success(workDataOf(KEY_FILE_URI to uri.toString()))
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("UsmanError",e.message?:"e is null")
                showCompletionNotification("Download Failed","Unable to Download File")
                Result.failure()
            }

        }
        showCompletionNotification("Download Failed","Unable to Download File")
        return Result.failure()
    }

    private fun downloadFileFromUri(url: String,mimeType: String, filename: String?): Uri? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val connection = URL(url).openConnection() as HttpURLConnection
            connection.requestMethod = "HEAD"
            val contentLength = connection.contentLength.toLong()

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
                        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
                        var bytesRead:Int
                        var totalBytesRead = 0L
                        while (input.read(buffer).also{bytesRead = it} != -1){
                            output?.write(buffer,0,bytesRead)
                            totalBytesRead += bytesRead
                            val progress = ((totalBytesRead*100)/contentLength).toInt()
                            setProgressAsync(workDataOf("Progress" to progress))
                            setForegroundAsync(progressNotification(progress))
                        }
//                        input.copyTo(output!!, DEFAULT_BUFFER_SIZE)
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

    private fun showCompletionNotification(title: String, text:String) {
        val notificationBuilder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
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


        notificationManager.cancel(PROGRESS_NOTIFICATION_ID)
        notificationManager.notify(COMPLETE_NOTIFICATION_ID, notificationBuilder.build())
    }


    private fun progressNotification(progress: Int): ForegroundInfo {

        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle("Downloading")
            .setContentText("File is downloading...")
            .setTicker("Car Image")
            .setSmallIcon(R.drawable.ic_custom_marker)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setOnlyAlertOnce(true)
            .setAutoCancel(true)
            .setProgress(100, progress, false)

//        notificationManager.notify(PROGRESS_NOTIFICATION_ID, notificationBuilder.build())
        val foregroundInfo = ForegroundInfo(PROGRESS_NOTIFICATION_ID,notificationBuilder.build())
        return foregroundInfo
    }

}