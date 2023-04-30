package de.hypercdn.extensions.okhttpktx.proxy.provider

import java.net.Proxy
import java.util.concurrent.ConcurrentLinkedQueue

open class RotatingProxyProvider(proxies: List<Proxy>): ProxyProvider {

    private val proxies = ConcurrentLinkedQueue(proxies)

    @Synchronized
    override fun next(): Proxy {
        val proxy = proxies.poll()?:
            throw IllegalStateException("No proxy available")
        proxies.add(proxy)
        return proxy
    }

}