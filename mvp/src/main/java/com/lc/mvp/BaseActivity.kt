package com.lc.mvp

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.kongzue.dialog.v3.WaitDialog

/**
@packageName com.lc.mvp
@author admin
@date 2020/3/9
 */
abstract class BaseActivity<V : IBaseView, P : BasePresenter< V>> : AppCompatActivity(), IBaseView {
    //    private var mLoading: QMUIDialog? = null
    private var mPresenter: P? = null
    private val Fragments = listOf<Fragment>()

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
        WaitDialog.show(getContext() as AppCompatActivity, "").setTipTime(2000)
    }

    override fun hideLoading() {
        WaitDialog.dismiss()
    }

    fun showFragment(fragment: Fragment, containerId: Int): Int =
        supportFragmentManager.run {
            val show = beginTransaction()
            if (fragments.contains(fragment))
                fragments.forEach {
                    if (it != fragment) show.hide(it) else {
                        show.show(it)
                    }
                } else {
                show.add(containerId, fragment)
            }
            show.commit()
        }

}
