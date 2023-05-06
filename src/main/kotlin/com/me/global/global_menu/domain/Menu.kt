package com.me.global.global_menu.domain

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import com.me.global.global_menu.common.support.Auditing
import com.me.global.global_menu.domain.dto.MenuRequest
import jakarta.persistence.*
import jdk.jfr.Description

@Entity
@Table(name = "menus")
@Description("메뉴")
class Menu : Auditing() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Description("sequence")
    val id: Long? = null

    @Column(nullable = false)
    @Description("제목")
    var title: String? = null

    @Column(nullable = false)
    @Description("링크")
    var link: String? = null

    @ManyToOne
    @JoinColumn(name = "parent_id")
    @JsonBackReference
    @Description("부모 메뉴")
    var parent: Menu? = null

    @OneToMany(mappedBy = "parent", cascade = [CascadeType.ALL], orphanRemoval = true)
    @JsonManagedReference
    @Description("하위 메뉴")
    val subMenus: MutableList<Menu> = mutableListOf()

    @OneToMany(mappedBy = "menu", cascade = [CascadeType.ALL], orphanRemoval = true)
    @Description("배너")
    var banners: MutableList<Banner> = mutableListOf()

    fun setData(request: MenuRequest, parentMenu: Menu?): Menu {
        return Menu().apply {
            this.title = request.title
            this.link = request.link
            this.parent = parentMenu
            this.banners = banners?.toMutableList() ?: mutableListOf()
        }
    }

    fun changeData(request: MenuRequest, parentMenu: Menu?) {
        this.title = request.title
        this.link = request.link
        this.parent = parentMenu
    }
}