package com.example.demo_instaforfood.TemporaryModels

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("avatar")
    var avatar: String,
    @SerializedName("email")
    var email: String,
    @SerializedName("first_name")
    var firstName: String,
    @SerializedName("id")
    var id: Int,
    @SerializedName("last_name")
    var lastName: String,
    //var suport:Support
)