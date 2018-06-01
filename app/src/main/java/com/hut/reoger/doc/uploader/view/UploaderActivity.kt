package com.hut.reoger.doc.uploader.view

import android.content.Intent
import android.os.Bundle
import com.hut.reoger.doc.R
import com.hut.reoger.doc.base.BaseActivity
import kotlinx.android.synthetic.main.activity_uploader.*
import android.app.Activity
import com.hut.reoger.doc.uploader.presenter.UploaderPresenter
import com.hut.reoger.doc.utils.log.LogUtils


/**
 * Created by reoger on 2018/3/28.
 */
class UploaderActivity : BaseActivity(),IUploaderView{


    override fun uploaderFileSuccessful(doc_id: String) {
        LogUtils.d(doc_id)
        toast("文件上传成功！")
    }

    override fun uploaderFileFail(error: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        const val INTENT_FILE_URL   = 0x11
    }

    private var mPresenter :UploaderPresenter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_uploader)
    }
    override fun setActionBar() {
        setActivityTitle("文件上传")
    }

    override fun initView() {
        mPresenter = UploaderPresenter(this,this)
        btu_uploader_file_select.setOnClickListener({
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "*/*"
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            startActivityForResult(intent,INTENT_FILE_URL)
        })

        but_uploader.setOnClickListener({
            //todo 加上条件的判断
            mPresenter?.uploader(cb_uploader_private.isChecked,et_uploader_abstract.text.toString(),et_uploader_author.text.toString(),tv_uploader_file_url.text.toString())
        })


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == INTENT_FILE_URL) {
                val uri = data?.data
                tv_uploader_file_url.text = uri.toString()
            }
        }
    }

}