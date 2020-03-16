package com.lc.mvp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.kongzue.dialog.v3.WaitDialog

/**
@packageName com.lc.mvp
@author admin
@date 2020/3/10
 */
@Suppress("UNCHECKED_CAST")
abstract class BaseFragment<V : IFragmentView, P : BasePresenter<V>> : Fragment(), IFragmentView {
    private lateinit var context: Context
    protected lateinit var mPresenter: P
    private var rootView: View? = null
    override fun getContext(): Context = context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (rootView == null)
            rootView = inflater.inflate(getLayoutId(), null, false)
        // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        val parent = rootView!!.parent as ViewGroup?
        parent?.removeView(rootView)
        mPresenter = createPresenter()
        mPresenter.attView(this as V)
        initData()
        return rootView
    }

    override fun showLoading() {
        WaitDialog.show(context as AppCompatActivity, "").setTipTime(2000)
    }

    override fun hideLoading() {
        WaitDialog.dismiss()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context
    }

    abstract fun createPresenter(): P

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.detachView()
    }

}