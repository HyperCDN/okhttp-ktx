package de.hypercdn.extensions.okhttpktx.proxy.utils

import de.hypercdn.extensions.okhttpktx.proxy.extended
import sockslib.client.Socks5
import sockslib.client.SocksSocket
import java.net.InetAddress
import java.net.Proxy
import java.net.Socket
import javax.net.SocketFactory

open class SocksSocketFactory(
    proxy: Proxy,
    private val ignoreLocalRebindBindAttempts: Boolean = false
) : SocketFactory() {

    private val client: Socks5

    init {
        if (proxy.type() != Proxy.Type.SOCKS)
            throw IllegalStateException("Not a socks proxy")
        val extended = proxy.extended()
        client = if (extended.credentials != null) {
            Socks5(proxy.address(), extended.credentials.userPrincipal.name, extended.credentials.password)
        } else {
            Socks5(proxy.address())
        }
    }

    override fun createSocket(): Socket {
        return SocksSocket(client)
    }

    override fun createSocket(host: String?, port: Int): Socket {
        return SocksSocket(client, host, port)
    }

    override fun createSocket(host: String?, port: Int, localHost: InetAddress?, localPort: Int): Socket {
        if (!ignoreLocalRebindBindAttempts)
            throw UnsupportedOperationException("Rebinding local address not supported with Socks5Proxy")
        return SocksSocket(client, host, port)
    }

    override fun createSocket(address: InetAddress?, port: Int): Socket {
        return SocksSocket(client, address, port)
    }

    override fun createSocket(address: InetAddress?, port: Int, localAddress: InetAddress?, localPort: Int): Socket {
        if (!ignoreLocalRebindBindAttempts)
            throw UnsupportedOperationException("Rebinding local address not supported with Socks5Proxy")
        return SocksSocket(client, address, port)
    }

}