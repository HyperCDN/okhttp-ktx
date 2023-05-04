package de.hypercdn.extensions.okhttpktx.proxy

import de.hypercdn.extensions.okhttpktx.proxy.utils.HttpProxyAuthenticator
import de.hypercdn.extensions.okhttpktx.proxy.utils.SocksSocketFactory
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import java.net.Proxy
import java.util.*

var PROXY_BUILDER_DEFAULT = fun(base: OkHttpClient.Builder?): OkHttpClient.Builder {
    return (base ?: OkHttpClient.Builder()).apply {
        retryOnConnectionFailure(false)
        connectionPool(ConnectionPool())
    }
}

fun OkHttpClient.withProxy(proxy: Proxy?, allowDefaultFallback: Boolean = true): OkHttpClient {
    if (proxy == null) {
        if (allowDefaultFallback)
            return directProxy()
        throw IllegalArgumentException("No proxy configuration provided")
    }
    return when (proxy.type()) {
        Proxy.Type.DIRECT -> directProxy(proxy)
        Proxy.Type.HTTP -> httpProxy(proxy)
        Proxy.Type.SOCKS -> socksProxy(proxy)
        else -> throw IllegalArgumentException("Proxy type is null")
    }
}

val SOCKS_PROXY_CLIENT_REF_MAP = WeakHashMap<Proxy, OkHttpClient>()
fun OkHttpClient.socksProxy(proxy: Proxy): OkHttpClient {
    if (proxy.type() != Proxy.Type.SOCKS)
        throw IllegalArgumentException("Proxy not of type SOCKS")
    return SOCKS_PROXY_CLIENT_REF_MAP.computeIfAbsent(proxy) {
        PROXY_BUILDER_DEFAULT(this.newBuilder())
            .socketFactory(SocksSocketFactory(proxy))
            .build()
    }
}

val HTTP_PROXY_CLIENT_REF_MAP = WeakHashMap<Proxy, OkHttpClient>()
fun OkHttpClient.httpProxy(proxy: Proxy): OkHttpClient {
    if (proxy.type() != Proxy.Type.HTTP)
        throw IllegalArgumentException("Proxy not of type HTTP")
    return HTTP_PROXY_CLIENT_REF_MAP.computeIfAbsent(proxy) {
        PROXY_BUILDER_DEFAULT(this.newBuilder())
            .proxy(proxy)
            .proxyAuthenticator(HttpProxyAuthenticator(proxy))
            .build()
    }
}

val DIRECT_PROXY_CLIENT_REF_MAP = WeakHashMap<Proxy, OkHttpClient>()
fun OkHttpClient.directProxy(proxy: Proxy? = null): OkHttpClient {
    if (proxy != null && proxy.type() != Proxy.Type.DIRECT)
        throw IllegalArgumentException("Proxy not of type DIRECT")
    return DIRECT_PROXY_CLIENT_REF_MAP.computeIfAbsent(proxy) {
        PROXY_BUILDER_DEFAULT(this.newBuilder())
            .proxy(proxy)
            .build()
    }
}
