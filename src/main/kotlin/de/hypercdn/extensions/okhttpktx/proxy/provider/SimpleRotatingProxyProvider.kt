package de.hypercdn.extensions.okhttpktx.proxy.provider

import de.hypercdn.extensions.okhttpktx.proxy.utils.LoopIterator
import java.net.Proxy

open class SimpleRotatingProxyProvider(val proxies: List<Proxy>): ProxyProvider {

    val iterator = LoopIterator(proxies)

    override fun next(): Proxy {
        return iterator.next()
    }

}