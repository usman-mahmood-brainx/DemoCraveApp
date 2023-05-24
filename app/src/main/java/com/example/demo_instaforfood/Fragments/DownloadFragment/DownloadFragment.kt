package com.example.demo_instaforfood.Fragments.DownloadFragment

import android.annotation.SuppressLint

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import androidx.lifecycle.Observer
import androidx.work.*
import com.bumptech.glide.Glide
import com.example.demo_instaforfood.databinding.FragmentDownloadBinding
import com.example.demo_instaforfood.Utils.Constants.KEY_FILE_NAME
import com.example.demo_instaforfood.Utils.Constants.KEY_FILE_TYPE
import com.example.demo_instaforfood.Utils.Constants.KEY_FILE_URI
import com.example.demo_instaforfood.Utils.Constants.KEY_FILE_URL
import java.util.concurrent.TimeUnit


class DownloadFragment : Fragment() {

    lateinit var workManager: WorkManager
    lateinit var binding: FragmentDownloadBinding
    lateinit var notificationManager: NotificationManagerCompat
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDownloadBinding.inflate(inflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        workManager = WorkManager.getInstance(requireContext())
        notificationManager = NotificationManagerCompat.from(requireContext())

        val imageUrl = "https://images.pexels.com/photos/1402787/pexels-photo-1402787.jpeg"
        Glide.with(this).load(imageUrl).into(binding.ivUrlImage)

        binding.btnDownload.setOnClickListener {
            startDownloadingFile(imageUrl)
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
                .setBackoffCriteria(BackoffPolicy.LINEAR,10,TimeUnit.SECONDS)
                .setInputData(data.build())
                .addTag("Downloading")
                .build()


        workManager.enqueue(oneTimeWorkRequest)
//        val worker = workManager.getWorkInfosByTag("Downloading")


        workManager.getWorkInfoByIdLiveData(oneTimeWorkRequest.id)
            .observe(viewLifecycleOwner, Observer { info ->
                info?.let {
                    when (it.state) {
                        WorkInfo.State.SUCCEEDED -> {
                            val uri = it.outputData.getString(KEY_FILE_URI)
                            
                            Toast.makeText(
                                requireContext(), "Downloading Completed", Toast.LENGTH_SHORT
                            ).show()

                            Toast.makeText(requireContext(), uri, Toast.LENGTH_SHORT).show()
                            val imageUri = uri?.toUri()
                            binding.ivUrlImage.setImageURI(imageUri)


                        }
                        WorkInfo.State.FAILED -> {

                            Toast.makeText(
                                requireContext(), "Downloading Failed", Toast.LENGTH_SHORT
                            ).show()
                        }
                        WorkInfo.State.ENQUEUED -> {
                            Toast.makeText(
                                requireContext(), "Downloading Started", Toast.LENGTH_SHORT
                            ).show()
                        }
                        WorkInfo.State.RUNNING -> {
                            Toast.makeText(
                                requireContext(), "Downloading In Progress", Toast.LENGTH_SHORT
                            ).show()
                        }
                        else -> {

                        }
                    }
                }
            })
    }


}