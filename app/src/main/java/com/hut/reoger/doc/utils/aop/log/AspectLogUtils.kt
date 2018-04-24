@file:JvmName("Greeter")
package com.hut.reoger.doc.utils.aop.log


import android.util.Log


import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * Created by reoger on 2018/4/14.
 *
 */
@Aspect
class AspectLogUtils {

    @Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER) //可以注解在方法 上
    @Retention(RetentionPolicy.RUNTIME) //运行时（执行时）存在
    annotation class AspectLog(val value: String)

    @Pointcut("execution(@com.hut.reoger.doc.utils.aop.log.AspectLogUtils.AspectLog * *(..))")
    fun logForActivity() {

    }

    @Before("logForActivity()")
    fun doRealLog(joinPoint: JoinPoint): Any {
        try {
            val methodSignature = joinPoint.signature as MethodSignature
            val aspectJAnnotation = methodSignature.method.getAnnotation(AspectLog::class.java)
            val log = aspectJAnnotation?.value
            Log.d(TAG, "doRealLog: 打印日志$log")
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return true
    }

    companion object {
        internal val TAG = "TAG"
    }
}