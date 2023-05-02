package de.hypercdn.extensions.okhttpktx.proxy.provider.imp

import de.hypercdn.extensions.okhttpktx.proxy.provider.ConfigurableProxyProvider
import de.hypercdn.extensions.okhttpktx.proxy.utils.RandomSelector
import java.net.Proxy

open class RandomProxyProvider(proxies: Collection<Proxy>) : ConfigurableProxyProvider(proxies, RandomSelector)