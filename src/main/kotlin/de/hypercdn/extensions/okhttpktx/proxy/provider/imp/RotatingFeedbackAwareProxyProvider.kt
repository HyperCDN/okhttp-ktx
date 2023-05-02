package de.hypercdn.extensions.okhttpktx.proxy.provider.imp

import de.hypercdn.extensions.okhttpktx.proxy.provider.ConfigurableFeedbackAwareProxyProvider
import de.hypercdn.extensions.okhttpktx.proxy.utils.DisableStrategy
import de.hypercdn.extensions.okhttpktx.proxy.utils.EnableStrategy
import de.hypercdn.extensions.okhttpktx.proxy.utils.QueueLoopSelector
import java.net.Proxy
import java.util.*

class RotatingFeedbackAwareProxyProvider(proxies: Queue<Proxy>, strategies: HashMap<DisableStrategy, () -> EnableStrategy>) : ConfigurableFeedbackAwareProxyProvider(proxies, QueueLoopSelector, strategies)