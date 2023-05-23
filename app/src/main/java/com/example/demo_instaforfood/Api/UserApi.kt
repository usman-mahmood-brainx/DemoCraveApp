package com.example.demo_instaforfood.Api

import com.example.demo_instaforfood.Models.Client
import com.example.demo_instaforfood.Models.LoginRequest
import com.example.demo_instaforfood.Models.LoginResponse
import retrofit2.Response
import retrofit2.http.*

interface UserApi {

    @Headers("Content-Type:application/json")
    @POST("api/v1/users/sign_in.json")
    suspend fun userLogin(@Body request: LoginRequest): Response<LoginResponse>

    @Headers("Content-Type:application/json")
    @GET("api/v1/clients.json")
    suspend fun getClientsList(
        @Header("access-token") accessToken: String?,
        @Header("client") client: String?,
        @Header("uid") uid: String?,
        @Query("query") name:String?
    ):Response<List<Client>>
}