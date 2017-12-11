package com.stehno.dd.campaigntools.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.stehno.dd.campaigntools.model.PartyMember
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.File

@Service
class PartyService(@Value("\${repository.directory}") private var repositoryDirectory: File,
                   @Autowired private val objectMapper: ObjectMapper) {

    private val party: List<PartyMember>

    init {
        val memberFile = ensurePersistenceFile(repositoryDirectory, "party-members.json", "[]")
        party = objectMapper.readValue(memberFile)
    }

    fun retrieveAll(): List<PartyMember> {
        return party
    }

    fun retrieveMember(memberId: Long): PartyMember {
        return party.first { it.id == memberId }
    }

    // FIXME: move this to common area
    companion object {
        fun ensurePersistenceFile(directory: File, filename: String, defaultContent: String): File {
            val file = File(directory, filename)
            if (!file.exists()) {
                file.parentFile.mkdirs()
                file.createNewFile()
                file.writeText(defaultContent)
            }
            return file
        }
    }
}