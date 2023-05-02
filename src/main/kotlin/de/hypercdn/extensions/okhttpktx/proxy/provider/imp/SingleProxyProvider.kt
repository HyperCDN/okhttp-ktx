package de.hypercdn.extensions.okhttpktx.proxy.provider.imp

import de.hypercdn.extensions.okhttpktx.proxy.provider.ConfigurableProxyProvider
import de.hypercdn.extensions.okhttpktx.proxy.utils.FirstSelector
import java.net.Proxy

open class SingleProxyProvider(proxy: Proxy) : ConfigurableProxyProvider(mutableListOf(proxy), FirstSelector)