package com.example.demo_instaforfood.TempModels

import com.google.gson.annotations.SerializedName

data class ResponseListUsers(
    @SerializedName("data")
    var data: List<Data>,
    @SerializedName("page")
    var page: Int,
    @SerializedName("per_page")
    var perPage: Int,
    @SerializedName("total")
    var total: Int,
    @SerializedName(
        "total_pages")
    var totalPages: Int
)