package de.hypercdn.extensions.okhttpktx.proxy.utils

import de.hypercdn.extensions.okhttpktx.proxy.provider.FeedbackAwareProxyProvider
import okhttp3.Interceptor
import okhttp3.Response
import java.net.Proxy

open class FeedbackAwareProxyInterceptor (
    val feedbackAwareProxyProvider: FeedbackAwareProxyProvider,
    val usedProxy: Proxy,
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = try {
            chain.proceed(chain.request())
        } catch (t: Throwable) {
            feedbackAwareProxyProvider.handleFailure(usedProxy, t)
            throw t
        }
        feedbackAwareProxyProvider.handleResponse(usedProxy, response)
        return response
    }
}