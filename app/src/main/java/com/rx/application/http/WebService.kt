package com.rx.application.http

import com.rx.application.BuildConfig
import com.rx.application.http.Api.Companion.BASE_URL
import com.rx.application.http.Config.Companion.CONNECT_TIMEOUT
import com.rx.application.http.Config.Companion.READ_TIMEOUT
import com.rx.application.http.core.ApiManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class WebService {
    companion object{
        @Volatile
        private var instance: Api? = null

        @Synchronized
        fun getInstance(): Api?{
            if (instance == null){
                val builder = OkHttpClient().newBuilder()
                    .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                    .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)

                builder.addInterceptor { chain: Interceptor.Chain ->
                    val request = chain.request().newBuilder()
                        .build()
                    chain.proceed(request)
                }

                if (BuildConfig.DEBUG) {
                    val interceptor = HttpLoggingInterceptor()
                    interceptor.level = HttpLoggingInterceptor.Level.BODY
                    builder.addInterceptor(interceptor)
                }

                val retrofit = Retrofit.Builder()
                    .client(builder.build())
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build()

                instance = retrofit.create(Api::class.java)
            }
            return instance
        }
    }

    fun cancelTag(tag: Any?) {
        ApiManager.getInstance()?.cancel(tag)
    }

    fun cancelAll() {
        ApiManager.getInstance()?.cancelAll()
    }
}