package de.hypercdn.extensions.okhttpktx.proxy.provider

import okhttp3.Response
import java.net.Proxy

interface FeedbackAwareProxyProvider : ProxyProvider {

    fun handleFailure(proxy: Proxy?, throwable: Throwable?)

    fun handleResponse(proxy: Proxy?, response: Response?)

}