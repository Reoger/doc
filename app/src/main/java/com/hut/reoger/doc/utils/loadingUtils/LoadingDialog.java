package com.hut.reoger.doc.utils.loadingUtils;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hut.reoger.doc.R;


/**
 * Created by CM on 2018/1/24.
 *
 */

public class LoadingDialog {

    LVCircularRing mLoadingView;
    Dialog mLoadingDialog;
    private final TextView loadingText;

    public LoadingDialog(Context context, String msg) {
        // 首先得到整个View
        View view = LayoutInflater.from(context).inflate(
                R.layout.loading_dialog_view, null);
        // 获取整个布局
        LinearLayout layout = view.findViewById(R.id.dialog_view);
        // 页面中的LoadingView
        mLoadingView = view.findViewById(R.id.lv_circularring);
        // 页面中显示文本
        loadingText = view.findViewById(R.id.loading_text);
        // 显示文本
        loadingText.setText(msg);
        // 创建自定义样式的Dialog
//        mLoadingDialog = new Dialog(context, R.style.loading_dialog);
        mLoadingDialog = new Dialog(context, R.style.loading_dialog);
        // 设置返回键无效
        mLoadingDialog.setCancelable(false);
        mLoadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
    }

    public void show(){
        mLoadingDialog.show();
        Log.d("debug","xianshi进度条的实际操作");
        mLoadingView.startAnim();
    }

//    public void loadFial(){
//        loadingText.setText("加载失败");
//    }

    public void close(){
        if (mLoadingDialog!=null) {
            Log.d("debug","取消进度条的实际操作");
            mLoadingView.stopAnim();
            mLoadingDialog.dismiss();
            mLoadingDialog=null;
        }
    }
}
