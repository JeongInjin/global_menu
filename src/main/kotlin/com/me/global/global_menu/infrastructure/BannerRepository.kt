package com.me.global.global_menu.infrastructure

import com.me.global.global_menu.domain.Banner
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface BannerRepository : JpaRepository<Banner, Long> {

    fun findByTitle(title: String): Optional<Banner>
    fun findByMenuId(menuId: Long): List<Banner>
}
