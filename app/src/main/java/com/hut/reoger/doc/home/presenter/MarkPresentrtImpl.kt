package com.hut.reoger.doc.home.presenter

import android.content.Context
import com.hut.reoger.doc.bean.MarksBean
import com.hut.reoger.doc.home.view.IMarkDocView
import com.hut.reoger.doc.utils.db.IMarkDao
import com.hut.reoger.doc.utils.db.MarkDbImpl

/**
 * Created by reoger on 2018/4/15.
 */
class MarkPresentrtImpl(val context:Context,val markView :IMarkDocView) : IMarkPresenter{
    var markDao : IMarkDao?= null

    init {
        markDao = MarkDbImpl(context)
    }

    override fun deleteMarkById(doc_id: String): Boolean {
        if (doc_id.isEmpty())
            return false
        return markDao?.deleteMark(doc_id)==true
    }

    override fun readMarksFromDb(): List<MarksBean>? {
        return markDao?.getScollData(0,10)
    }

}