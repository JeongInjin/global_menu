package com.me.global.global_menu.init

import com.me.global.global_menu.domain.Banner
import com.me.global.global_menu.domain.Menu
import com.me.global.global_menu.infrastructure.BannerRepository
import com.me.global.global_menu.infrastructure.MenuRepository
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class SampleDataInitializer @Autowired constructor(
    private val menuRepository: MenuRepository, private val bannerRepository: BannerRepository
) {

    @PostConstruct
    fun init() {
        for (i in 1..5) {
            if (menuRepository.findByTitle("Top Menu $i").isPresent) continue

            // 메인 메뉴
            val menu = Menu().apply {
                title = "Top Menu $i"
                link = "/top-menu-$i"
            }
            menuRepository.save(menu)

            // 서브 메뉴
            for (j in 1..3) {
                if (menuRepository.findByTitle("Sub Menu $j of Top Menu $i").isPresent) continue

                val subMenu = Menu().apply {
                    title = "Sub Menu $j of Top Menu $i"
                    link = "/top-menu-$i/sub-menu-$j"
                    parent = menu
                }
                menu.subMenus.add(subMenu)
                menuRepository.save(subMenu)
            }
        } // TODO WOW!

        // 배너
        for (i in 1..3) {
            if (bannerRepository.findByTitle("Banner $i").isPresent) continue // Check for duplicates

            // Find the corresponding top-level menu
            val menu = menuRepository.findByTitle("Top Menu $i").orElse(null)

            val banner = Banner().apply {
                title = "Banner $i"
                link = "/banner-$i"
                this.menu = menu
            }
            bannerRepository.save(banner)
        }
    }
}
