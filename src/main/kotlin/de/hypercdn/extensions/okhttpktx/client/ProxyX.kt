package de.hypercdn.extensions.okhttpktx.client

import sockslib.common.Credentials
import java.net.Proxy
import java.util.WeakHashMap

val REF_MAP = WeakHashMap<Proxy, ExtendedProxy>()

fun Proxy.extended(credentials: Credentials? = null): ExtendedProxy {
    if (this is ExtendedProxy) return this
    return REF_MAP.computeIfAbsent(this){
        ExtendedProxy(this, credentials)
    }
}