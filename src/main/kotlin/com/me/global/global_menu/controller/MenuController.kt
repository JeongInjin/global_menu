package com.me.global.global_menu.controller

import com.me.global.global_menu.common.support.ApiResponse
import com.me.global.global_menu.domain.Menu
import com.me.global.global_menu.domain.dto.MenuRequest
import com.me.global.global_menu.domain.dto.MenuResponse
import com.me.global.global_menu.service.MenuService
import jakarta.validation.Valid
import jdk.jfr.Description
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/menus")
class MenuController(private val menuService: MenuService) {

    @Description("메뉴 등록")
    @PostMapping
    fun createMenu(@Valid @RequestBody request: MenuRequest): ResponseEntity<Any> {
        val menu = menuService.createMenu(request)
        val response = MenuResponse.from(menu)
        val apiResponse = ApiResponse(success = true, message = null, result = response)
        return ResponseEntity.ok(apiResponse)
    }

    @Description("메뉴 수정")
    @PutMapping("/{id}")
    fun updateMenu(@PathVariable id: Long, @Valid @RequestBody request: MenuRequest): ResponseEntity<Any> {
        val menu = menuService.updateMenu(id, request)
        val response = MenuResponse.from(menu)
        val apiResponse = ApiResponse(success = true, message = null, result = response)
        return ResponseEntity.ok(apiResponse)
    }

    @Description("메뉴 삭제")
    @DeleteMapping("/{id}")
    fun deleteMenu(@PathVariable id: Long): ResponseEntity<Any> {
        menuService.deleteMenu(id)
        val apiResponse = ApiResponse(success = true, message = null, result = null)
        return ResponseEntity.ok(apiResponse)
    }

    @Description("상위 메뉴를 사용하여 하위 메뉴 찾기")
    @GetMapping("/{parentId}/sub-menus")
    fun findSubMenus(@PathVariable parentId: Long): ResponseEntity<Any> {
        val subMenus = menuService.findSubMenus(parentId)
        val response = MenuResponse.from(subMenus)
        val apiResponse = ApiResponse(success = true, message = null, result = response)
        return ResponseEntity.ok(apiResponse)
    }
}
