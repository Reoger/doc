package com.hut.reoger.doc.utils.downloader.bean

/**
 * Date: 2018/3/2 18:12
 * Email: luojie@cmcm.com
 * Author: luojie
 * Description: TODO
 */

data class ThreadInfo(
        var id : Int,
        var url : String,
        var start : Long,
        var end : Long,
        var finished : Long
)