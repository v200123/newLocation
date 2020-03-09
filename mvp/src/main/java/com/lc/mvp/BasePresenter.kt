package com.lc.mvp

import android.content.Context
import java.lang.ref.WeakReference

/**
@packageName com.lc.mvp
@author admin
@date 2020/3/9
 */
open class BasePresenter<in V : IBaseView> : IBasePresenter {

    private var mView: V? = null
        get() {
            if (field == null)
                throw IllegalArgumentException("当前View没有加入") else return field
        }
    private var weakReference: WeakReference<V>? =  null

    fun attView(view: V) {
        view.let {
            weakReference = WeakReference(view)
            this.mView = weakReference!!.get()
        }
    }

    fun getContex(): Context {
        return mView!!.getContext()
    }

    fun detachView(view: V) {
        this.mView = null
    }

    fun isAttatch():Boolean =weakReference != null && weakReference!!.get()!=null

    fun detachView() {
        if (weakReference != null) {
            weakReference!!.clear()
            weakReference = null
            mView = null
        }
    }
}