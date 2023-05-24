package com.example.demo_instaforfood.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demo_instaforfood.Models.Client
import com.example.demo_instaforfood.Models.LoginRequest
import com.example.demo_instaforfood.Models.LoginResponse
import com.example.demo_instaforfood.Repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ClientsViewModel @Inject constructor(private val repository: AppRepository) : ViewModel() {


    val clientList:LiveData<List<Client>>
        get() = repository.clientList

    fun getClientList(accessToken: String?, client: String?, uid: String?,name:String?) {
        viewModelScope.launch {
          repository.getClientList(accessToken,client,uid,name)
        }
    }
}