package com.example.demo_instaforfood.Api


import com.example.demo_instaforfood.TempModels.ResponseListUsers
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ReviewImageAPI {
    @GET("/api/users")
    suspend fun getAllUsers(@Query("page")page:Int): ResponseListUsers
}