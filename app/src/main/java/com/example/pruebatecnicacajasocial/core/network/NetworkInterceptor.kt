package com.example.pruebatecnicacajasocial.core.network

import okhttp3.Interceptor
import okhttp3.Response

class NetworkInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        original.url
        original.body
        return chain.proceed(original)
    }
}