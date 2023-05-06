package com.me.global.global_menu

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class GlobalMenuApplication

fun main(args: Array<String>) {
    runApplication<GlobalMenuApplication>(*args)
}
