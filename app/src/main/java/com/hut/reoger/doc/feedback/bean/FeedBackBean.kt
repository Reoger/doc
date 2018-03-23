package com.hut.reoger.doc.feedback.bean

import com.google.gson.annotations.SerializedName

/**
 * Created by reoger on 2018/3/19.
 */
data class FeedBackBean(
        @SerializedName("app_id")  var app_id :Int,
        @SerializedName("did")  var did :String,
        @SerializedName("version_id")  var version_id :Int,
        @SerializedName("version_mini") var version_mini :Int,
        @SerializedName("feedback") var feedback :String,
        @SerializedName("links") var links :String
)