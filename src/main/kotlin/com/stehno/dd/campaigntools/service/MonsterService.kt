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
package com.stehno.dd.campaigntools.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.stehno.dd.campaigntools.model.Monster
import com.stehno.dd.campaigntools.repository.MonsterRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.File
import java.io.InputStream

@Service
class MonsterService(@Autowired private val repository: MonsterRepository,
                     @Autowired private val objectMapper: ObjectMapper) {


    fun retrieveAll(): List<Monster> = repository.retrieveAll()

    fun retrieveMonster(monsterId: Long): Monster = repository.retrieve(monsterId)

    fun addMonster(monster: Monster) = repository.add(monster)

    fun removeMonster(monsterId: Long) = repository.remove(monsterId)

    fun updateMonster(monster: Monster) = repository.update(monster)

    fun exportMonsters(): File {
        val file = File(System.getProperty("temp.dir"), "monster-export-${System.currentTimeMillis()}.json")
        file.deleteOnExit()

        objectMapper.writeValue(file, repository.retrieveAll())

        return file
    }

    fun importMonsters(input: InputStream) = objectMapper.readValue<List<Monster>>(input).forEach { m ->
        repository.add(m)
    }
}