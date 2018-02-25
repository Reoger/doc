package com.hut.reoger.doc.home.model
import com.google.gson.annotations.SerializedName



/**
 * Created by CM on 2018/1/28.
 */


data class SearchByContentBean(
		@SerializedName("from") var from: Int, //10
		@SerializedName("size") var size: Int, //10
		@SerializedName("query") var query: Query
)

data class Query(
		@SerializedName("match") var match: Match
)

data class Match(
		@SerializedName("content") var content: String //res
)