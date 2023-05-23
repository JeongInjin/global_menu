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
    constructor(menu: Menu) : this(
        id = menu.id!!,
        title = menu.title!!,
        link = menu.link!!,
        parentId = menu.parent?.id,
        subMenus = menu.subMenus.map(::MenuResponse),
        banners = menu.banners.map(::BannerResponse)
    )

    companion object {
        fun from(menu: Menu): MenuResponse {
            return MenuResponse(menu)
        }

        fun from(menus: List<Menu>): List<MenuResponse> {
            return menus.map { menu -> from(menu) }
        }
    }

//    companion object {
//        fun from(menu: Menu): MenuResponse {
//            val subMenus = menu.subMenus.map { subMenu -> from(subMenu) }
//            val banners = menu.banners.map(::BannerResponse)
//
//            return MenuResponse(
//                id = menu.id!!,
//                title = menu.title!!,
//                link = menu.link!!,
//                parentId = menu.parent?.id,
//                subMenus = subMenus,
//                banners = banners
//            )
//        }
//
//        fun from(menus: List<Menu>): List<MenuResponse> {
//            return menus.map { menu -> from(menu) }
//        }
//    }
}
