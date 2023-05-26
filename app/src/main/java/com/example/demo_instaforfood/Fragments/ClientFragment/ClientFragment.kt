package com.example.demo_instaforfood.Fragments.ClientFragment

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demo_instaforfood.SharedPreferencesHelper
import com.example.demo_instaforfood.ViewModels.ClientsViewModel
import com.example.demo_instaforfood.databinding.FragmentClientsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject


@AndroidEntryPoint
class ClientFragment : Fragment() {

    lateinit var binding: FragmentClientsBinding
    private lateinit var clientViewModel: ClientsViewModel
    @Inject lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    private var searchJob: Job? = null
    private lateinit var clientAdapter:ClientAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentClientsBinding.inflate(inflater)

        clientListSetup()
        searchBarSetup()

        return binding.root
    }

    private fun clientListSetup() {
        clientAdapter = ClientAdapter(emptyList())
        binding.rvClients.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = clientAdapter
        }

        clientViewModel = ViewModelProvider(requireActivity()).get(ClientsViewModel::class.java)
        sharedPreferencesHelper.let {
            clientViewModel.getClientList(it.getAccessToken(), it.getClient(), it.getUid(),"")
        }

        clientViewModel.clientList.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                binding.pbClients.visibility = View.GONE
                clientAdapter.setList(it)
            }
        })
    }

    private fun searchBarSetup() {
        binding.etClientSearch.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(editable: Editable?) {
                searchJob?.cancel() // Cancel any ongoing search job
                val query = editable.toString()
                searchJob = CoroutineScope(Dispatchers.Main).launch {
                    clientAdapter.setList(emptyList())
                    binding.pbClients.visibility = View.VISIBLE
                    delay(800) // Debounce delay
                    sharedPreferencesHelper.let {
                        clientViewModel.getClientList(it.getAccessToken(), it.getClient(), it.getUid(),query)
                    }
                }
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Release any resources associated with the view here
        searchJob?.cancel() // Cancel the ongoing search job, if any
    }


}