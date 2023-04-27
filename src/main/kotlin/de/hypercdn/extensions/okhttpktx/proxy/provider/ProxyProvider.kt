package de.hypercdn.extensions.okhttpktx.proxy.provider

import java.net.Proxy

interface ProxyProvider {

    fun next(): Proxy

}