package com.aungbophyoe.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.kaopiz.kprogresshud.KProgressHUD

abstract class ViewBindingFragment<VB : ViewDataBinding> : Fragment() {

    @get:LayoutRes
    abstract val layoutRes: Int
    lateinit var viewBinding: VB

    private lateinit var dialog: KProgressHUD

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initProgressDialog()
    }

    @Throws(Exception::class)
    fun initProgressDialog() {
        dialog = KProgressHUD.create(requireActivity())
            .setWindowColor(ContextCompat.getColor(requireContext(),R.color.purple_200))
            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
            .setCancellable(false)
            .setAnimationSpeed(1)
            .setDimAmount(0.3f)
    }
    @Throws(Exception::class)
    fun showProgressDialog() {
        synchronized(requireContext()) {
            if (!dialog.isShowing) {
                dialog.show()
            }
        }
    }

    @Throws(Exception::class)
    fun dismissProgressDialog() {
        synchronized(requireContext()) {
            if (dialog.isShowing) {
                dialog.dismiss()
            }
        }
    }
}