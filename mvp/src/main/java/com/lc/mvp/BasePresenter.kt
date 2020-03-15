package com.lc.mvp

import android.content.Context
import java.lang.ref.WeakReference

/**
@packageName com.lc.mvp
@author admin
@date 2020/3/9
 */
open class BasePresenter< V : IBaseView> : IBasePresenter {

    var mView: V? = null
        get() {
            if (field == null)
                throw IllegalArgumentException("当前View没有加入") else return field
        }
    private lateinit var weakReference: WeakReference<V>

    fun attView(view: V) {
        view.let {
            weakReference = WeakReference(view)
            this.mView = weakReference.get()
        }
    }


    fun getContext(): Context = mView!!.getContext()


    fun detachView(view: V) {
        this.mView = view
    }

    fun isAttatch():Boolean = weakReference.get()!=null

    fun detachView() {
        if (weakReference != null) {
            weakReference.clear()
            mView = null
        }
    }
}