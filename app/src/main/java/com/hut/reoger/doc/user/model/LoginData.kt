package com.hut.reoger.doc.user.model

import com.google.gson.annotations.SerializedName

/**
 * Created by CM on 2018/1/25.
 */

data class LoginData(
        @SerializedName("token") var token: String, //e23007c2f91a043ce0c333e20633ea31
        @SerializedName("user_id") var userId: String, //1
        @SerializedName("user_name") var userName: String //admin
)