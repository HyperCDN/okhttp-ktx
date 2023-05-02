package de.hypercdn.extensions.okhttpktx.proxy.utils

import de.hypercdn.extensions.okhttpktx.proxy.extended
import okhttp3.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.net.Proxy

open class HttpProxyAuthenticator (
    private val proxy: Proxy
) : Authenticator {

    init {
        if (proxy.type() != Proxy.Type.HTTP)
            throw IllegalStateException("Not a http proxy")
    }

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    override fun authenticate(route: Route?, response: Response): Request? {
        val extended = proxy.extended()
        if (extended.credentials == null)
            return null
        val credentials = Credentials.basic(extended.credentials.userPrincipal.name, extended.credentials.password)
        if (response.code == 407
            && (response.challenges().any{ it.scheme.equals("OkHttp-Preemptive", ignoreCase = true) })
            || response.request.header("Proxy-Authorization") == null)
        {
            log.debug("Injecting credentials for proxy {}", proxy)
            return response.request.newBuilder()
                .header("Proxy-Authorization", credentials)
                .build()
        }
        return null
    }

}