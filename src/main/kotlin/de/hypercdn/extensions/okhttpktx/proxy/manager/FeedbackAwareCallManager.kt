package de.hypercdn.extensions.okhttpktx.proxy.manager

import de.hypercdn.extensions.okhttpktx.proxy.newProxy
import de.hypercdn.extensions.okhttpktx.proxy.provider.FeedbackAwareProxyProvider
import de.hypercdn.extensions.okhttpktx.proxy.utils.FeedbackAwareProxyInterceptor
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request

open class FeedbackAwareCallManager(
    val okHttpClient: OkHttpClient,
    val feedbackAwareProxyProvider: FeedbackAwareProxyProvider
) : ProxyCallManager {
    override fun newCall(request: Request): Call {
        val proxy = feedbackAwareProxyProvider.next()
        return okHttpClient
            .newProxy(proxy)
            .newBuilder()
            .addInterceptor(FeedbackAwareProxyInterceptor(feedbackAwareProxyProvider, proxy))
            .build()
            .newCall(request)
    }
}