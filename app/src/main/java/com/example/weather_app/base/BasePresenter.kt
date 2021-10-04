package com.example.weather_app.base

import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter<V> : MvpPresenter<V>{
    var view: V? = null
    var compositeDisposable = CompositeDisposable()

    override fun attachView(view: V) {
        this.view = view
        if (compositeDisposable.isDisposed) {
            compositeDisposable = CompositeDisposable()
        }
    }

    override fun deAttachView() {
        this.view = null
        compositeDisposable.dispose()
    }


}