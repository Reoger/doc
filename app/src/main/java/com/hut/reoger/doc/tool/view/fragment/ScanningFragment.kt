package com.hut.reoger.doc.tool.view.fragment

import android.os.Bundle
import android.view.View
import com.example.cm.mytestdemo.utils.loadingUtils.LoadDialog
import com.google.zxing.client.result.ParsedResultType
import com.hut.reoger.doc.R
import com.hut.reoger.doc.base.BaseFragment
import com.hut.reoger.doc.utils.log.LogUtils
import com.mylhyl.zxing.scanner.ScannerView


/**
 * Created by reoger on 2018/4/18.
 */
class ScanningFragment : BaseFragment() {

    companion object {
        fun getInstance(): ScanningFragment {
            return InnerClass.INSTANCE
        }
    }

    object InnerClass {
        val INSTANCE = ScanningFragment()
    }

    private var mScannerView: ScannerView? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_sacnning
    }

    override fun initView(mRootView: View) {
        mScannerView = mRootView.findViewById(R.id.scanner_view)

        mScannerView?.setOnScannerCompletionListener({ rawResult, parsedResult, barcode ->
            LoadDialog.show(activity)
            val type = parsedResult.type
            LogUtils.d("打印一下 $type  --> $parsedResult  000 ${rawResult.text}")
            when(type){
                ParsedResultType.TEXT->{
                    startResultFragment(rawResult.text)
                }
            }
            LoadDialog.cancel()
        })
    }

    override fun onResume() {
        mScannerView?.onResume()
        super.onResume()
    }

    override fun onPause() {
        mScannerView?.onPause()
        super.onPause()
    }

    fun startResultFragment(str:String){
        val fragment = ResultScanningFragment()
        val bundle = Bundle()
        bundle.putString("key",str)
        fragment.arguments = bundle
        fragmentManager.beginTransaction().replace(R.id.fragment_main,fragment).addToBackStack(null).commit()

    }



}