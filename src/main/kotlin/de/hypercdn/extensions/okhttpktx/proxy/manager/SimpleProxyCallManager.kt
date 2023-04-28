package de.hypercdn.extensions.okhttpktx.proxy.manager

import de.hypercdn.extensions.okhttpktx.proxy.newProxy
import de.hypercdn.extensions.okhttpktx.proxy.provider.ProxyProvider
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request

open class SimpleProxyCallManager(
    val okHttpClient: OkHttpClient,
    val proxyProvider: ProxyProvider
) : ProxyCallManager {

    override fun newCall(request: Request): Call {
        return okHttpClient
            .newProxy(proxyProvider.next())
            .newCall(request)
    }

}