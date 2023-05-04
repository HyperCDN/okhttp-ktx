package de.hypercdn.extensions.okhttpktx.proxy.manager

import de.hypercdn.extensions.okhttpktx.proxy.provider.ProxyProvider
import de.hypercdn.extensions.okhttpktx.proxy.withProxy
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import org.slf4j.Logger
import org.slf4j.LoggerFactory

open class SimpleProxyCallManager(
    val okHttpClient: OkHttpClient,
    val proxyProvider: ProxyProvider
) : ProxyCallManager {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    override fun newCall(request: Request): Call {
        val proxy = proxyProvider.next()
        log.debug("New call using proxy {}", proxy)
        return okHttpClient
            .withProxy(proxy)
            .newCall(request)
    }

}