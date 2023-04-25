package de.hypercdn.extensions.okhttpktx.proxy

import de.hypercdn.extensions.okhttpktx.client.extended
import okhttp3.*
import java.net.Proxy

class HttpProxyAuthenticator (
    private val proxy: Proxy
) : Authenticator {

    init {
        if (proxy.type() != Proxy.Type.HTTP)
            throw IllegalStateException("Not a http proxy")
    }

    override fun authenticate(route: Route?, response: Response): Request? {
        val extended = proxy.extended()
        if (extended.credentials == null)
            return null
        val credentials = Credentials.basic(extended.credentials.userPrincipal.name, extended.credentials.password)
        if (response.code == 407
            && (response.challenges().any{ it.scheme.equals("OkHttp-Preemptive", ignoreCase = true) })
            || response.request.header("Proxy-Authorization") == null)
        {
            return response.request.newBuilder()
                .header("Proxy-Authorization", credentials)
                .build()
        }
        return null
    }

}