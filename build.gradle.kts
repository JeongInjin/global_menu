import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
    id("org.springframework.boot") version "3.0.5"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("jvm") version "1.7.22"
    kotlin("plugin.spring") version "1.7.22"
    kotlin("plugin.jpa") version "1.7.22"
    kotlin("kapt") version "1.7.22"
    idea
}

group = "com.me.global"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

buildscript {
    dependencies {
        classpath("gradle.plugin.com.ewerk.gradle.plugins:querydsl-plugin:1.0.10")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.22")
        classpath("org.jetbrains.kotlin:kotlin-allopen:1.7.22")
        classpath("org.jetbrains.kotlin:kotlin-noarg:1.7.22")
    }
}


repositories {
    mavenCentral()
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    //validation
    implementation("org.springframework.boot:spring-boot-starter-validation")

    //kotlin-logging
    implementation("org.springframework.boot:spring-boot-starter-logging")
    implementation("io.github.microutils:kotlin-logging:3.0.5")

    //h2 database
    implementation("com.h2database:h2")

    //jpa & querydsl
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.0.5")
    implementation("com.vladmihalcea:hibernate-types-60:2.21.1")
    implementation("com.infobip:infobip-spring-data-jpa-querydsl-boot-starter:8.1.1")
    kapt("com.querydsl:querydsl-apt:5.0.0:jakarta")

    //envers
    implementation("org.springframework.data:spring-data-envers")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

//query dsl
idea {
    module {
        val kaptMain = file("build/generated/source/kapt/main")
        sourceDirs.add(kaptMain)
        generatedSourceDirs.add(kaptMain)
    }
}