package com.hut.reoger.doc.search.bean


import com.google.gson.annotations.SerializedName

/**
 * 重新构建的搜索结果bean对象
 */
data class Hits(@SerializedName("hits")
                val hits: List<HitsItem>?,
                @SerializedName("total")
                val total: Int = 0,
                @SerializedName("max_score")
                val maxScore: Double = 0.0)


data class ResultAsSearchBean(@SerializedName("_shards")
                              val Shards: Shards,
                              @SerializedName("hits")
                              val hits: Hits,
                              @SerializedName("took")
                              val took: Int = 0,
                              @SerializedName("timed_out")
                              val timedOut: Boolean = true)


data class Highlights(@SerializedName("content")
                     val content: List<String>?)


data class Shards(@SerializedName("total")
                  val total: Int = 0,
                  @SerializedName("failed")
                  val failed: Int = 0,
                  @SerializedName("successful")
                  val successful: Int = 0,
                  @SerializedName("skipped")
                  val skipped: Int = 0)


data class Source(@SerializedName("down_link")
                  val downLink: String = "",
                  @SerializedName("update_time")
                  val updateTime: Long = 0,
                  @SerializedName("size")
                  val size: Int = 0,
                  @SerializedName("author")
                  val author: String = "",
                  @SerializedName("name")
                  val name: String = "",
                  @SerializedName("time")
                  val time: Int = 0)


data class HitsItem(@SerializedName("highlight")
                    val highlight: Highlights,
                    @SerializedName("_index")
                    val Index: String = "",
                    @SerializedName("_type")
                    val Type: String = "",
                    @SerializedName("_source")
                    val Source: Source,
                    @SerializedName("_id")
                    val Id: String = "",
                    @SerializedName("_score")
                    val Score: Double = 0.0)


