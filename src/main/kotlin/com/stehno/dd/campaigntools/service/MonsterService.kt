package com.stehno.dd.campaigntools.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.net.URL

/**
 * Pulls from http://www.dnd5eapi.co/
 *
 * at this point the list of available monsters (and their urls) is cached locally to reduce the hits on the
 * external server. this may change in the future.
 */
@Service
class MonsterService(@Autowired val objectMapper: ObjectMapper) {

    private val monsters: MonsterList

    init {
        monsters = MonsterService::class.java.getResourceAsStream("/data/monster-uris.json")!!.let { inputStream ->
            objectMapper.readValue(inputStream)
        }
    }

    fun retrieveMonsterList(): List<MonsterUrl> {
        return monsters.results
    }
}

data class MonsterList(val count: Int, val results: List<MonsterUrl>)
data class MonsterUrl(val name: String, val url: URL)
