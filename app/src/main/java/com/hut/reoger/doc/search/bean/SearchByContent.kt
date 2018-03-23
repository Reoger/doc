package com.hut.reoger.doc.search.bean

import com.google.gson.annotations.SerializedName


/**
 * Date: 2018/2/4 17:07
 * Email: luojie@cmcm.com
 * Author: luojie
 * Description: 用来返回指定的字段，避免oom
 */

data class SearchByContent(
		@SerializedName("from") var from: Int, //0
		@SerializedName("size") var size: Int, //10
		@SerializedName("query") var query: Query,
		@SerializedName("_source") var sorece: List<String>
)

data class Query(
		@SerializedName("match") var match: Match
)

data class Match(
		@SerializedName("content") var content: String //中国
)