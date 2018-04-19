package com.hut.reoger.doc.home.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hut.reoger.doc.R
import com.hut.reoger.doc.search.bean.HitsItem

/**
 * Created by reoger on 2018/3/26.
 */
class HistoryAdapter(val context:Context?) :  RecyclerView.Adapter<HistoryAdapter.ItemHolder>(){
    private var historyDate :List<HitsItem> ?=null

    /**
     * 设置数据
     */
    fun setData(hitsItem:List<HitsItem>){
        this.historyDate = hitsItem
        this.notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ItemHolder?, position: Int) {
        if (historyDate != null) {
            val item : HitsItem = historyDate!![position]
            holder?.title?.text = item.Source.name  //设置标题
            var contents =  item.highlight.content.toString()
            contents.removeSuffix("/r")
            contents.removeSuffix("/n")
//            contents.replace("em","strong") //暂时先不管，后面在进行修改。
            holder?.content?.text = Html.fromHtml(contents) //设置内容
            holder?.author?.text = item.Source.author   //作者
            holder?.time?.text = item.Source.time.toString() //时间

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): HistoryAdapter.ItemHolder {
        val v  =  LayoutInflater.from(context).inflate(R.layout.item_search_result,parent,false)
        return HistoryAdapter.ItemHolder(v)
    }

    override fun getItemCount(): Int {
        historyDate?.let {
            return historyDate?.size!!
        }
        return 0
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