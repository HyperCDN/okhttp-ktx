package de.hypercdn.extensions.okhttpktx.proxy

import sockslib.common.Credentials
import java.net.Proxy
import java.util.*

val PROXY_REF_MAP = WeakHashMap<Proxy, ExtendedProxy>()

fun Proxy.extended(credentials: Credentials? = null): ExtendedProxy {
    if (this is ExtendedProxy) return this
    return PROXY_REF_MAP.computeIfAbsent(this){
        ExtendedProxy(this, credentials)
    }
}