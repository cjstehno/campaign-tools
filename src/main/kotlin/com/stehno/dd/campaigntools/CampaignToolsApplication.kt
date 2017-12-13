package com.stehno.dd.campaigntools

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.flywaydb.core.Flyway
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationInitializer
import org.springframework.context.annotation.Bean

@SpringBootApplication
class CampaignToolsApplication

fun main(args: Array<String>) {
    SpringApplication.run(CampaignToolsApplication::class.java, *args)
}

@Bean
fun objectMapper(): ObjectMapper {
    val mapper = ObjectMapper()
    mapper.registerModule(KotlinModule())
    return mapper
}
