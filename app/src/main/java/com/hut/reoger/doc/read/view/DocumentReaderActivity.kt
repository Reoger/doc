package com.hut.reoger.doc.read.view

import android.os.Bundle
import com.hut.reoger.doc.base.BaseActivity
import com.hut.reoger.doc.R
import com.hut.reoger.doc.read.presenter.DocumentReadPresenterImple
import com.hut.reoger.doc.utils.log.TLog
import java.io.File

/**
 * Created by reoger on 2018/3/2.
 */


class DocumentReaderActivity : BaseActivity(), IReadView {

    override fun updateProgress(progress: Int) {

        if(progress>=99)
            stopLoad()
    }

    private var mSuperFileView: SuperFileView2? = null

    companion object {
        const val READ_ONLINE = "read_activity"
    }


    private var mReadPresenter : DocumentReadPresenterImple?=null

    private var filePath :String ?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_doc)

    }


    override fun setActionBar() {
       setActivityTitle("在线阅读界面")
    }

    override fun initView() {
        mReadPresenter = DocumentReadPresenterImple(this, this)
        filePath = intent.getStringExtra(READ_ONLINE)
        init()
    }


    private fun init() {
        mSuperFileView = findViewById(R.id.mSuperFileView)
        mSuperFileView!!.setOnGetFilePathListener(object : SuperFileView2.OnGetFilePathListener {
            override fun onGetFilePath(mSuperFileView2: SuperFileView2) {
                getFilePathAndShowFile(mSuperFileView2)
            }
        })

        mSuperFileView!!.show()

    }


    private fun getFilePathAndShowFile(mSuperFileView2: SuperFileView2) {

        if (filePath!!.contains("http")) {//网络地址要先下载
            startLoad()
            mReadPresenter?.downLoadFromNet(filePath!!, mSuperFileView2)
        } else {
            mSuperFileView2.displayFile(File(filePath))
        }
    }

    public override fun onDestroy() {
        super.onDestroy()
        TLog.d("FileDisplayActivity-->onDestroy")
        if (mSuperFileView != null) {
            mSuperFileView!!.onStopDisplay()
        }
    }




}
