package com.hut.reoger.doc.bean

import com.google.gson.annotations.SerializedName

data class ServiceReply(
        @SerializedName("code") var code: Int, //2000
        @SerializedName("message") var message: String //登陆成功
)