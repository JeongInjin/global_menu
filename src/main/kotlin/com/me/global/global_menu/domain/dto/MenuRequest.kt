package com.me.global.global_menu.domain.dto

import jakarta.validation.constraints.NotBlank
import jdk.jfr.Description

data class MenuRequest(
    @field:NotBlank(message = "메뉴 제목은 필수입니다.") var title: String,

    @field:NotBlank(message = "메뉴 링크는 필수입니다.") var link: String,

    @Description("빈값이라는 것은 최상의 메뉴를 뜻함.")
    val parentId: Long? = null,

    @Description("배너 목록입니다.") var banners: List<BannerRequest>? = null
)