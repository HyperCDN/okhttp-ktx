package de.hypercdn.extensions.okhttpktx.proxy.provider

import de.hypercdn.extensions.okhttpktx.proxy.utils.Selector
import java.net.Proxy

open class ConfigurableProxyProvider (
    private val proxies: Collection<Proxy>,
    private val selector: Selector
): ProxyProvider {

    override fun next(): Proxy {
        return selector(proxies)
    }

    override fun all(): List<Proxy> {
        return proxies.toList()
    }
}