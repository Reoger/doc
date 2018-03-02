package com.hut.reoger.doc.read

import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import com.example.cm.mytestdemo.base.BaseActivity
import com.hut.reoger.doc.R
import com.hut.reoger.doc.utils.log.TLog
import com.hut.reoger.doc.utils.netWork.LoadFileModel
import com.hut.reoger.doc.utils.safe.Md5Tool
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

/**
 * Created by reoger on 2018/3/2.
 */


class DocumentReaderActivity : BaseActivity() {
    internal var mSuperFileView: SuperFileView2? = null


    //    String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/test.docx";
    internal var filePath = "http://www.hrssgz.gov.cn/bgxz/sydwrybgxz/201101/P020110110748901718161.doc"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_doc)
        TLog.d("TAG", "filePath = --->" + filePath)
        init()
    }


    override fun setActionBar() {
       setActivityTitle("在线阅读界面")
    }

    override fun initView() {

    }


    private fun init() {
        mSuperFileView = findViewById(R.id.mSuperFileView)
        mSuperFileView!!.setOnGetFilePathListener(object : SuperFileView2.OnGetFilePathListener {
            override fun onGetFilePath(mSuperFileView2: SuperFileView2) {
                getFilePathAndShowFile(mSuperFileView2)
            }
        })

        mSuperFileView!!.show()

    }


    private fun getFilePathAndShowFile(mSuperFileView2: SuperFileView2) {


        if (filePath.contains("http")) {//网络地址要先下载

            downLoadFromNet(filePath, mSuperFileView2)

        } else {
            mSuperFileView2.displayFile(File(filePath))
        }
    }


    public override fun onDestroy() {
        super.onDestroy()
        TLog.d("FileDisplayActivity-->onDestroy")
        if (mSuperFileView != null) {
            mSuperFileView!!.onStopDisplay()
        }
    }


    private fun downLoadFromNet(url: String, mSuperFileView2: SuperFileView2) {

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


                    //fileN : /storage/emulated/0/pdf/kauibao20170821040512.pdf
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
                        TLog.d(TAG, "写入缓存文件" + fileN.name + "进度: " + progress)
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
                    TLog.d(TAG, "删除下载失败文件")
                    file.delete()
                }
            }
        })


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
