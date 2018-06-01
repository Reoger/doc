package com.hut.reoger.doc.utils.downloader.bean

import java.io.Serializable

/**
 * Date: 2018/3/2 18:12
 * Email: luojie@cmcm.com
 * Author: luojie
 * Description: 下载的信息
 */

data class FileInfo(
        var id : Int,
        var url : String,
        var fileName : String,
        var length : Long,
        var finish : Long

):Serializable