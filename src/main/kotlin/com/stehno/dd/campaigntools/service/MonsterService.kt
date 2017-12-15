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

        objectMapper.writeValue(file, repository.retrieveAll())

        return file
    }

    fun importMonsters(input: InputStream) = objectMapper.readValue<List<Monster>>(input).forEach { m ->
        repository.add(m)
    }
}