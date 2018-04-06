package com.hut.reoger.doc.read.bean


import com.google.gson.annotations.SerializedName

data class DataItem(@SerializedName("comment_time") val commentTime: String = "",
                    @SerializedName("comment_doc_id") val commentDocId: String = "",
                    @SerializedName("comment_user_id") val commentUserId: String = "",
                    @SerializedName("comment_id") val commentId: String = "",
                    @SerializedName("comment_user_name") val commentUserName: String = "",
                    @SerializedName("comment_score") val commentScore: String = "",
                    @SerializedName("comment_content") val commentContent: String = "")


data class CommentsByDoc(@SerializedName("code")
                         val code: Int = 0,
                         @SerializedName("data")
                         val data: List<DataItem>?,
                         @SerializedName("message")
                         val message: String = "")


