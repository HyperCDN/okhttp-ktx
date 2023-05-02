package de.hypercdn.extensions.okhttpktx.proxy.provider.imp

import de.hypercdn.extensions.okhttpktx.proxy.provider.ConfigurableProxyProvider
import de.hypercdn.extensions.okhttpktx.proxy.utils.QueueLoopSelector
import java.net.Proxy
import java.util.*

class RotatingProxyProvider(proxies: Queue<Proxy>) : ConfigurableProxyProvider(proxies, QueueLoopSelector)