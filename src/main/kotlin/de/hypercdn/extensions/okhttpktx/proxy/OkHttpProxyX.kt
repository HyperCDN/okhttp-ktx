package de.hypercdn.extensions.okhttpktx.proxy

import de.hypercdn.extensions.okhttpktx.proxy.utils.HttpProxyAuthenticator
import de.hypercdn.extensions.okhttpktx.proxy.utils.SocksSocketFactory
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import java.net.Proxy

var PROXY_BUILDER_DEFAULT = fun(base: OkHttpClient.Builder?): OkHttpClient.Builder {
    return (base ?: OkHttpClient.Builder())
        .retryOnConnectionFailure(false)
        .connectionPool(ConnectionPool())
}

fun OkHttpClient.newProxy(proxy: Proxy?, allowDefaultFallback: Boolean = true): OkHttpClient {
    if (proxy == null) {
        if (allowDefaultFallback)
            return PROXY_BUILDER_DEFAULT(this.newBuilder()).build()
        throw IllegalArgumentException("No proxy configuration provided")
    }
    return when(proxy.type()) {
        Proxy.Type.DIRECT -> newDirectProxy(proxy)
        Proxy.Type.HTTP -> newHttpProxy(proxy)
        Proxy.Type.SOCKS -> newSocksProxy(proxy)
        else -> throw IllegalArgumentException("Proxy type is null")
    }
}

fun OkHttpClient.newSocksProxy(proxy: Proxy): OkHttpClient {
    if (proxy.type() != Proxy.Type.SOCKS)
        throw IllegalArgumentException("Proxy not of type SOCKS")
    return PROXY_BUILDER_DEFAULT(this.newBuilder())
        .socketFactory(SocksSocketFactory(proxy))
        .build()
}

fun OkHttpClient.newHttpProxy(proxy: Proxy): OkHttpClient {
    if (proxy.type() != Proxy.Type.HTTP)
        throw IllegalArgumentException("Proxy not of type HTTP")
    return PROXY_BUILDER_DEFAULT(this.newBuilder())
        .proxy(proxy)
        .proxyAuthenticator(HttpProxyAuthenticator(proxy))
        .build()
}

fun OkHttpClient.newDirectProxy(proxy: Proxy): OkHttpClient {
    if (proxy.type() != Proxy.Type.DIRECT)
        throw IllegalArgumentException("Proxy not of type DIRECT")
    return PROXY_BUILDER_DEFAULT(this.newBuilder())
        .proxy(proxy)
        .build()
}
