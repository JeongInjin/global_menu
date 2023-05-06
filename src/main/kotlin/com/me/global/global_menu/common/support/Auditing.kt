package com.me.global.global_menu.common.support

import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.hibernate.envers.Audited
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
@Audited
abstract class Auditing {

    @CreatedDate
    @DateTimeFormat(pattern = DateTimeFormatType.DEFAULT_PATTERN)
    var createdAt: LocalDateTime? = null

    @LastModifiedDate
    @DateTimeFormat(pattern = DateTimeFormatType.DEFAULT_PATTERN)
    var updatedAt: LocalDateTime? = null
}