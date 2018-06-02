package com.hut.reoger.doc.tool.view.fragment

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.hut.reoger.doc.R
import com.hut.reoger.doc.base.BaseFragment
import com.hut.reoger.doc.tool.utils.Utils
import com.hut.reoger.doc.utils.imageUtils.ImageSave
import com.hut.reoger.doc.utils.log.LogUtils
import java.io.ByteArrayOutputStream

/**
 * Created by reoger on 2018/4/22.
 */
class TextToImageFragment : BaseFragment() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_text_to_image
    }

    var bitmap:Bitmap ?=null

    override fun initView(mRootView: View) {
        val text = mRootView.findViewById<EditText>(R.id.et_text_to_image)
        val image = mRootView.findViewById<ImageView>(R.id.iv_show_cache)
        val button = mRootView.findViewById<Button>(R.id.bu_text_to_image)

        button.setOnClickListener({
            text.text
             bitmap = BitmapFactory.decodeResource(activity.resources, R.drawable.item_card_bg)
            if (bitmap != null) {
                val bitmapp2 = Utils.getTextBitmap(bitmap, text.text.toString(), 11)
                val stream = ByteArrayOutputStream()
                bitmapp2.compress(Bitmap.CompressFormat.PNG, 100, stream)
                Glide.with(activity).load(stream.toByteArray()).asBitmap().into(image)
            } else {
                LogUtils.d("哎，为甚一直为nul啊")
                return@setOnClickListener
            }

        })

        image.setOnClickListener({
            ImageSave.saveImageToGallery(activity,bitmap)
        })
    }

}