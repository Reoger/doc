package com.hut.reoger.doc.utils.netWork

import android.content.Context
import android.support.annotation.StringRes
import com.hut.reoger.doc.R

/**
 * Created by CM on 2018/1/25.
 */
enum class ApiErrorType(val code: Int, @param: StringRes private val messageId: Int) {
    //根据实际情况进行增删
    INTERNAL_SERVER_ERROR(500, R.string.service_error),
    BAD_GATEWAY(502, R.string.service_error),
    NOT_FOUND(404, R.string.not_found),
    CONNECTION_TIMEOUT(408, R.string.timeout),
    NETWORK_NOT_CONNECT(499, R.string.network_wrong),
    UNEXPECTED_ERROR(700, R.string.unexpected_error);

    private val DEFAULT_CODE = 1

    fun getApiErrorModel(context: Context): ApiErrorModel {
        return ApiErrorModel(DEFAULT_CODE, context.getString(messageId))
    }
}
