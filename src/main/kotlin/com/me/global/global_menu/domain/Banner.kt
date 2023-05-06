package com.me.global.global_menu.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.me.global.global_menu.common.support.Auditing
import com.me.global.global_menu.domain.dto.BannerRequest
import jakarta.persistence.*
import jdk.jfr.Description

@Entity
@Table(name = "banners")
@Description("배너")
class Banner : Auditing() {
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
    @JoinColumn(name = "menu_id")
    @JsonIgnoreProperties("subMenus", "parent")
    @Description("메뉴")
    var menu: Menu? = null

    fun setData(request: BannerRequest, menu: Menu?): Banner {
        return Banner().apply {
            title = request.title
            link = request.link
            this.menu = menu
        }
    }
}
