package com.aungbophyoe.core

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.kaopiz.kprogresshud.KProgressHUD

abstract class ViewBindingActivity<VB : ViewDataBinding> : AppCompatActivity() {

    private lateinit var dialog: KProgressHUD

    @get:LayoutRes
    abstract val layoutRes: Int
    //
    lateinit var viewBinding: VB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, layoutRes)
        initProgressDialog()
    }

    @Throws(Exception::class)
    fun initProgressDialog() {
        dialog = KProgressHUD.create(this)
            .setWindowColor(ContextCompat.getColor(this,R.color.purple_200))
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setCancellable(false)
            .setAnimationSpeed(1)
            .setDimAmount(0.3f)
    }

    @Throws(Exception::class)
    fun showProgressDialog() {
        synchronized(this) {
            if (!dialog.isShowing) {
                dialog.show()
            }
        }
    }


    @Throws(Exception::class)
    fun dismissProgressDialog() {
        synchronized(this) {
            if (dialog.isShowing) {
                dialog.dismiss()
            }
        }
    }

}