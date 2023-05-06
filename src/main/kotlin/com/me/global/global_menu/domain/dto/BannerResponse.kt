package com.me.global.global_menu.domain.dto

import com.me.global.global_menu.domain.Banner

class BannerResponse(
    val id: Long,
    val title: String,
    val link: String,
    val menuId: Long
) {
    companion object {
        fun from(banner: Banner): BannerResponse {
            return BannerResponse(
                id = banner.id!!,
                title = banner.title!!,
                link = banner.link!!,
                menuId = banner.menu?.id!!
            )
        }
    }
}
