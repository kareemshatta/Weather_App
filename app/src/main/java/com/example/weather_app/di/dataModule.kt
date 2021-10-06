package com.example.weather_app.di

import androidx.room.Room
import com.example.weather_app.Constants.APP_ID
import com.example.weather_app.data.db.DbApiHelper
import com.example.weather_app.data.db.DbApiHelperImpl
import com.example.weather_app.data.db.data_base.AppDatabase
import com.example.weather_app.data.pref.PrefHelper
import com.example.weather_app.data.pref.PrefHelperImpl
import com.example.weather_app.data.remote.ApiHelper
import com.example.weather_app.data.remote.ApiHelperImpl
import com.example.weather_app.data.remote.RetrofitApiService
import com.example.weather_app.data.resource_helper.ResourceHelper
import com.example.weather_app.data.resource_helper.ResourceHelperImpl
import com.example.weather_app.Constants.BASE_URL
import okhttp3.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val dataModule = module {
    single(named("retrofitClient")) {
        val client = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
        client.addInterceptor { chain ->
            val request = chain.request().newBuilder()
            request.addHeader("app_id", APP_ID).build()
            chain.proceed(request.build())
        }.build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client.build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    single<PrefHelper> {
        PrefHelperImpl(androidContext())
    }
    single<RetrofitApiService> {
        get<Retrofit>(named("retrofitClient")).create(RetrofitApiService::class.java)
    }

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "weather_app_db"
        ).allowMainThreadQueries().build()
    }

    single {
        get<AppDatabase>().repoDao
    }

    single<DbApiHelper> {
        DbApiHelperImpl(get())
    }
    single<ApiHelper> {
        ApiHelperImpl(get())
    }

    single<ResourceHelper> {
        ResourceHelperImpl(androidContext())
    }

}
