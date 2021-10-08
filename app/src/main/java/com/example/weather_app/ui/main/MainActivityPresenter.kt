package com.example.weather_app.ui.main

import com.example.weather_app.base.BasePresenter
import com.example.weather_app.base.DataResult
import com.example.weather_app.domain.inputs.GetCityDetailsInput
import com.example.weather_app.domain.interactors.AppInteractor
import com.example.weather_app.models.CityDetails
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class MainActivityPresenter(private val appInteractor: AppInteractor) :
    BasePresenter<MainActivityContract.View>(),
    MainActivityContract.Presenter {

    override fun saveCityDetails(cityDetails: CityDetails) {
        appInteractor.saveCityDetails(cityDetails)
    }

    override fun getCityData(getCityDetailsInput: GetCityDetailsInput) {
        view?.showLoading()
        compositeDisposable.add(
            appInteractor.getCityData(getCityDetailsInput)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onNext = {
                        view?.hideLoading()
                        when (it) {
                            is DataResult.Success -> {
                                view?.onGetCityDataSuccess(it.dataResponse)
                            }
                            is DataResult.Failure -> {
                                view?.showMessage(it.error)
                            }
                        }
                    },
                    onError = {
                        view?.hideLoading()
                        view?.onNetworkError()
                    }
                )
        )
    }
}