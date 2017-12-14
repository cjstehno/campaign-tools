/**
 * Copyright (C) 2017 Christopher J. Stehno <chris@stehno.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
