package com.hut.reoger.doc.home.model
import com.google.gson.annotations.SerializedName


/**
 * Created by CM on 2018/1/28.
 */


data class ResultBySearchContent(
		@SerializedName("took") var took: Int, //21
		@SerializedName("timed_out") var timedOut: Boolean, //false
		@SerializedName("_shards") var shards: Shards,
		@SerializedName("hits") var hits: Hits
)

data class Hits(
		@SerializedName("total") var total: Int, //2068
		@SerializedName("max_score") var maxScore: Double, //3.2571268
		@SerializedName("hits") var hits: List<Hit>
)

data class Hit(
		@SerializedName("_index") var index: String, //document
		@SerializedName("_type") var type: String, //dang
		@SerializedName("_id") var id: String, //AWDyf0-bXBltiY6dTjJp
		@SerializedName("_score") var score: Double, //2.730814
		@SerializedName("_source") var source: Source
)

data class Source(
		@SerializedName("content") var content: String, //纪律和.
		@SerializedName("down_link") var downLink: String, //172.22.66.245:50075/webhdfs/v1/input/æ%
		@SerializedName("integer") var integer: Int, //135704
		@SerializedName("key_word") var keyWord: String, //党
		@SerializedName("name") var name: String, //湖工大党字
		@SerializedName("organization") var organization: String, //湖工大
		@SerializedName("time") var time: Int, //2015
		@SerializedName("upload_time") var uploadTime: Long //1515896917913
)

data class Shards(
		@SerializedName("total") var total: Int, //24
		@SerializedName("successful") var successful: Int, //24
		@SerializedName("skipped") var skipped: Int, //0
		@SerializedName("failed") var failed: Int //0
)