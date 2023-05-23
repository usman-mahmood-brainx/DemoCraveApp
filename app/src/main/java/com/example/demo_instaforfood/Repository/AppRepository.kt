package com.example.demo_instaforfood.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.demo_instaforfood.Api.ReviewImageApi
import com.example.demo_instaforfood.Api.UserApi
import com.example.demo_instaforfood.Models.Client
import com.example.demo_instaforfood.Models.LoginRequest
import com.example.demo_instaforfood.Models.LoginResponse
import com.example.demo_instaforfood.Paging.ReviewImagesPagingSource
import com.example.demo_instaforfood.SharedPreferencesHelper
import retrofit2.Response
import javax.inject.Inject


class AppRepository @Inject constructor(
    private val reviewImagesAPI: ReviewImageApi,
    private val userAPI: UserApi,
    private val sharedPreferencesHelper: SharedPreferencesHelper,
) {


    private val clientsLiveData = MutableLiveData<List<Client>>()
    val clientList: LiveData<List<Client>>
        get() = clientsLiveData

    fun getImages() = Pager(
        config = PagingConfig(pageSize = 20, maxSize = 100),
        pagingSourceFactory = { ReviewImagesPagingSource(reviewImagesAPI) }
    ).liveData

    suspend fun userLogin(loginRequest: LoginRequest): Response<LoginResponse> {
        val response = userAPI.userLogin(loginRequest)
        if (response.isSuccessful) {
            sharedPreferencesHelper.saveHeaders(response.headers())
        }
        return response
    }

    suspend fun getClientList(accessToken: String?, client: String?, uid: String?,name:String?) {
        val response = userAPI.getClientsList(accessToken, client, uid,name)
        clientsLiveData.value = response.body()
    }

}