package com.hut.reoger.doc.search.bean

import com.google.gson.annotations.SerializedName



data class Fields(@SerializedName("content")
                  val str: NullPoint)
class NullPoint

data class Highlight(@SerializedName("fields")
                     val fields: Fields)

data class SearchByContentBean(@SerializedName("highlight")
               val highlight: Highlight,
                               @SerializedName("size")
               val size: Int = 0,
                               @SerializedName("query")
               val query: Query,
                               @SerializedName("from")
               val from: Int = 0,
                               @SerializedName("_source")
               val Source: List<String>?)

data class Query(@SerializedName("match")
                 val match: Match)


data class Match(@SerializedName("content")
                 val content: String = "")


