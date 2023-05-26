package com.example.demo_instaforfood.Api


import com.example.demo_instaforfood.TemporaryModels.ResponseListUsers
import com.example.demo_instaforfood.Utils.Constants.GET_USERS
import retrofit2.http.GET
import retrofit2.http.Query


interface ReviewImageApi {
    @GET(GET_USERS)
    suspend fun getAllUsers(@Query("page")page:Int): ResponseListUsers
}