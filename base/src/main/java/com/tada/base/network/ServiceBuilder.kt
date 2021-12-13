package com.tada.base.network

import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

open class ServiceBuilder {

    // retrofit config
    private var callAdapterFactories: MutableList<CallAdapter.Factory> = arrayListOf()
    private var converterFactories: MutableList<Converter.Factory> = arrayListOf()
    private val headers: MutableList<Header> = ArrayList()

    private val interceptors: MutableList<Interceptor> = ArrayList()

    private var authenticator: Authenticator? = null
    private var clientBuilder: OkHttpClient.Builder? = null
    private var connectTimeout: Pair<Long, TimeUnit>? = null

    fun addCallAdapterFactories(callAdapterFactory: CallAdapter.Factory): ServiceBuilder {
        this.callAdapterFactories.add(callAdapterFactory)
        return this
    }

    fun addConverterFactories(converterFactory: Converter.Factory): ServiceBuilder {
        this.converterFactories.add(converterFactory)
        return this
    }

    fun addHeader(header: Header): ServiceBuilder {
        headers.add(header)
        return this
    }

    fun setAuthenticator(authenticator: Authenticator): ServiceBuilder {
        this.authenticator = authenticator
        return this
    }

    fun addInterceptor(interceptor: Interceptor): ServiceBuilder {
        interceptors.add(interceptor)
        return this
    }

    fun <T> build(clazz: Class<T>, endpoint: String): T {
        val clientBuilder = clientBuilder ?: OkHttpClient.Builder().apply {
            connectTimeout?.let { connectTimeout(it.first, it.second) }
        }

        /**
         * Header Config with an interceptor
         */
        clientBuilder.addInterceptor { chain ->
            val original = chain.request()
            val builder = original.newBuilder()

            for (header: Header in headers) {
                header.provideValue()?.let { builder.addHeader(header.key, it) }
            }

            builder.method(original.method, original.body)
            chain.proceed(builder.build())
        }


        /**
         *  Authenticator config
         */
        authenticator?.let { clientBuilder.authenticator(it) }

        /**
         * Interceptor Config
         */
        interceptors.forEach {
            clientBuilder.addInterceptor(it)
        }

        /**
         * Retrofit Config
         */
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(endpoint)
            .client(clientBuilder.build())

        for (callAdapterFactory in callAdapterFactories) {
            retrofitBuilder.addCallAdapterFactory(callAdapterFactory)
        }

        for (converterFactory in converterFactories) {
            retrofitBuilder.addConverterFactory(converterFactory)
        }

        return retrofitBuilder.build().create(clazz)
    }

    abstract class Header constructor(val key: String) {
        abstract fun provideValue(): String?
    }

    abstract class AccessTokenHeader : Header("Authorization")
}