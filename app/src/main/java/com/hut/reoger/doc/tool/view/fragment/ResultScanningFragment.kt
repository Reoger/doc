package com.hut.reoger.doc.tool.view.fragment

import android.view.View
import com.hut.reoger.doc.R
import com.hut.reoger.doc.base.BaseFragment
import com.mylhyl.zxing.scanner.ScannerView

/**
 * Created by reoger on 2018/4/18.
 */
class ResultScanningFragment : BaseFragment() {

    companion object {
        fun getInstance(): ResultScanningFragment {
            return InnerClass.INSTANCE
        }
    }

    object InnerClass {
        val INSTANCE = ResultScanningFragment()
    }

    private var mScannerView: ScannerView? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_sacnning
    }

    override fun initView(mRootView: View) {
        mScannerView = mRootView.findViewById(R.id.scanner_view)

    }

    override fun onResume() {
        mScannerView?.onResume()
        super.onResume()
    }

    override fun onPause() {
        mScannerView?.onPause()
        super.onPause()
    }

}