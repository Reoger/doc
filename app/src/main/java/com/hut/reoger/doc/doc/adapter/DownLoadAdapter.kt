package com.hut.reoger.doc.doc.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.hut.reoger.doc.R
import com.hut.reoger.doc.utils.downloader.bean.FileInfo

/**
 * Created by reoger on 2018/5/18.
 */
class DownLoadAdapter(val context: Context) : RecyclerView.Adapter<DownLoadAdapter.ItemView>() {

    var datas: MutableList<FileInfo>? = null

    fun setData(data: MutableList<FileInfo>) {
        this.datas = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ItemView {
        val v = LayoutInflater.from(context).inflate(R.layout.item_download, parent)
        return ItemView(v)
    }

    override fun getItemCount(): Int {
        if (datas == null || datas!!.isEmpty()) {
            return 0
        } else {
            return datas!!.size
        }
    }

    override fun onBindViewHolder(holder: ItemView, position: Int) {
        if (datas != null) {
            val itemData = datas!![position]
            holder.mTvName?.text = itemData?.fileName
            // 这里有三个都不是固定写死的，都是实时更新的
        }
    }


    class ItemView(val view: View) : RecyclerView.ViewHolder(view) {
        var mTvName: TextView? = null
        var mTvProgress: TextView? = null
        var mProgress: ProgressBar? = null
        var mBuControl: Button? = null

        init {
            mTvName = view.findViewById<TextView>(R.id.tv_download_name)
            mTvProgress = view.findViewById<TextView>(R.id.tv_download_progress)
            mProgress = view.findViewById<ProgressBar>(R.id.pr_dowloader)
            mBuControl = view.findViewById<Button>(R.id.bu_control)
        }
    }
}