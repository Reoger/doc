package com.hut.reoger.doc.share

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Environment
import android.system.ErrnoException
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import com.hut.reoger.doc.R
import com.hut.reoger.doc.utils.log.LogUtils
import java.io.File
import java.io.FileOutputStream
import java.net.URI
import java.util.*


/**
 * Created by reoger on 2018/4/1.
 */
class ShareUtils(var context:Context) {

    //分享图片
    fun shareImage(title:String){

        Thread(Runnable {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            val uri = Uri.fromFile(preparShareImage())
            shareIntent.putExtra(Intent.EXTRA_STREAM,uri)
            shareIntent.type = "image/jpeg"
            val intent = Intent.createChooser(shareIntent,title)
             if ( context.packageManager.resolveActivity(intent,PackageManager.MATCH_ALL) !=null){
                context.startActivity(intent)

            }else{
                LogUtils.d("怀疑一下")
                throw Exception("can`t share")
            }
        }).start()

    }



    fun shareText(title:String) :Boolean{
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT,"test this")
        shareIntent.putExtra(Intent.EXTRA_TEXT,title)
        shareIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        val intent = Intent.createChooser(shareIntent,title)
        if ( context.packageManager.resolveActivity(intent,PackageManager.MATCH_ALL) !=null){
            context.startActivity(intent)
            return true
        }else{
            LogUtils.d("怀疑一下")
            return false
        }
    }

    private fun preparShareImage():File?{
        val shareView = setViewWidthAndHeight()
       return  viewSaveToImage(shareView)
    }


    private fun setViewWidthAndHeight():View{

        val resources = context.resources
        val dm = resources.displayMetrics
        val width = dm.widthPixels
        val height = dm.heightPixels

        LogUtils.d("宽和$height 高$width")

        val shareView:View = LayoutInflater.from(context).inflate(R.layout.view_share,null,false)
        layoutView(shareView,width,height)
        return shareView
    }

    private fun layoutView(v: View, width: Int, height: Int) {
        // 整个View的大小 参数是左上角 和右下角的坐标
        v.layout(0, 0, width, height)
        val measuredWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY)
        val measuredHeight = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)
        /** 当然，measure完后，并不会实际改变View的尺寸，需要调用View.layout方法去进行布局。
         * 按示例调用layout函数后，View的大小将会变成你想要设置成的大小。
         */
        v.measure(measuredWidth, measuredHeight)
        LogUtils.d("打印$measuredHeight 和 $measuredHeight 和 ${v.measuredHeight} 和${v.measuredWidth} ")
        v.layout(0, 0, v.measuredWidth, v.measuredHeight)
    }

    private  fun viewSaveToImage(view:View):File?{
        view.isDrawingCacheEnabled = true
        view.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
        view.drawingCacheBackgroundColor = Color.WHITE

        // 把一个View转换成图片
        val cachebmp = loadBitmapFromView(view)
        var file :File?=null
        val fos: FileOutputStream
        try {
            // 判断手机设备是否有SD卡
            val isHasSDCard = Environment.getExternalStorageState() == android.os.Environment.MEDIA_MOUNTED
            if (isHasSDCard) {
                // SD卡根目录
                val sdRoot = Environment.getExternalStorageDirectory().toString()+"/007/"
                file = File(sdRoot, Calendar.getInstance().timeInMillis.toString()+".png")
                val scanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
                fos = FileOutputStream(file)
                context.sendBroadcast(scanIntent)//通知媒体库更新
            } else
                throw Exception("创建文件失败!")

            cachebmp.compress(Bitmap.CompressFormat.PNG, 90, fos)

            fos.flush()
            fos.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }

        view.destroyDrawingCache()

        return file
    }

    private fun loadBitmapFromView(v:View):Bitmap{
        val w = v.width
        val h = v.height

        LogUtils.d("v的宽度$w 和长度 $h")

        val bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val c = Canvas(bmp)

        c.drawColor(Color.WHITE)
        /** 如果不设置canvas画布为白色，则生成透明 */
        v.layout(0, 0, w, h)
        v.draw(c)
        return bmp
    }
}