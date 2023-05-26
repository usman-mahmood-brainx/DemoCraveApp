package com.example.demo_instaforfood.Api

import com.example.demo_instaforfood.Models.Client
import com.example.demo_instaforfood.Models.LoginRequest
import com.example.demo_instaforfood.Models.LoginResponse
import com.example.demo_instaforfood.Utils.Constants.GET_CLIENTS
import com.example.demo_instaforfood.Utils.Constants.USER_LOGIN
import retrofit2.Response
import retrofit2.http.*

interface UserApi {

    @Headers("Content-Type:application/json")
    @POST(USER_LOGIN)
    suspend fun userLogin(@Body request: LoginRequest): Response<LoginResponse>

    @Headers("Content-Type:application/json")
    @GET(GET_CLIENTS)
    suspend fun getClientsList(
        @Header("access-token") accessToken: String?,
        @Header("client") client: String?,
        @Header("uid") uid: String?,
        @Query("query") name:String?
    ):Response<List<Client>>
}