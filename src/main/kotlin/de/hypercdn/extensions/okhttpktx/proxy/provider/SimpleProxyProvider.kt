package de.hypercdn.extensions.okhttpktx.proxy.provider

import java.net.Proxy

class SimpleProxyProvider(
    private val proxy: Proxy
): ProxyProvider {
    override fun next(): Proxy {
        return proxy
    }

}