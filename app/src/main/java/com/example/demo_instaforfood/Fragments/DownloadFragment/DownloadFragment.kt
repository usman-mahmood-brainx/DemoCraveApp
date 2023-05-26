package com.example.demo_instaforfood.Fragments.DownloadFragment

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.Context
import android.content.Intent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.lifecycle.Observer
import androidx.work.*
import com.bumptech.glide.Glide
import com.example.demo_instaforfood.CameraActivity
import com.example.demo_instaforfood.databinding.FragmentDownloadBinding
import com.example.demo_instaforfood.Utils.Constants.KEY_FILE_NAME
import com.example.demo_instaforfood.Utils.Constants.KEY_FILE_TYPE

import com.example.demo_instaforfood.Utils.Constants.KEY_FILE_URL
import java.util.UUID
import java.util.concurrent.TimeUnit


class DownloadFragment : Fragment() {


    private lateinit var binding: FragmentDownloadBinding
    private lateinit var workManager: WorkManager
    private var enquedRequestFlag = false
    private val tag = "Downloading"
    private  val imageUrl = "https://images.pexels.com/photos/1402787/pexels-photo-1402787.jpeg"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDownloadBinding.inflate(inflater)
        
        workManager = WorkManager.getInstance(requireContext())

        imageSetup()
        checkingEnquedWorkRequest()

        // Download butoon listener
        binding.btnDownload.setOnClickListener {
            startDownloadingFile(imageUrl)
        }

        binding.btnCamera.setOnClickListener {
            val intent = Intent(requireContext(),CameraActivity::class.java)
            startActivity(intent)
        }

        return binding.root


    }

    private fun imageSetup() {
        Glide.with(this).load(imageUrl).into(binding.ivUrlImage)
    }

    private fun checkingEnquedWorkRequest() {
        for(workInfo in workManager.getWorkInfosByTag(tag).get()){
            if(workInfo?.state == WorkInfo.State.ENQUEUED || workInfo?.state == WorkInfo.State.RUNNING){
                enquedRequestFlag = true
                workerObserver(workInfo.id)
            }
        }
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

        workManager.enqueueUniqueWork("downloadImage",ExistingWorkPolicy.KEEP,oneTimeWorkRequest)
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
                            binding.apply {
                                tvProgress.visibility = View.VISIBLE
                                pbDownloading.visibility = View.VISIBLE
                                val progress = it.progress.getInt("Progress",0)
                                tvProgress.text =  "${progress}%"
                                pbDownloading.progress = progress
                            }

                        }
                        else -> {

                        }
                    }
                }
            })
    }


}