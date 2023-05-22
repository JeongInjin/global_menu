package com.me.global.global_menu.service

import com.me.global.global_menu.common.exception.BusinessException
import com.me.global.global_menu.common.exception.ErrorCode.MENU_NOT_FOUND
import com.me.global.global_menu.common.exception.ErrorCode.NOT_TOP_LEVEL_MENU
import com.me.global.global_menu.domain.Banner
import com.me.global.global_menu.domain.Menu
import com.me.global.global_menu.domain.dto.MenuRequest
import com.me.global.global_menu.infrastructure.BannerRepository
import com.me.global.global_menu.infrastructure.MenuRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class MenuService(
    private val menuRepository: MenuRepository,
    private val bannerRepository: BannerRepository,
) {
    @Transactional
    fun createMenu(request: MenuRequest): Menu {
        // 부모아이디가 넘어옮.
        val parentMenu = request.parentId?.let {
            getMenuOrThrow(it)
        }

        val menu = Menu().setData(request, parentMenu)
        val savedMenu = menuRepository.save(menu)

        // 최상위 메뉴 & 배너 데이터가 있는 경우
        if (parentMenu == null && request.banners != null) {
            val banners: List<Banner> = request.banners!!.map { Banner().setData(it, savedMenu) }
            savedMenu.banners.addAll(banners)
            bannerRepository.saveAll(banners)
        }

        return savedMenu
    }

    @Transactional
    fun updateMenu(id: Long, request: MenuRequest): Menu {
        val menu = getMenuOrThrow(id)

        // 부모객체 확인
        val parentMenu = request.parentId?.let {
            getMenuOrThrow(it)
        } ?: null

        // 배너데이터가 있다면 부모 아이디가 있는지 확인.
        parentBannerValid(parentMenu, request)

        menu.changeData(request, parentMenu)

        // 배너 업데이트
        if (!request.banners.isNullOrEmpty()) {
            val updatedBanners = request.banners!!.map { Banner().setData(it, menu) }
            menu.banners.clear()
            menu.banners.addAll(updatedBanners)
        }

        return menuRepository.save(menu)
    }

    @Transactional
    fun deleteMenu(id: Long) {
        val menu = getMenuOrThrow(id)
        if (menu.parent != null) {
            menuRepository.delete(menu)
        } else { //최상위 메뉴라면.
            menu.subMenus.forEach { subMenu ->
                subMenu.banners.clear()
            }
            menu.subMenus.clear()
            menuRepository.delete(menu)
        }
    }

    // 메뉴반환 or Throw BusinessException
    private fun getMenuOrThrow(id: Long): Menu {
        return menuRepository.findById(id).orElseThrow { BusinessException(MENU_NOT_FOUND) }
    }

    // 부모 메뉴가 존재하는데 배너 데이터가 있다면 에러 발생.
    private fun parentBannerValid(parentMenu: Menu?, request: MenuRequest) {
        if (parentMenu != null && request.banners != null) {
            throw BusinessException(NOT_TOP_LEVEL_MENU)
        }
    }

    // 상위 메뉴를 사용하여 하위 메뉴 찾기
    fun findSubMenus(parentId: Long): List<Menu> = menuRepository.findByParentId(parentId)
}
