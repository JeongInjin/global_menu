package com.me.global.global_menu.common.exception

import java.lang.RuntimeException


class BusinessException : RuntimeException {

    var errorCode: ErrorCode? = null
        private set

    constructor(message: String) : super(message)

    constructor(message: String, errorCode: ErrorCode) : super(message) {
        this.errorCode = errorCode
    }

    constructor(errorCode: ErrorCode) : super(errorCode.message) {
        this.errorCode = errorCode
    }
}