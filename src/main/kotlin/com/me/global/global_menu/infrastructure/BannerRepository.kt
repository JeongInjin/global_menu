package com.me.global.global_menu.infrastructure

import com.me.global.global_menu.domain.Banner
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface BannerRepository : JpaRepository<Banner, Long> {

    fun findByTitle(title: String): Optional<Banner> // 타이블로 배너 찾기

    fun findByMenuId(menuId: Long): List<Banner> // 메뉴 ID 로 배너 찾기
}
