package com.hut.reoger.doc.read.view

import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.hut.reoger.doc.utils.log.LogUtils
import com.hut.reoger.doc.utils.log.TLog
import com.tencent.smtt.sdk.TbsReaderView
import java.io.File

/**
 * Created by reoger on 2018/3/2.
 */

class SuperFileView2 @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr), TbsReaderView.ReaderCallback {
    private var mTbsReaderView: TbsReaderView? = null
    private val saveTime = -1


    private var mOnGetFilePathListener: OnGetFilePathListener? = null

    init {
        mTbsReaderView = TbsReaderView(context, this)
        this.addView(mTbsReaderView, LinearLayout.LayoutParams(-1, -1))
    }


    fun setOnGetFilePathListener(mOnGetFilePathListener: OnGetFilePathListener) {
        this.mOnGetFilePathListener = mOnGetFilePathListener

    }


    private fun getTbsReaderView(context: Context): TbsReaderView {
        return TbsReaderView(context, this)
    }

    fun displayFile(mFile: File?) {

        if (mFile != null && !TextUtils.isEmpty(mFile.toString())) {
            //增加下面一句解决没有TbsReaderTemp文件夹存在导致加载文件失败
            val bsReaderTemp = Environment.getExternalStorageDirectory().absolutePath + "/TbsReaderTemp"
            val bsReaderTempFile = File(bsReaderTemp)

            if (!bsReaderTempFile.exists()) {
                TLog.d("bsReaderTemp！！")
                val mkdir = bsReaderTempFile.mkdir()
                if (!mkdir) {
                    TLog.e("bsReaderTemp！！！！！")
                }
            }

            //加载文件
            val localBundle = Bundle()
            LogUtils.d(mFile.toString())
            localBundle.putString("filePath", mFile.toString())
            TLog.d("filePath.toString  =" + mFile.toString())

            localBundle.putString("tempPath", Environment.getExternalStorageDirectory().absolutePath + "/" + "TbsReaderTemp")

            if (this.mTbsReaderView == null)
                this.mTbsReaderView = getTbsReaderView(context)
            val bool = this.mTbsReaderView!!.preOpen(getFileType(mFile.toString()), false)
            if (bool) {
                this.mTbsReaderView!!.openFile(localBundle)
            }
        } else {
            TLog.e("文件路径无效！")
        }

    }

    /***
     * 获取文件类型
     *
     * @param paramString
     * @return
     */
    private fun getFileType(paramString: String): String {
        var str = ""

        if (TextUtils.isEmpty(paramString)) {
            Log.d(TAG, "paramString---->null")
            return str
        }
        Log.d(TAG, "paramString:" + paramString)
        val i = paramString.lastIndexOf('.')
        if (i <= -1) {
            Log.d(TAG, "i <= -1")
            return str
        }

        val j = paramString.lastIndexOf('?')
        if (j <= -1){
            str = paramString.substring(i + 1)
        }else{
            str  = paramString.substring(i+1,j);
        }


        Log.d(TAG, "paramString.substring(i + 1)------>" + str)
        return str
    }

    fun show() {
        if (mOnGetFilePathListener != null) {
            mOnGetFilePathListener!!.onGetFilePath(this)
        }
    }

    /***
     * 将获取File路径的工作，“外包”出去
     */
    interface OnGetFilePathListener {
        fun onGetFilePath(mSuperFileView2: SuperFileView2)
    }


    override fun onCallBackAction(integer: Int?, o: Any, o1: Any) {
        TLog.e("****************************************************" + integer!!)
    }

    fun onStopDisplay() {
        if (mTbsReaderView != null) {
            mTbsReaderView!!.onStop()
        }
    }

    companion object {

        private const val TAG = "SuperFileView"
    }
}

