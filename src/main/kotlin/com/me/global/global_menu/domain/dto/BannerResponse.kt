package com.me.global.global_menu.domain.dto

import com.me.global.global_menu.domain.Banner
import com.me.global.global_menu.domain.Bannerdata class BannerResponse(
    val id: Long,
    val title: String,
    val link: String,
    val menuId: Long
) {
    constructor(banner: Banner): this(
        banner.id!!,
        banner.title!!,
        banner.link!!,
        banner.menu?.id!!
    )

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


