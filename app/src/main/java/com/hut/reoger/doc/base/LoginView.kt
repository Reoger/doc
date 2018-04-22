//package com.hut.reoger.doc.base
//
//import android.content.Context
//import android.graphics.Bitmap
//import android.graphics.drawable.BitmapDrawable
//import android.view.View
//import android.view.ViewGroup
//import android.widget.PopupWindow
//import com.hut.reoger.doc.R
//
///**
// * Created by reoger on 2018/4/21.
// */
//class LoginView(val context: Context):PopupWindow(){
//    private val contentV by lazy {
//        View.inflate(context, R.layout.layout_loading, null)
//    }
//    private val parView = view
//
//
//    init
//    {
//        this.contentView = contentV
//        this.width = ViewGroup.LayoutParams.WRAP_CONTENT
//        this.height = ViewGroup.LayoutParams.WRAP_CONTENT
//        this.isFocusable = false
//        // 设置外部可以点击
//        this.isOutsideTouchable = true
//        // 测量当前popuwindow的宽高，必须测量，下一步还得用呢
//        this.contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
//        // 设置背景透明
//        this.setBackgroundDrawable(BitmapDrawable(view.context.resources, null as Bitmap?))
//        // 设置点击popuwindow外边不关闭popuwindow
//        this.setTouchInterceptor { _, _ ->
//            true
//        }
//    }
//    fun show()
//    {
//        // 显示的位置为，控件的下方水平中间的位置
//        showAsDropDown(parView,parView.width/2-contentView.measuredWidth/2,0)
//    }
//}