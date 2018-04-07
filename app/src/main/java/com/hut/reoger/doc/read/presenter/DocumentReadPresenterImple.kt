package com.hut.reoger.doc.read.presenter

import android.content.Context
import android.os.Environment
import android.text.TextUtils
import com.hut.reoger.doc.App
import com.hut.reoger.doc.bean.ServiceReply
import com.hut.reoger.doc.read.bean.CommentsByDoc
import com.hut.reoger.doc.read.view.IReadView
import com.hut.reoger.doc.read.view.SuperFileView2
import com.hut.reoger.doc.user.model.LoginInfo
import com.hut.reoger.doc.utils.log.LogUtils
import com.hut.reoger.doc.utils.log.TLog
import com.hut.reoger.doc.utils.netWork.*
import com.hut.reoger.doc.utils.safe.Md5Tool
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.trello.rxlifecycle2.kotlin.bindUntilEvent
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

/**
 * Created by reoger on 2018/3/25.
 * 阅读界面的具体控制逻辑
 */
class DocumentReadPresenterImple(val context:RxAppCompatActivity,val mIReadView: IReadView): IDocumentReadPresenter {

    /**
     * 删除文档
     */
    override fun deleteComment(comment_id: Int) {
        ApiClient.instance.service.deletComment(App.instance.token,comment_id)
                .compose(NetworkScheduler.compose())
                .bindUntilEvent(context, event = ActivityEvent.DESTROY)
                .subscribe(object : ApiResponse<ServiceReply>(context) {
                    override fun success(data: ServiceReply) {
                        //评论添加成功
                        LogUtils.d("评论0删除成功")
                        mIReadView.deleteCommentSuccessful()
                    }
                    override fun failure(statusCode: Int, apiErrorModel: ApiErrorModel) {
                        LogUtils.d("评论删除fail $apiErrorModel")
                        mIReadView.deleteCommentFail(apiErrorModel.message)
                    }
                })
    }

    override fun doComment(comments: String, usr: String, doc_id: String,score:Int) {
        ApiClient.instance.service.insertComment(App.instance.token,usr,comments,score,doc_id)
                .compose(NetworkScheduler.compose())
                .bindUntilEvent(context, event = ActivityEvent.DESTROY)
                .subscribe(object : ApiResponse<ServiceReply>(context) {
                    override fun success(data: ServiceReply) {
                        //评论添加成功
                        LogUtils.d("评论添加成功")
                        mIReadView.commentSuccessful(data)
                    }
                    override fun failure(statusCode: Int, apiErrorModel: ApiErrorModel) {
                        LogUtils.d("评论添加fail $apiErrorModel")
                        mIReadView.commentFail(apiErrorModel.message)
                    }
                })
    }

    override fun loadComments(doc_id: String) {
       ApiClient.instance.service.queryCommentByDocId(App.instance.token,doc_id)
               .compose(NetworkScheduler.compose())
               .bindUntilEvent(context ,event = ActivityEvent.DESTROY)
               .subscribe(object :ApiResponse<CommentsByDoc>(context){
                   override fun success(data: CommentsByDoc) {
                       LogUtils.d("加载评论成功$data")
                       if(data.code == 2000){
                            mIReadView.loadCommentSuccessful(data)
                       }
                   }

                   override fun failure(statusCode: Int, apiErrorModel: ApiErrorModel) {
                       TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                   }

               })
    }

    val TAG = "jj"

    override fun downLoadFromNet(url: String, mSuperFileView2: SuperFileView2) {
        //1.网络下载、存储路径、
        val cacheFile = getCacheFile(url)
        if (cacheFile.exists()) {
            if (cacheFile.length() <= 0) {
                TLog.d(TAG, "删除空文件！！")
                cacheFile.delete()
                return
            }
        }


        LoadFileModel.loadPdfFile(url, object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                TLog.d(TAG, "下载文件-->onResponse")
                val flag: Boolean
                var ints: InputStream? = null
                val buf = ByteArray(2048)
                var len = 0
                var fos: FileOutputStream? = null
                try {
                    val responseBody = response.body()
                    ints = responseBody?.byteStream()
                    val total = responseBody?.contentLength()

                    val file1 = getCacheDir(url)
                    if (!file1.exists()) {
                        file1.mkdirs()
                        TLog.d(TAG, "创建缓存目录： " + file1.toString())
                    }

                    val fileN = getCacheFile(url)//new File(getCacheDir(url), getFileName(url))

                    TLog.d(TAG, "创建缓存文件： " + fileN.toString())
                    if (!fileN.exists()) {
                        val mkdir = fileN.createNewFile()
                    }
                    fos = FileOutputStream(fileN)
                    var sum: Long = 0


                    while (true) {
                        len = ints!!.read(buf)
                        if (len == -1)
                            break
                        fos.write(buf, 0, len)
                        sum += len.toLong()
                        val progress = (sum * 1.0f / total!! * 100).toInt()
                        mIReadView.updateProgress(progress)
                        TLog.d(TAG, "写入缓存文件" + fileN.name + "进度: " + progress)
                        //这个地方可以添加进度提示
                    }
                    fos.flush()
                    TLog.d(TAG, "文件下载成功,准备展示文件。")
                    //2.ACache记录文件的有效期
                    mSuperFileView2.displayFile(fileN)
                } catch (e: Exception) {
                    TLog.d(TAG, "文件下载异常 = " + e.toString())
                } finally {
                    try {
                        if (ints != null)
                            ints.close()
                    } catch (e: IOException) {
                    }

                    try {
                        if (fos != null)
                            fos.close()
                    } catch (e: IOException) {
                    }

                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                TLog.d(TAG, "文件下载失败")
                val file = getCacheFile(url)
                if (!file.exists()) {
                    file.delete()
                }
            }
        })
    }

    /***
     * 绝对路径获取缓存文件
     *
     * @param url
     * @return
     */
    private fun getCacheFile(url: String): File {
        val cacheFile = File(Environment.getExternalStorageDirectory().absolutePath + "/007/"
                + getFileName(url))
        TLog.d(TAG, "缓存文件 = " + cacheFile.toString())
        return cacheFile
    }

    /***
     * 获取缓存目录
     *
     * @param url
     * @return
     */
    private fun getCacheDir(url: String): File {

        return File(Environment.getExternalStorageDirectory().absolutePath + "/007/")

    }

    /***
     * 根据链接获取文件名（带类型的），具有唯一性
     *
     * @param url
     * @return
     */
    private fun getFileName(url: String): String {
        return Md5Tool.hashKey(url) + "." + getFileType(url)
    }

    /***
     * 获取文件类型
     *
     * @param paramString
     * @return
     */
    private fun getFileType(paramString: String): String {
        var str = ""

        if (TextUtils.isEmpty(paramString)) {
            TLog.d(TAG, "paramString---->null")
            return str
        }
        TLog.d(TAG, "paramString:" + paramString)
        val i = paramString.lastIndexOf('.')
        if (i <= -1) {
            TLog.d(TAG, "i <= -1")
            return str
        }


        str = paramString.substring(i + 1)
        TLog.d(TAG, "paramString.substring(i + 1)------>" + str)
        return str
    }


}