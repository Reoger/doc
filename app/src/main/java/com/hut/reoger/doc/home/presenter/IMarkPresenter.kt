package com.hut.reoger.doc.home.presenter

import com.hut.reoger.doc.bean.MarksBean

/**
 * Created by reoger on 2018/4/15.
 */
interface IMarkPresenter{
    fun readMarksFromDb():List<MarksBean>?

    fun deleteMarkById(doc_id:String):Boolean
}