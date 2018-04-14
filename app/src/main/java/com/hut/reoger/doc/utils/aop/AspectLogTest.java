//package com.hut.reoger.doc.utils.aop;
//
///**
// * Created by reoger on 2018/4/14.
// */
//
//import android.util.Log;
//
//
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.aspectj.lang.reflect.MethodSignature;
//
//import java.lang.annotation.ElementType;
//import java.lang.annotation.Retention;
//import java.lang.annotation.RetentionPolicy;
//import java.lang.annotation.Target;
//
//
///**
// * Created by CM on 2018/1/18.
// *
// */
//@Aspect
//public class AspectLogTest {
//
//    @Target(ElementType.METHOD) //可以注解在方法 上
//    @Retention(RetentionPolicy.RUNTIME) //运行时（执行时）存在
//    public @interface AspectLog {
//        String value();
//    }
//
//    final static String TAG = "TAGX";
//
//    @Pointcut("execution(@com.hut.reoger.doc.utils.aop.AspectLogTest.AspectLog * *(..))")
//    public void logForActivity(){
//
//    }
//
//    @Before("logForActivity()")
//    public Object doRealLog(JoinPoint joinPoint){
//        try {
//            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
//            AspectLog aspectJAnnotation = methodSignature.getMethod().getAnnotation(AspectLog.class);
//            String log = aspectJAnnotation.value();
//            Log.d(TAG, "doRealLog: 打印日志"+log);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return true;
//    }
//}