package de.hypercdn.extensions.okhttpktx.proxy

import sockslib.common.Credentials
import java.lang.ref.WeakReference
import java.net.Proxy
import java.net.SocketAddress

open class ExtendedProxy: Proxy {

    val ref: WeakReference<Proxy>?
    val credentials: Credentials?

    constructor(proxy: Proxy, credentials: Credentials?): super(proxy.type(), proxy.address()){
        this.credentials = credentials
        this.ref = WeakReference(proxy)
    }

    constructor(type: Type, address: SocketAddress, credentials: Credentials?) : super(type, address) {
        this.credentials = credentials
        this.ref = null
    }

}