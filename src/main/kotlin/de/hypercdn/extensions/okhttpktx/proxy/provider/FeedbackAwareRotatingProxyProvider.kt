package de.hypercdn.extensions.okhttpktx.proxy.provider

import de.hypercdn.extensions.okhttpktx.proxy.utils.DisableStrategy
import de.hypercdn.extensions.okhttpktx.proxy.utils.EnableStrategy
import okhttp3.Response
import java.net.Proxy
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentLinkedQueue

open class FeedbackAwareRotatingProxyProvider(proxies: List<Proxy>, handlingStrategies: HashMap<DisableStrategy, () -> EnableStrategy>): FeedbackAwareProxyProvider {

    val strategies = HashMap<DisableStrategy, () -> EnableStrategy>(handlingStrategies)
    val enabled = ConcurrentLinkedQueue(proxies)
    val disabled = ConcurrentHashMap<Proxy, EnableStrategy>()

    @Synchronized
    private fun tryDisable(proxy: Proxy?, response: Response?, throwable: Throwable?) {
        if (disabled.contains(proxy) || proxy == null) return
        strategies.entries.find { it.key(response, throwable) }?.let {
            enabled.remove(proxy)
            disabled.put(proxy, it.value())
            return
        }
        return
    }

    @Synchronized
    private fun tryEnableAll() {
        if (disabled.isEmpty()) return
        val reEnable = disabled.entries.filter { it.value() }.map { it.key }
        reEnable.forEach {
            disabled.remove(it)
        }
        enabled.addAll(reEnable)
    }

    override fun handleFailure(proxy: Proxy?, throwable: Throwable?) {
        tryDisable(proxy, null, throwable)
    }

    override fun handleResponse(proxy: Proxy?, response: Response?) {
        tryDisable(proxy, response, null)
    }

    @Synchronized
    override fun next(): Proxy {
        tryEnableAll()
        val proxy = enabled.poll()
            ?: throw IllegalStateException("No proxy available. ${disabled.size} proxies currently disabled.")
        enabled.add(proxy)
        tryDisable(proxy, null, null)
        return proxy
    }
}