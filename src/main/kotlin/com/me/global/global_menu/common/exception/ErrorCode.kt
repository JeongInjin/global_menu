package com.me.global.global_menu.common.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val status: HttpStatus,
    val code: String,
    val message: String
) {
    // common errors
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "E0001", "입력 값이 잘못되었습니다."),
    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "E0002", "입력 값 형식이 잘못되었습니다."),
    MENU_NOT_FOUND(HttpStatus.NOT_FOUND, "E1003", "메뉴를 찾을 수 없습니다."),
    NOT_TOP_LEVEL_MENU(HttpStatus.BAD_REQUEST, "E1004", "메뉴가 최상위 메뉴가 아닙니다.")
}