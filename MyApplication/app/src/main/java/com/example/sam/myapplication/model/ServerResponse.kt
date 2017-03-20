package com.example.sam.myapplication.model

import com.google.gson.annotations.SerializedName

class ServerResponse {
    @SerializedName("success")
    var success: Boolean = false
    @SerializedName("message")
    var message: String? = null


}