package com.hut.reoger.doc.user.model

import com.google.gson.annotations.SerializedName

/**
 * Created by CM on 2018/1/26.
 */
data class RegisterData(
        @SerializedName("user_name") var user_name: String,
        @SerializedName("user_passwd") var user_passwd: String,
        @SerializedName("nickname") var nickname: String,
        @SerializedName("user_birthday") var user_birthday: String,
        @SerializedName("user_email") var user_email: String,
        @SerializedName("user_introduction") var user_introduction: String
)