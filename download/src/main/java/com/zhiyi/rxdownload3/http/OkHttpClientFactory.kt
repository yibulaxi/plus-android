package com.zhiyi.rxdownload3.http

import okhttp3.OkHttpClient

interface OkHttpClientFactory {
    fun build(): OkHttpClient
}