package com.hut.reoger.doc.utils.aop.testAspect



/**
 * Created by reoger on 2018/4/14.
 */
class JustTest{
    @Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION,
            AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.EXPRESSION)
    @Retention(AnnotationRetention.SOURCE)
    @MustBeDocumented
    annotation class Fancy

}