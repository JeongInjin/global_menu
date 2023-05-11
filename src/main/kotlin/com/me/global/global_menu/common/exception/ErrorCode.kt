package com.me.global.global_menu.common.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val status: HttpStatus,
    val code: String,
    val message: String
) {
    // common errors
    MENU_NOT_FOUND(HttpStatus.NOT_FOUND, "E1003", "메뉴를 찾을 수 없습니다."),
    NOT_TOP_LEVEL_MENU(HttpStatus.BAD_REQUEST, "E1004", "메뉴가 최상위 메뉴가 아닙니다.")
}