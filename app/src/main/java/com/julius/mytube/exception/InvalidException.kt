package com.julius.mytube.exception

class InvalidException(code: Int = 0, message: String? = null, cause: Throwable? = null) :
    BaseException(code, message, cause)