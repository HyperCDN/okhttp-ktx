package de.hypercdn.extensions.okhttpktx.proxy.manager

import okhttp3.Call
import okhttp3.Request

interface ProxyCallManager {

    fun newCall(request: Request): Call

}