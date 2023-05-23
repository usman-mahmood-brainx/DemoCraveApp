package com.example.demo_instaforfood.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.demo_instaforfood.Models.LoginRequest
import com.example.demo_instaforfood.Models.LoginResponse
import com.example.demo_instaforfood.Repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: AppRepository) : ViewModel() {

    private val _loginResponse : MutableLiveData<Response<LoginResponse>>  = MutableLiveData()
    val loginResponse: LiveData<Response<LoginResponse>>
        get() = _loginResponse
    
    fun login(loginRequest: LoginRequest) {
        viewModelScope.launch {
            _loginResponse.value = repository.userLogin(loginRequest)
        }
    }
}