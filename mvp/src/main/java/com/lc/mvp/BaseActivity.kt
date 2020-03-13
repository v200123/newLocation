package com.lc.mvp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.qmuiteam.qmui.widget.dialog.QMUIDialog

/**
@packageName com.lc.mvp
@author admin
@date 2020/3/9
 */
abstract class BaseActivity<out V : IBaseView, out P : BasePresenter< V>> : AppCompatActivity(),
    IBaseView {
    private var mLoading: QMUIDialog? = null
    private var mPresenter: P? = null
    private val Fragments = listOf<Fragment>()

    @Suppress("UNCHECKED_CAST")
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
            mLoading = QMUIDialog(getContext())
        }
        mLoading!!.show()
    }

    override fun hideLoading() {
        mLoading!!.hide()
    }

    fun showFragment(fragment: Fragment,containerId : Int):Int =
        supportFragmentManager.run {
            val show = beginTransaction()
            if (fragments.contains(fragment))
                fragments.forEach { if (it != fragment) show.hide(it)else{
                    show.show(it)
                 }
            }else {
                show.add(containerId,fragment)
            }
            show.commit()
        }


}