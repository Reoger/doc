package com.hut.reoger.doc.utils.imageUtils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.youth.banner.loader.ImageLoader

/**
 * Created by CM on 2018/1/28.
 */
class GlideImageLoader : ImageLoader(){
    override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
        Glide.with(context).load(path).into(imageView)
    }
}