package com.hut.reoger.doc.utils.downloader.service

import android.app.Service
import android.content.Intent
import android.os.Environment
import android.os.Handler
import android.os.IBinder
import android.util.Log
import com.hut.reoger.doc.utils.downloader.bean.FileInfo
import com.hut.reoger.doc.utils.downloader.until.DownTask
import com.hut.reoger.doc.utils.log.LogUtils
import com.hut.reoger.doc.utils.log.TLog
import java.io.File
import java.io.IOException
import java.io.RandomAccessFile
import java.net.HttpURLConnection
import java.net.URL

/**
 * Date: 2018/3/5 14:42
 * Email: luojie@cmcm.com
 * Author: luojie
 * Description: 下载的服务
 */

class DownloadService : Service() {

    companion object {
        private const val TAG = "DownloadService"
        //初始化
        private const val MSG_INIT = 0
        //开始下载
        const val ACTION_START = "ACTION_START"
        //暂停下载
        const val ACTION_PAUSE = "ACTION_PAUSE"
        //结束下载
        const val ACTION_FINISHED = "ACTION_FINISHED"
        //更新UI
        const val ACTION_UPDATE = "ACTION_UPDATE"
        //传递的值
        const val EXTRE_INFO = "extre_info"

        //下载列表
        var downloadTask: MutableList<FileInfo>? = null
        //已经下载完成的列表
        var downLoadedTask: MutableList<FileInfo>? = null


        var DOWNLOAD_ID = -1
        //下载路径
        val DOWNLOAD_PATH = Environment.getExternalStorageDirectory().absolutePath + "/downloads/"

    }


    private val mHandler = Handler(Handler.Callback { message ->
        when (message.what) {
            MSG_INIT -> {
                var fileInfo: FileInfo = message.obj as FileInfo
                val instance = DownTask(this@DownloadService, fileInfo, 3)
                instance.startDownTask()
                tasks[fileInfo.id] = instance
            }
        }
        true
    })


    var tasks = LinkedHashMap<Int, DownTask>()


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if (ACTION_START == intent?.action) {
            //开始下载
            val fileInfo: FileInfo = intent?.getSerializableExtra(EXTRE_INFO) as FileInfo
            //这里还是需要先判断当前这个任务是否有正在下载的，
            val downTask: DownTask? = tasks[fileInfo.id]
            if (downTask != null && !downTask.isPause) {
                LogUtils.d("什么都不干，避免多次下载")
            } else {
                LogUtils.d("开始下载...${fileInfo.url}")
                downloadTask?.add(fileInfo)
                InitThread(fileInfo, mHandler).start()
            }
        } else if (ACTION_PAUSE == intent?.action) {
            //暂停下载
            val fileInfo: FileInfo = intent?.getSerializableExtra(EXTRE_INFO) as FileInfo
            val downTask: DownTask? = tasks[fileInfo.id]
            if (downTask != null) {
                downTask.isPause = true
            }
            TLog.d("TAG", "暂停下载")
        }
        return super.onStartCommand(intent, flags, startId)
    }


    override fun onBind(p0: Intent?): IBinder? {

        return null
    }

    class InitThread(fileInfo: FileInfo, mHandler: Handler) : Thread() {
        private var tFileInfo: FileInfo? = null
        private var mHandler: Handler? = null

        init {
            this.tFileInfo = fileInfo
            this.mHandler = mHandler
        }


        override fun run() {
            var conn: HttpURLConnection? = null
            var raf: RandomAccessFile? = null
            try {
                //连接网络文件
                val url = URL(tFileInfo?.url)
                conn = url.openConnection() as HttpURLConnection
                conn.connectTimeout = 3000
                conn.requestMethod = "GET"
                var length = -1
                LogUtils.d("getResponseCode== $conn.responseCode")
                if (conn.responseCode == HttpURLConnection.HTTP_OK) {
                    //获取文件长度
                    length = conn.contentLength
                    LogUtils.d("\"length==$length")
                }
                if (length < 0) {
                    return
                }
                val dir = File(DOWNLOAD_PATH)
                if (!dir.exists()) {
                    dir.mkdir()
                }
                //在本地创建文件
                val file = File(dir, tFileInfo?.fileName)
                raf = RandomAccessFile(file, "rwd")
                //设置本地文件长度
                raf!!.setLength(length.toLong())
                tFileInfo?.length = length.toLong()

                LogUtils.d("tFileInfo.getLength== ${tFileInfo?.length}")
                mHandler?.obtainMessage(MSG_INIT, tFileInfo)?.sendToTarget()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                try {
                    if (conn != null && raf != null) {
                        raf.close()
                        conn.disconnect()
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
    }


}