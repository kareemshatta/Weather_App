package com.example.weather_app.ui.home

import com.example.weather_app.base.BasePresenter
import com.example.weather_app.base.DataResult
import com.example.weather_app.domain.inputs.GetWeatherDetailsInput
import com.example.weather_app.domain.interactors.AppInteractor
import com.example.weather_app.models.CityDetails
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class HomeActivityPresenter(private val appInteractor: AppInteractor) :
    BasePresenter<HomeActivityContract.View>(),
    HomeActivityContract.Presenter {

    override fun getLocalCityDetails(): CityDetails {
        return appInteractor.getLocalCityDetails()
    }

    override fun getLocalWeatherDetails(cityDetails: CityDetails) {
        view?.showLoading()
        compositeDisposable.add(
            appInteractor.getLocalWeatherDetails(cityDetails)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onNext = {
                        view?.hideLoading()
                        when (it) {
                            is DataResult.Success -> {
                                view?.onGetWeatherDetailsSuccess(it.dataResponse)
                            }
                            is DataResult.Failure -> {
                                view?.onGetLocalWeatherDetailsFailed(cityDetails)
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

    override fun getWeatherDetails(getWeatherDetailsInput: GetWeatherDetailsInput) {
        view?.showLoading()
        compositeDisposable.add(
            appInteractor.getWeatherDetails(getWeatherDetailsInput)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onNext = {
                        view?.hideLoading()
                        when (it) {
                            is DataResult.Success -> {
                                view?.onGetWeatherDetailsSuccess(it.dataResponse)
                            }
                            is DataResult.Failure -> {
                                view?.onGetRemoteWeatherDetailsFailed(it.error)
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

    override fun getAllCities() {
        view?.showLoading()
        compositeDisposable.add(
            appInteractor.getAllCities()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onNext = {
                        view?.hideLoading()
                        when (it) {
                            is DataResult.Success -> {
                                view?.onGetAllCitiesSuccess(it.dataResponse)
                            }
                            is DataResult.Failure -> {
                                view?.onGetAllCitiesFailed(it.error)
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

    override fun deleteWeatherDetails(cityId: Int) {
        appInteractor.deleteWeatherDetails(cityId)
    }
}