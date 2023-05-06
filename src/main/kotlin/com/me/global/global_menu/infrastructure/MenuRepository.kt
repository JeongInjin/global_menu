package com.me.global.global_menu.infrastructure

import com.me.global.global_menu.domain.Menu
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MenuRepository : JpaRepository<Menu, Long> {
    fun findByParentIsNull(): List<Menu> // 상위 메뉴 찾기

    fun findByTitle(title: String): Optional<Menu> // 타이틀 찾기

    fun findByParentId(parentId: Long): List<Menu> // 상위 메뉴를 사용하여 하위 메뉴 찾기

    fun findByParentIsNotNull(): List<Menu> // 상위 메뉴가 있는 메뉴 찾기
}
