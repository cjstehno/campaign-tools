package com.stehno.dd.campaigntools.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.stehno.dd.campaigntools.model.*
import com.stehno.dd.campaigntools.service.PartyService.Companion.ensurePersistenceFile
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.File
import java.util.*

@Service
class EncounterService(@Value("\${repository.directory}") private val repositoryDirectory: File,
                       @Autowired private val objectMapper: ObjectMapper) {

    private val persistenceFile: File = ensurePersistenceFile(repositoryDirectory, "encounters.json", "{}")
    private val encounters: MutableMap<Long, Encounter>

    init {
        encounters = objectMapper.readValue(persistenceFile)
    }

    private fun persist() {
        objectMapper.writeValue(persistenceFile, encounters)
    }

    fun retrieveAllEncounters(): List<Encounter> {
        return encounters.values.toList()
    }

    fun retrieveEncounter(encounterId: Long): Encounter? {
        return encounters[encounterId]
    }

    fun removeParticipant(encounterId: Long, participantId: Long) {
        val encounter: Encounter? = encounters[encounterId]
        if (encounter != null) {
            encounters[encounterId] = encounter.updateParticipants(TreeSet(encounter.participants.filter { it.id != participantId }))
            persist()
        }
    }

    fun addMonsterParticipant(encounterId: Long, participant: MonsterEncounterParticipant) {
        val encounter: Encounter? = encounters[encounterId]
        if (encounter != null) {
            val nextId = encounter.participants.maxBy { it.id }?.id ?: 1

            val updated = encounter.participants.toMutableSet()
            updated.add(MonsterEncounterParticipant(
                nextId,
                participant.initiative,
                participant.description,
                participant.armorClass,
                participant.hitPoints,
                participant.conditions
            ))

            encounters[encounterId] = encounter.updateParticipants(TreeSet(updated))
            persist()
        }
    }

    fun addPartyParticipant(encounterId: Long, partyMember: PartyMember, initiative: Int) {
        val encounter: Encounter? = encounters[encounterId]
        if (encounter != null) {
            val nextId = encounter.participants.maxBy { it.id }?.id ?: 1

            val updated = encounter.participants.toMutableSet()
            updated.add(PartyMemberEncounterParticipant(partyMember, nextId, initiative, setOf()))

            encounters[encounterId] = encounter.updateParticipants(TreeSet(updated))
            persist()
        }
    }

    fun adjustHp(encounterId: Long, participantId: Long, hitPoints: Int?) {
        val encounter: Encounter? = encounters[encounterId]
        if (encounter != null) {
            val participants = TreeSet<EncounterParticipant>(encounter.participants.map { p ->
                MonsterEncounterParticipant(
                    p.id,
                    p.initiative,
                    p.description,
                    p.armorClass,
                    if (p.id == participantId) {
                        hitPoints
                    } else {
                        p.hitPoints
                    },
                    p.conditions
                )
            })

            encounters[encounterId] = encounter.updateParticipants(participants)
            persist()
        }
    }

    fun updateDescription(encounterId: Long, participantId: Long, description: String?) {
        val encounter: Encounter? = encounters[encounterId]
        if (encounter != null) {
            val participants = TreeSet<EncounterParticipant>(encounter.participants.map { p ->
                MonsterEncounterParticipant(
                    p.id,
                    p.initiative,
                    if (!description.isNullOrBlank() && p.id == participantId) {
                        description!!
                    } else {
                        p.description
                    },
                    p.armorClass,
                    p.hitPoints,
                    p.conditions
                )
            })

            encounters[encounterId] = encounter.updateParticipants(participants)
            persist()
        }
    }

    fun updateConditions(encounterId: Long, participantId: Long, conditions: Array<String>?) {
        val encounter: Encounter? = encounters[encounterId]
        if (encounter != null) {
            val participants = TreeSet<EncounterParticipant>(encounter.participants.map { p ->
                MonsterEncounterParticipant(
                    p.id,
                    p.initiative,
                    p.description,
                    p.armorClass,
                    p.hitPoints,
                    if (conditions != null && p.id == participantId) {
                        TreeSet(conditions.map { c -> Condition.valueOf(c) })
                    } else {
                        p.conditions
                    }
                )
            })

            encounters[encounterId] = encounter.updateParticipants(participants)
            persist()
        }
    }

    fun startEncounter(encounterId: Long) {
        val encounter: Encounter? = encounters[encounterId]
        if (encounter != null) {
            encounters[encounterId] = encounter.start()
            persist()
        }
    }

    fun nextParticipant(encounterId: Long) {
        val encounter: Encounter? = encounters[encounterId]
        if (encounter != null) {
            encounters[encounterId] = encounter.next()
            persist()
        }
    }

    fun stopEncounter(encounterId: Long) {
        val encounter: Encounter? = encounters[encounterId]
        if (encounter != null) {
            encounters[encounterId] = encounter.stop()
            persist()
        }
    }

    fun addEncounter(name: String) {
        val nextId = when {
            encounters.isNotEmpty() -> encounters.keys.max()!! + 1
            else -> 1
        }

        encounters[nextId] = Encounter(
            nextId,
            name,
            TreeSet(),
            false,
            null,
            null
        )

        persist()
    }

    fun removeEncounter(encounterId: Long) {
        encounters.remove(encounterId)
        persist()
    }
}



