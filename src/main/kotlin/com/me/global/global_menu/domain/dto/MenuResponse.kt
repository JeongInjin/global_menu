package com.me.global.global_menu.domain.dto

import com.me.global.global_menu.domain.Menu

class MenuResponse(
    val id: Long,
    val title: String,
    val link: String,
    val parentId: Long?,
    val subMenus: List<MenuResponse>,
    val banners: List<BannerResponse>
) {
    companion object {
        fun from(menu: Menu): MenuResponse {
            val subMenus = menu.subMenus.map { subMenu -> from(subMenu) }
            val banners = menu.banners.map { banner -> BannerResponse.from(banner) }

            return MenuResponse(
                id = menu.id!!,
                title = menu.title!!,
                link = menu.link!!,
                parentId = menu.parent?.id,
                subMenus = subMenus,
                banners = banners
            )
        }

        fun from(menus: List<Menu>): List<MenuResponse> {
            return menus.map { menu -> from(menu) }
        }
    }
}
