package com.julius.mytube.exception

import java.lang.Exception

open class BaseException @JvmOverloads constructor(
    val code: Int = 0, message: String? = null, cause: Throwable? = null
) : Exception(message, cause) {
}