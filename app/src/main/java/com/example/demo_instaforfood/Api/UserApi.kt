package com.example.demo_instaforfood.Api

import com.example.demo_instaforfood.Models.LoginRequest
import com.example.demo_instaforfood.Models.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface UserApi {

    @Headers("Content-Type:application/json")
    @POST("api/v1/users/sign_in.json")
    suspend fun userLogin(@Body request: LoginRequest): Response<LoginResponse>
}