package com.hut.reoger.doc.search.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hut.reoger.doc.R
import com.hut.reoger.doc.search.bean.Hits
import com.hut.reoger.doc.search.bean.HitsItem
import com.hut.reoger.doc.search.view.SizeLabel
import com.hut.reoger.doc.utils.log.TLog


/**
 * Created by CM on 2018/2/1.
 */

class SearchResultAdapter(mContext: Context?) : RecyclerView.Adapter<SearchResultAdapter.ItemHolder>() {

    var mContext: Context ?= null
//    private var list :List<Hit>?= null

    private var data : Hits ?=null
//    private var listData :MutableList<Hit>? = null

    init {
        this.mContext = mContext
    }

    fun setData(data: Hits){

        this.data = data
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ItemHolder {
           val v  =  LayoutInflater.from(mContext).inflate(R.layout.item_search_result,parent,false)
            return ItemHolder(v)
    }

    override fun getItemCount(): Int {
        data?.let {
            return data?.hits?.size!!
        }
        return 0
    }

    override fun onBindViewHolder(holder: ItemHolder?, position: Int) {
        if (data != null) {
            val item : HitsItem =data!!.hits!![position]
            holder?.title?.text = item?.Source.name  //设置标题
            var contents =  item?.highlight.content.toString()
            contents.removeSuffix("/r")
            contents.removeSuffix("/n")
//            contents.replace("em","strong") //暂时先不管，后面在进行修改。
            holder?.content?.text = Html.fromHtml(contents) //设置内容
            holder?.author?.text = item?.Source.author   //作者
            holder?.time?.text = item?.Source.time.toString() //时间

        }
    }


    class ItemHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var title: TextView?= null
        var content: TextView?= null
        var author: TextView?= null
        var time: TextView?= null

        init {
            title = itemView?.findViewById(R.id.item_search_title)
            content = itemView?.findViewById(R.id.item_search_content)
            author = itemView?.findViewById(R.id.item_search_author)
            time = itemView?.findViewById(R.id.item_search_time)
        }
    }

}