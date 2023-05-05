package de.hypercdn.extensions.okhttpktx.proxy.provider

import de.hypercdn.extensions.okhttpktx.proxy.utils.DisableStrategy
import de.hypercdn.extensions.okhttpktx.proxy.utils.EnableStrategy
import de.hypercdn.extensions.okhttpktx.proxy.utils.Selector
import okhttp3.Response
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.net.Proxy
import java.util.concurrent.ConcurrentHashMap

open class ConfigurableFeedbackAwareProxyProvider(
    proxies: MutableCollection<Proxy>,
    private val selector: Selector,
    private val strategies: HashMap<DisableStrategy, () -> EnableStrategy>
) : FeedbackAwareProxyProvider {

    private val enabled = proxies
    private val disabled = ConcurrentHashMap<Proxy, EnableStrategy>()
    private val supplyInfo = ConcurrentHashMap<Proxy, Long>()

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @Synchronized
    private fun tryDisable(proxy: Proxy?, response: Response?, throwable: Throwable?) {
        if (disabled.contains(proxy) || proxy == null) return
        strategies.entries.find { it.key(response, throwable) }?.let {
            enabled.remove(proxy)
            disabled.put(proxy, it.value())
            log.debug("Disabled proxy {}", proxy)
            return
        }
        return
    }

    @Synchronized
    private fun tryEnableAll() {
        if (disabled.isEmpty()) return
        val reEnable = disabled.entries.filter { it.value() }.map { it.key }
        if (reEnable.isEmpty()) return
        reEnable.forEach {
            disabled.remove(it)
        }
        enabled.addAll(reEnable)
        log.debug("Enabled proxies {}", reEnable)
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
        val proxy = selector(enabled)
        supplyInfo.computeIfPresent(proxy) { _, v -> v + 1 }
        tryDisable(proxy, null, null)
        return proxy
    }

    @Synchronized
    override fun all(): List<Proxy> {
        return ArrayList<Proxy>().apply {
            addAll(enabled)
            addAll(disabled.keys)
        }.toList()
    }

    fun ratio(): Pair<Int, Int> {
        return enabled.size to disabled.size
    }

    fun supplyInfo(): Map<Proxy, Long> {
        return supplyInfo.toMap()
    }
}