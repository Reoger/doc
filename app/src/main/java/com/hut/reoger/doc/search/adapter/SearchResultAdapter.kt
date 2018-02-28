package com.hut.reoger.doc.search.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hut.reoger.doc.R
import com.hut.reoger.doc.home.model.Hit
import com.hut.reoger.doc.home.model.Source


/**
 * Created by CM on 2018/2/1.
 */

class SearchResultAdapter(mContext: Context?) : RecyclerView.Adapter<SearchResultAdapter.ItemHolder>() {

    var mContext: Context ?= null
    private var list :List<Hit>?= null

//    private var listData :MutableList<Hit>? = null

    init {

        this.mContext = mContext
    }

    fun setData(list: List<Hit>){

       this.list = list
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ItemHolder {
           val v  =  LayoutInflater.from(mContext).inflate(R.layout.item_search_result,parent,false)
            return ItemHolder(v)
    }

    override fun getItemCount(): Int {
        list?.let {
            return list?.size!!
        }
        return 0
    }

    override fun onBindViewHolder(holder: ItemHolder?, position: Int) {
        if (list != null) {
            val item: Source = list!![position].source
            holder?.title?.text = item?.name
            if (item?.content.length > 80)
                item?.content = item.content.substring(80)
            holder?.content?.text = item?.content
            holder?.author?.text = item?.organization
            holder?.time?.text = item?.uploadTime.toString()

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