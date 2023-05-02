package de.hypercdn.extensions.okhttpktx.proxy.utils

import java.net.Proxy
import java.util.*

typealias Selector = (proxies: Collection<Proxy>) -> Proxy

val FirstSelector: Selector = { proxies -> proxies.first() }
val RandomSelector: Selector = { proxies -> proxies.random() }
val QueueLoopSelector: Selector = { proxies ->
    val queue = (proxies as Queue<Proxy>)
    val proxy = queue.poll() ?: throw NoSuchElementException()
    queue.add(proxy)
    proxy
}
