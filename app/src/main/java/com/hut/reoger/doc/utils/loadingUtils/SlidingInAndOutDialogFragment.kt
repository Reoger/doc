package com.hut.reoger.doc.utils.loadingUtils

import android.app.DialogFragment
import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.Button
import com.hut.reoger.doc.R


/**
 * Created by reoger on 2018/3/21.
 */
class SlidingInAndOutDialogFragment : DialogFragment() {
    private var button: Button? = null

    override fun onStart() {
        super.onStart()

        // safety check
        if (dialog == null) {
            return
        }

        // set the animations to use on showing and hiding the dialog
        dialog.window.setWindowAnimations(
                R.style.DialogAnimation)
        // alternative way of doing it
        //getDialog().getWindow().getAttributes().
        //    windowAnimations = R.style.dialog_animation_fade;

        // ... other stuff you want to do in your onStart() method
        //	  setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo);
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup,
                              savedInstanceState: Bundle): View {
        dialog.window.requestFeature(Window.FEATURE_NO_TITLE)
        val v = inflater.inflate(R.layout.custom_dialog, null) as View

        button =   v.findViewById(R.id.button1)

//        button?.setOnClickListener({
//            view->when(view.id){
//            R.id.button1->{
//                TLog.d("this had click the button ~")
//            }
//        }
//        })
        return v
    }
}