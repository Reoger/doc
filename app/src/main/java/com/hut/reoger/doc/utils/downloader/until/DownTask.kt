package com.hut.reoger.doc.utils.downloader.until

import android.content.Context
import android.content.Intent
import android.support.v4.content.LocalBroadcastManager
import android.util.Log
import com.hut.reoger.doc.utils.downloader.bean.FileInfo

import com.hut.reoger.doc.utils.downloader.service.DownloadService.Companion.ACTION_FINISHED
import com.hut.reoger.doc.utils.downloader.service.DownloadService.Companion.ACTION_UPDATE
import com.hut.reoger.doc.utils.downloader.service.DownloadService.Companion.DOWNLOAD_PATH

import com.hut.reoger.doc.utils.downloader.bean.ThreadInfo
import com.hut.reoger.doc.utils.downloader.db.ThreadDao
import com.hut.reoger.doc.utils.downloader.db.ThreadDaoImple
import com.hut.reoger.doc.utils.downloader.service.DownloadService
import com.hut.reoger.doc.utils.log.LogUtils
import com.hut.reoger.doc.utils.log.TLog
import java.io.File
import java.io.InputStream
import java.io.RandomAccessFile
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import java.util.concurrent.Executors



/**
 * Date: 2018/3/5 14:46
 * Email: luojie@cmcm.com
 * Author: luojie
 * Description: 下载的任务类
 */

class DownTask(context: Context, mFileInfo: FileInfo, threadCount: Int) {
    private var mContext: Context? = null
    private var mFileInfo: FileInfo? = null
    private var mThreadDAOMultImpe: ThreadDao? = null

    private var mFinished: Long = 0

    private var mThreadCount = 1

    var isPause = false

    private var mThreadList: MutableList<DownTaskThread>? = null


    init {
        this.mContext = context
        this.mFileInfo = mFileInfo
        this.mThreadCount = threadCount
        mThreadDAOMultImpe = ThreadDaoImple(context)
    }


    /**
     * 开始下载
     */
    fun startDownTask() {
        //开始下载
        val threadInfos: ArrayList<ThreadInfo> = mThreadDAOMultImpe!!.getThread(mFileInfo!!.url) as ArrayList<ThreadInfo>
        if (threadInfos.size == 0) {
            val length = mFileInfo!!.length / mThreadCount

            for (i in 0 until mThreadCount) {
                val threadInfo = ThreadInfo(i, mFileInfo!!.url, length * i, (i + 1) * length - 1, 0)
                if (i + 1 == mThreadCount)
                    threadInfo.end = (mFileInfo!!.length)
                //添加到线程信息集合中
                threadInfos.add(threadInfo)

                mThreadDAOMultImpe!!.insertThread(threadInfo)
            }
        }

        mThreadList = ArrayList()

        //启动多线程进行下载
        for (threadInfo in threadInfos) {
            val downloadThread = DownTaskThread(threadInfo)
            mExecutorService.execute(downloadThread)
            mThreadList!!.add(downloadThread)
        }
    }


    private val localBroadcastManager: LocalBroadcastManager?
        get() {
            val manager = LocalBroadcastManager.getInstance(mContext)
            return manager
        }

    //下载线程
    internal inner class DownTaskThread(private val threadInfo: ThreadInfo) : Thread() {
        var isFinished = false//表示线程是否结束

        override fun run() {
            //向数据库插入线程信息
            //            Log.e("isExists==", mThreadDAO2.isExists(threadInfo.getUrl(), threadInfo.getId()) + "");
            //            if (!mThreadDAO2.isExists(threadInfo.getUrl(), threadInfo.getId())) {
            //                mThreadDAO2.insertThread(threadInfo);
            //            }
            val connection: HttpURLConnection
            val raf: RandomAccessFile
            val inputStream: InputStream
            try {
                val url = URL(threadInfo.url)
                connection = url.openConnection() as HttpURLConnection
                connection.connectTimeout = 3000
                connection.requestMethod = "GET"
                //设置下载位置
                val start = threadInfo.start + threadInfo.finished
                connection.setRequestProperty("Range", "bytes=" + start + "-" + threadInfo.end)
                //设置文件写入位置
                val file = File(DOWNLOAD_PATH, mFileInfo!!.fileName)
                raf = RandomAccessFile(file, "rwd")
                raf.seek(start)

                //得到LocalBroadcastManager实例
                val manager = LocalBroadcastManager.getInstance(mContext)
                val intent = Intent()
                intent.action = ACTION_UPDATE
                //发送本地广播

//                val intent = Intent(ACTION_UPDATE)
                mFinished += threadInfo.finished
                LogUtils.d("hreadInfo.getFinish==  ${threadInfo.finished}")
                LogUtils.d("getResponseCode === ${connection.responseCode}")
                //开始下载
                if (connection.responseCode == HttpURLConnection.HTTP_PARTIAL || connection.responseCode == HttpURLConnection.HTTP_OK) {
                LogUtils.d("getContentLength==$connection.contentLength")
                    //读取数据
                    inputStream = connection.inputStream
                    val buffer = ByteArray(1024 * 4)
                    var len = -1
                    var time = System.currentTimeMillis()
                    while (true) {
                        len = inputStream.read(buffer)
                        if (len == -1)
                            break
                        if (isPause) {
                            LogUtils.d("mfinished==pause=== $mFinished.toString()")
                            //下载暂停时，保存进度到数据库
                            mThreadDAOMultImpe!!.updateThread(threadInfo.url, threadInfo.id,
                                    threadInfo.finished)
                            return
                        }

                        //写入文件
                        raf.write(buffer, 0, len)
                        //累加整个文件下载进度
                        mFinished += len.toLong()
                        //累加每个线程完成的进度
                        threadInfo.finished = (threadInfo.finished + len)
                        //每隔1秒刷新UI
                        if (System.currentTimeMillis() - time > 1000) {//减少UI负载
                            time = System.currentTimeMillis()
                            //把下载进度发送广播给Activity
                            intent.putExtra(BROAD_ID, mFileInfo!!.id)
                            intent.putExtra(BROAD_FINISH, mFinished * 100 / mFileInfo!!.length)
                            LogUtils.d("前下载进度为 --> ${mFinished * 100 / mFileInfo!!.length}")
                            manager.sendBroadcast(intent)
                        }

                    }
                    //标识线程执行完毕
                    isFinished = true
                    //检查下载任务是否完成
                    checkAllThreadFinished()
                    inputStream.close()
                }
                raf.close()
                connection.disconnect()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

    }

    /**
     * 判断所有线程是否都执行完毕
     */
    @Synchronized
    private fun checkAllThreadFinished() {
        val allFinished = mThreadList!!.all { it.isFinished }
        //编辑线程集合 判断是否执行完毕
        if (allFinished) {
            //删除线程信息
            mThreadDAOMultImpe!!.deleteThread(mFileInfo!!.url)
            //发送广播给Activity下载结束
            val intent = Intent(ACTION_FINISHED)
            intent.putExtra(BROAD_FILE_INFO, mFileInfo)
//            mContext!!.sendBroadcast(intent)
            DownloadService.downloadTask?.remove(mFileInfo!!)
            DownloadService.downLoadedTask?.add(mFileInfo!!)
            LogUtils.d("开始下载了-- ${mFileInfo.toString()}")
            LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent)
        }
    }

    companion object {

        var mExecutorService = Executors.newCachedThreadPool()!!

        const val BROAD_ID = "broadcast_id"
        const val BROAD_FINISH = "broadcast_finish"
        const val BROAD_FILE_INFO = "broadcast_file_info"
    }

}
