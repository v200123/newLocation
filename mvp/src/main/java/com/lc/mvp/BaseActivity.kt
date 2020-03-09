package com.lc.mvp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.qmuiteam.qmui.widget.dialog.QMUIDialog
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog

/**
@packageName com.lc.mvp
@author admin
@date 2020/3/9
 */
abstract class BaseActivity<in V :IBaseView,out P :BasePresenter<V>> :AppCompatActivity(),IBaseView {
    private var mLoading: QMUIDialog? = null
    private  var mPresenter: P? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showLoading()
        setContentView(getLayoutId())
        if (mPresenter == null) {
            mPresenter = createPresenter()
        }
        mPresenter?.attView(this as V)

    }

    override fun onResume() {
        super.onResume()
        hideLoading()
    }

    protected abstract fun createPresenter(): P?
    override fun showLoading() {
        if (mLoading == null) {
            mLoading =QMUIDialog(getContext())
        }
        mLoading!!.show()
    }

    override fun hideLoading() {
        mLoading!!.hide()
    }
}