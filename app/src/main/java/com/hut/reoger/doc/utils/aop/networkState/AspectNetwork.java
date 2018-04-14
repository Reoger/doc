package com.hut.reoger.doc.utils.aop.networkState;

import android.content.Context;
import android.util.Log;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Created by reoger on 2018/4/14.
 */

@Aspect
public class AspectNetwork {

    private String TAG = "TAG";


    @Pointcut("execution(@com.hut.reoger.doc.utils.aop.networkState.AspectNetworkAnnotation  * *(..))")
    private void apsectCheckNet(){
    }

    @Around("apsectCheckNet()")
    private Object realDoCheckNetByApsect(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Log.d(TAG, "正在检查 网络状态: ");
        AspectNetworkAnnotation aspectJAnnotation = methodSignature.getMethod().getAnnotation(AspectNetworkAnnotation.class);

        Context context = (Context) joinPoint.getThis();
        if(NetworkManager.isNetworkConnected(context)){
            return joinPoint.proceed();
        }else{
            Log.d(TAG, "realDoCheckNetByApsect: 网络状态不行！！");
            return false;
        }

    }

}