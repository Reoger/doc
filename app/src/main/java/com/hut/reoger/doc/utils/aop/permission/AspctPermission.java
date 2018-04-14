package com.hut.reoger.doc.utils.aop.permission;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Created by reoger on 2018/4/14.
 */
@Aspect
public class AspctPermission {

    private String TAG = "TAG";

    //寻找切片点
    @Pointcut("execution(@com.hut.reoger.doc.utils.aop.permission.AspectPermissionAnnotation  * *(..))")
    public void executionAspectJ() {
        Log.d(TAG, "executionAspectJ: 空方法的执行》》》");
    }

    //切面的处理逻辑
    @Around("executionAspectJ()")
    public Object realDoSomeThing(ProceedingJoinPoint joinPoint){
        try {
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            Log.i(TAG, "aroundAspectJ(ProceedingJoinPoint joinPoint)");
            AspectPermissionAnnotation aspectJAnnotation = methodSignature.getMethod().getAnnotation(AspectPermissionAnnotation.class);
            String permission = aspectJAnnotation.value();
            Context context = (Context) joinPoint.getThis();
            Object o = null;
            String result = "";
            if (PermissionManager.getInnerInstance().checkPermission(context, permission)) {
                o = joinPoint.proceed();
                Log.i(TAG, "有权限");
                result =  "有权限";
            } else {
                Log.i(TAG, "没有权限，不可以使用");
                result =  "没有权限，不可以使用";
            }
            Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
            return o;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }

    }
}
