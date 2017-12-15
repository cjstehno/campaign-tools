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
import com.stehno.dd.campaigntools.model.PartyMember
import com.stehno.dd.campaigntools.repository.PartyRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.File
import java.io.InputStream
import java.lang.System.currentTimeMillis

@Service
class PartyService(@Autowired private val repository: PartyRepository,
                   @Autowired private val objectMapper: ObjectMapper) {

    fun retrieveAll(): List<PartyMember> = repository.retrieveAll()

    fun retrieveMember(memberId: Long): PartyMember = repository.retrieve(memberId)

    fun addMember(member: PartyMember) = repository.add(member)

    fun removeMember(memberId: Long) = repository.remove(memberId)

    fun updateMember(member: PartyMember) = repository.update(member)

    fun exportMembers(): File {
        val file = File(System.getProperty("temp.dir"), "party-export-${currentTimeMillis()}.json")

        objectMapper.writeValue(file, repository.retrieveAll())

        return file
    }

    fun importMembers(input: InputStream) = objectMapper.readValue<List<PartyMember>>(input).forEach { m ->
        repository.add(m)
    }
}
