package de.hypercdn.extensions.okhttpktx.proxy.provider.imp

import de.hypercdn.extensions.okhttpktx.proxy.provider.ConfigurableFeedbackAwareProxyProvider
import de.hypercdn.extensions.okhttpktx.proxy.utils.DisableStrategy
import de.hypercdn.extensions.okhttpktx.proxy.utils.EnableStrategy
import de.hypercdn.extensions.okhttpktx.proxy.utils.RandomSelector
import java.net.Proxy

class RandomFeedbackAwareProxyProvider(proxies: MutableCollection<Proxy>, strategies: HashMap<DisableStrategy, () -> EnableStrategy>) : ConfigurableFeedbackAwareProxyProvider(proxies, RandomSelector, strategies)