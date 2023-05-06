package com.me.global.global_menu.service

import com.me.global.global_menu.common.exception.BusinessException
import com.me.global.global_menu.common.exception.ErrorCode
import com.me.global.global_menu.domain.Banner
import com.me.global.global_menu.domain.Menu
import com.me.global.global_menu.domain.dto.BannerRequest
import com.me.global.global_menu.domain.dto.MenuRequest
import com.me.global.global_menu.infrastructure.BannerRepository
import com.me.global.global_menu.infrastructure.MenuRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.transaction.annotation.Transactional

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
class MenuServiceTest(
    @Autowired private val menuService: MenuService,
    @Autowired private val menuRepository: MenuRepository,
    @Autowired private val bannerRepository: BannerRepository,
) {
    @BeforeEach
    fun setup() {
        val menu1 = Menu().apply { title = "Menu1"; link = "link1" }
        val menu2 = Menu().apply { title = "Menu2"; link = "link2" }
        val subMenu1 = Menu().apply { title = "SubMenu1"; link = "link3"; parent = menu1 }
        val subMenu2 = Menu().apply { title = "SubMenu2"; link = "link4"; parent = menu1 }
        val subMenu3 = Menu().apply { title = "SubMenu3"; link = "link5"; parent = menu2 }

        val banner1 = Banner().apply { title = "Banner1"; link = "bannerLink1"; menu = menu1 }
        val banner2 = Banner().apply { title = "Banner2"; link = "bannerLink2"; menu = menu1 }

        menuRepository.saveAll(listOf(menu1, menu2, subMenu1, subMenu2, subMenu3))
        bannerRepository.saveAll(listOf(banner1, banner2))
    }

    @AfterEach
    fun tearDown() {
        // 샘플 데이터 삭제
        bannerRepository.deleteAll()
        menuRepository.deleteAll()
    }

    @Test
    @DisplayName("상위 메뉴 등록 - 배너 데이터 있음")
    fun createTopLevelMenuWithBanner() {
        // given
        val request = MenuRequest(
            title = "NewTopMenu", link = "newLink", banners = listOf(
                BannerRequest(title = "NewBanner1", link = "newBannerLink1"),
                BannerRequest(title = "NewBanner2", link = "newBannerLink2")
            )
        )

        // when
        val createdMenu = menuService.createMenu(request)

        // then
        assertThat(createdMenu.id).isNotNull()
        assertThat(createdMenu.title).isEqualTo(request.title)
        assertThat(createdMenu.link).isEqualTo(request.link)
        assertThat(createdMenu.parent).isNull()
        assertThat(createdMenu.banners).hasSize(2)
        assertThat(createdMenu.banners).anySatisfy {
            assertThat(it.title).isEqualTo("NewBanner1")
            assertThat(it.link).isEqualTo("newBannerLink1")
        }
        assertThat(createdMenu.banners).anySatisfy {
            assertThat(it.title).isEqualTo("NewBanner2")
            assertThat(it.link).isEqualTo("newBannerLink2")
        }
    }

    @Test
    @DisplayName("상위 메뉴 등록 - 배너 데이터 없음")
    fun createTopLevelMenuWithoutBanner() {
        // given
        val request = MenuRequest(title = "NewTopMenu", link = "newLink")

        // when
        val createdMenu = menuService.createMenu(request)

        // then
        assertThat(createdMenu.id).isNotNull()
        assertThat(createdMenu.title).isEqualTo(request.title)
        assertThat(createdMenu.link).isEqualTo(request.link)
        assertThat(createdMenu.parent).isNull()
        assertThat(createdMenu.banners).isEmpty()
    }


    @Test
    @DisplayName("하위 메뉴 등록 - 부모 메뉴 존재")
    fun createSubMenuWithParentMenu() {
        // given
        val parentMenu = menuRepository.findByTitle("Menu1").get()
        val request = MenuRequest(title = "NewTopMenu", link = "newLink", parentId = parentMenu.id)

        // when
        val createdMenu = menuService.createMenu(request)

        // then
        assertThat(createdMenu.id).isNotNull()
        assertThat(createdMenu.title).isEqualTo(request.title)
        assertThat(createdMenu.link).isEqualTo(request.link)
        assertThat(createdMenu.parent).isEqualTo(parentMenu)
        assertThat(createdMenu.banners).isEmpty()
    }


    @Test
    @DisplayName("하위 메뉴 등록 - 부모 메뉴 미존재")
    fun createSubMenuWithoutParentMenu() {
        // given
        val request = MenuRequest(title = "NewTopMenu", link = "newLink", parentId = 9999999999)

        // when
        val exception = assertThrows<BusinessException> {
            menuService.createMenu(request)
        }

        // then
        assertThat(exception.errorCode).isEqualTo(ErrorCode.MENU_NOT_FOUND)
    }

    @Test
    @DisplayName("메뉴 수정 - 상위 메뉴 변경 및 배너 변경")
    fun updateMenuWithParentChangeAndBannerChange() {
        // given
        val menu = menuRepository.findByTitle("Menu1").get()
        val request = MenuRequest(
            title = "UpdatedMenu1", link = "updatedLink1", parentId = null, banners = listOf(
                BannerRequest(title = "UpdatedBanner1", link = "updatedBannerLink1"),
                BannerRequest(title = "UpdatedBanner2", link = "updatedBannerLink2")
            )
        )

        // when
        val updatedMenu = menuService.updateMenu(menu.id!!, request)

        // then
        assertThat(updatedMenu).isNotNull
        assertThat(updatedMenu.title).isEqualTo(request.title)
        assertThat(updatedMenu.link).isEqualTo(request.link)
        assertThat(updatedMenu.parent).isNull()
        assertThat(updatedMenu.banners).hasSize(2)
        assertThat(updatedMenu.banners).anySatisfy { it.title == "UpdatedBanner1" }
        assertThat(updatedMenu.banners).anySatisfy { it.link == "updatedBannerLink2" }
    }

    @Test
    @DisplayName("메뉴 삭제 - 최상위 메뉴 삭제")
    fun deleteTopLevelMenu() {
        // given
        val menu = menuRepository.findByParentIsNull().get(0)

        // when
        menuService.deleteMenu(menu.id!!)

        // then
        assertThat(menuRepository.existsById(menu.id!!)).isFalse
        assertThat(bannerRepository.findByMenuId(menu.id!!)).isEmpty()
        assertThat(menuRepository.findByParentId(menu.id!!)).isEmpty()
    }

    @Test
    @DisplayName("메뉴 삭제 - 하위 메뉴 삭제")
    fun deleteSubMenu() {
        // given
        val subMenu = menuRepository.findByParentIsNotNull()[0]

        // when
        menuService.deleteMenu(subMenu.id!!)

        // then
        assertThat(menuRepository.existsById(subMenu.id!!)).isFalse
        assertThat(bannerRepository.findByMenuId(subMenu.id!!)).isEmpty()
    }


}