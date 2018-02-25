package com.example.cm.mytestdemo.utils.loadingUtils

import android.app.Dialog
import android.content.Context
import com.hut.reoger.doc.R

/**
 * Created by CM on 2018/1/25.
 */
object LoadDialog {
    private var dialog: Dialog? = null

    fun show(context: Context) {
        cancel()
        dialog = Dialog(context, R.style.progress_dialog)
        dialog?.setContentView(R.layout.dialog)
        dialog?.setCancelable(false)
        dialog!!.window.setBackgroundDrawableResource(android.R.color.transparent)
        dialog?.setCanceledOnTouchOutside(false)
        dialog?.show()
    }

    fun cancel() {
        dialog?.dismiss()
    }
}