package com.hut.reoger.doc.tool.view.fragment

import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.hut.reoger.doc.R
import com.hut.reoger.doc.base.BaseFragment
import com.mylhyl.zxing.scanner.encode.QREncode
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.google.zxing.client.result.ParsedResultType


/**
 * Created by reoger on 2018/4/23.
 */
class TextToQRCodeFragment :BaseFragment(){
    override fun getLayoutId(): Int {
        return R.layout.fragment_text_to_qr_code
    }

    override fun initView(mRootView: View) {
        val et = mRootView.findViewById<EditText>(R.id.et_text_to_qr)
        val bu = mRootView.findViewById<Button>(R.id.bu_text_to_image)
        val iv = mRootView.findViewById<ImageView>(R.id.iv_text_to_image)

        bu.setOnClickListener({
            iv.setImageBitmap(decodeQR(et.text.toString()))
        })

        iv.setOnLongClickListener {
            toast("假设保存")

            true
        }
    }

    fun decodeQR(str:String):Bitmap{
        val logBitmap = BitmapFactory.decodeResource(activity.resources,R.drawable.cat)
        return QREncode.Builder(activity)
                .setColor(resources.getColor(R.color.colorPrimary))//二维码颜色
                .setParsedResultType(ParsedResultType.TEXT)//默认是TEXT类型
                .setContents(str)//二维码内容
                .setLogoBitmap(logBitmap)//二维码中间logo
                .build().encodeAsBitmap()
    }
}