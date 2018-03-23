package com.hut.reoger.doc.utils.exception


import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.os.Process
import android.support.annotation.RequiresApi
import com.hut.reoger.doc.App
import com.hut.reoger.doc.bean.ServiceReply
import com.hut.reoger.doc.utils.log.TLog
import com.hut.reoger.doc.utils.netWork.ApiClient
import com.hut.reoger.doc.utils.netWork.ApiErrorModel
import com.hut.reoger.doc.utils.netWork.ApiResponse
import com.hut.reoger.doc.utils.netWork.NetworkScheduler
import com.hut.reoger.doc.utils.safe.VersionInfos
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.kotlin.bindUntilEvent
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*



/**
 * Created by reoger on 2018/3/2.
 */

//todo
class ExceptionHandler private constructor() : Thread.UncaughtExceptionHandler {
    private var defaultHandler: Thread.UncaughtExceptionHandler? = null
    private var saveSpacePath: File? = null
    private var localErrorSave: File? = null
    private var context: Context? = null
    private val sb = StringBuilder()

    fun initConfig(context: Context) {
        this.context = context
        saveSpacePath = File(Environment.getExternalStorageDirectory().absolutePath, "/007")
        localErrorSave = File(saveSpacePath, "error.txt")
        //假设上传了
        if (!saveSpacePath!!.exists()) {
            saveSpacePath!!.mkdirs()
        }
        if (!localErrorSave!!.exists()) {
            try {
                localErrorSave!!.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun uncaughtException(t: Thread, e: Throwable) {
        try {
            val a = writeErrorToLocal(t, e)
//            upLoadException(a)
            e.printStackTrace()
            Process.killProcess(Process.myPid())
        } catch (e: Exception) {
            e.printStackTrace()
        }

        if (instance == null) {
//            App.instance.showDialog()
            Process.killProcess(Process.myPid())
        }
    }


    /***
     * 上传异常信息到服务器
     * @param t1
     */
    private fun upLoadException(t1: String) {

        ApiClient.instance.service.feedError(1, VersionInfos.getDid(context!!), VersionInfos.getVersion(context!!), VersionInfos.getMinVersion(), t1)
                .compose(NetworkScheduler.compose())
                .subscribe(object : ApiResponse<ServiceReply>(context!!) {
                    override fun success(data: ServiceReply) {
                        if (data.code == 2000) {
                            TLog.d("YY", "上传数据成功$data")
                        } else {
                            TLog.d("YY", "测试失败$data")
                        }
                    }

                    override fun failure(statusCode: Int, apiErrorModel: ApiErrorModel) {
                        TLog.d("YY", "失败" + apiErrorModel.message)

                    }
                })

    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun writeErrorToLocal(t: Thread, e: Throwable): String {
        try {
            val fos = BufferedWriter(FileWriter(localErrorSave, true))
            val packageManager = context!!.packageManager
            val line = "\n----------------------------------------------------------------------------------------\n"
            sb.append(line)
            val packageInfo = packageManager.getPackageInfo(context!!.packageName, PackageManager.GET_ACTIVITIES)
            sb.append(SimpleDateFormat("yyyy-MM-dd").format(Date(System.currentTimeMillis())) + "<---->" +
                    "包名::" + packageInfo.packageName + "<---->版本名::" + packageInfo.versionName + "<---->版本号::" + packageInfo.versionCode + "\n")
            sb.append("手机制造商::" + Build.MANUFACTURER + "\n")
            sb.append("手机型号::" + Build.MODEL + "\n")
            sb.append("CPU架构::" + Build.CPU_ABI + "\n")
            sb.append(e.toString() + "\n")
            val trace = e.stackTrace
            for (traceElement in trace)
                sb.append("\n\tat $traceElement")
            sb.append("\n")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                val suppressed = e.suppressed
                for (se in suppressed)
                    sb.append("\tat " + se.message)
            }
            fos.write(sb.toString())
            fos.close()

            return sb.toString()

        } catch (e1: IOException) {
            e1.printStackTrace()
            defaultHandler!!.uncaughtException(t, e1)
        } catch (e1: PackageManager.NameNotFoundException) {
            e1.printStackTrace()
            defaultHandler!!.uncaughtException(t, e1)
        }
        return "can`n update"
    }

    companion object {
        val instance = ExceptionHandler()
    }
}