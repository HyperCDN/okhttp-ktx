package de.hypercdn.extensions.okhttpktx.proxy.utils

import okhttp3.Response
import java.time.OffsetDateTime

typealias EnableStrategy = () -> Boolean
typealias DisableStrategy = (response: Response?, throwable: Throwable?) -> Boolean

fun enableAfter(offsetDateTime: OffsetDateTime): EnableStrategy {
    return { offsetDateTime < OffsetDateTime.now() }
}

fun enableAlways(): EnableStrategy {
    return { true }
}

fun enableNever(): EnableStrategy {
    return { false }
}

fun disableOnException(): DisableStrategy {
    return { _, throwable ->  throwable != null }
}

fun disableOnStatusCode(code: Int): DisableStrategy {
    return { response, _ ->  response?.code == code }
}

fun disableAlways(): DisableStrategy {
    return { _, _ ->  true }
}

fun disableNever(): DisableStrategy {
    return { _, _ ->  false }
}