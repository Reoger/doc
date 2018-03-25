package com.hut.reoger.doc.utils.log

import android.util.Log
import com.hut.reoger.doc.BuildConfig




/**
 * Created by reoger on 2018/3/25.
 * 封装的日志类，来源：https://blog.csdn.net/jdsjlzx/article/details/51500292
 */
object LogUtils {
     var className: String ?=null//类名
     var methodName: String ?=null//方法名
     var lineNumber: Int = 0//行数

    val isDebuggable: Boolean
        get() = BuildConfig.DEBUG

    private fun createLog(log: String): String {
        val buffer = StringBuffer()
        buffer.append(methodName)
        buffer.append("(").append(className).append(":").append(lineNumber).append(")")
        buffer.append(log)
        return buffer.toString()
    }

    private fun getMethodNames(sElements: Array<StackTraceElement>) {
        className = sElements[1].fileName
        methodName = sElements[1].methodName
        lineNumber = sElements[1].lineNumber
    }


    fun e(message: String) {
        if (!isDebuggable)
            return

        // Throwable instance must be created before any methods
        getMethodNames(Throwable().stackTrace)
        Log.e(className, createLog(message))
    }


    fun i(message: String) {
        if (!isDebuggable)
            return

        getMethodNames(Throwable().stackTrace)
        Log.i(className, createLog(message))
    }

    fun d(message: String) {
        if (!isDebuggable)
            return

        getMethodNames(Throwable().stackTrace)
        Log.d(className, createLog(message))
    }

    fun v(message: String) {
        if (!isDebuggable)
            return

        getMethodNames(Throwable().stackTrace)
        Log.v(className, createLog(message))
    }

    fun w(message: String) {
        if (!isDebuggable)
            return

        getMethodNames(Throwable().stackTrace)
        Log.w(className, createLog(message))
    }

    fun wtf(message: String) {
        if (!isDebuggable)
            return

        getMethodNames(Throwable().stackTrace)
        Log.wtf(className, createLog(message))
    }

}/* Protect from instantiations */