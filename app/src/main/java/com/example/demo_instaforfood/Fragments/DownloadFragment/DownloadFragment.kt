package com.example.demo_instaforfood.Fragments.DownloadFragment

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.Context

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.net.toUri
import androidx.lifecycle.Observer
import androidx.work.*
import com.bumptech.glide.Glide
import com.example.demo_instaforfood.databinding.FragmentDownloadBinding
import com.example.demo_instaforfood.Utils.Constants.KEY_FILE_NAME
import com.example.demo_instaforfood.Utils.Constants.KEY_FILE_TYPE
import com.example.demo_instaforfood.Utils.Constants.KEY_FILE_URI
import com.example.demo_instaforfood.Utils.Constants.KEY_FILE_URL
import java.util.UUID
import java.util.concurrent.TimeUnit


class DownloadFragment : Fragment() {

    lateinit var workManager: WorkManager
    lateinit var binding: FragmentDownloadBinding
    private var enquedRequestFlag = false
    private val tag = "Downloading"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDownloadBinding.inflate(inflater)
        
        workManager = WorkManager.getInstance(requireContext())

        for(workInfo in workManager.getWorkInfosByTag(tag).get()){
           if(workInfo?.state == WorkInfo.State.ENQUEUED || workInfo?.state == WorkInfo.State.RUNNING){
               enquedRequestFlag = true
               workerObserver(workInfo.id)
           }
        }

        val imageUrl = "https://images.pexels.com/photos/1402787/pexels-photo-1402787.jpeg"
        Glide.with(this).load(imageUrl).into(binding.ivUrlImage)

        binding.btnDownload.setOnClickListener {
            startDownloadingFile(imageUrl)
        }


        return binding.root


    }




    @SuppressLint("MissingPermission")
    private fun startDownloadingFile(
        imageUrl: String,
    ) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresStorageNotLow(true)
            .setRequiresBatteryNotLow(true)
            .build()
        val data = Data.Builder()

        data.apply {
            putString(KEY_FILE_NAME, "pexels-photo-1519753.jpeg")
            putString(KEY_FILE_URL, imageUrl)
            putString(KEY_FILE_TYPE, "JPEG")
        }

        val oneTimeWorkRequest =
            OneTimeWorkRequest.Builder(FileDownloadWorker::class.java)
                .setConstraints(constraints)
                .setBackoffCriteria(BackoffPolicy.LINEAR, 10, TimeUnit.SECONDS)
                .setInputData(data.build())
                .addTag(tag)
                .build()



        workManager.enqueue(oneTimeWorkRequest)

//        val worker = workManager.getWorkInfosByTag("Downloading")

        workerObserver(oneTimeWorkRequest.id)

    }

    fun workerObserver(workerId:UUID){
        workManager.getWorkInfoByIdLiveData(workerId)
            .observe(viewLifecycleOwner, Observer { info ->
                info?.let {
                    when (it.state) {
                        WorkInfo.State.SUCCEEDED -> {
//                            val uri = it.outputData.getString(KEY_FILE_URI)?.toUri()
                            enquedRequestFlag = false
                            binding.tvProgress.text = "Download Completed"
                        }
                        WorkInfo.State.FAILED -> {
                            enquedRequestFlag = false
                        }
                        WorkInfo.State.ENQUEUED -> {
                            enquedRequestFlag = true
                        }
                        WorkInfo.State.RUNNING -> {
                            binding.tvProgress.visibility = View.VISIBLE
                            binding.pbDownloading.visibility = View.VISIBLE
                            val progress = it.progress.getInt("Progress",0)
                            binding.tvProgress.text =  "${progress}%"
                            binding.pbDownloading.progress = progress
                        }
                        else -> {

                        }
                    }
                }
            })
    }


}