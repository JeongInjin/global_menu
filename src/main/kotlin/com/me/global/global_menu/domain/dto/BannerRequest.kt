package com.me.global.global_menu.domain.dto

import jakarta.validation.constraints.NotBlank

data class BannerRequest(
    @field:NotBlank(message = "배너 제목은 필수입니다.") var title: String,

    @field:NotBlank(message = "배너 링크는 필수입니다.") var link: String,
)
