package com.stehno.dd.campaigntools.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.stehno.dd.campaigntools.model.PartyMember
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.File

@Service
class PartyService(@Autowired private val objectMapper: ObjectMapper,
                   @Value("\${repository.directory}") private var directory: File) {

    private val party: List<PartyMember>

    init {
        val memberFile = File(directory, "party-members.json")
        party = when {
            memberFile.exists() -> objectMapper.readValue(memberFile)
            else -> mutableListOf()
        }
    }

    fun retrieveAll(): List<PartyMember> {
        return party
    }

    fun retrieveMember(memberId: Long): PartyMember {
        return party.first { it.id == memberId }
    }
}