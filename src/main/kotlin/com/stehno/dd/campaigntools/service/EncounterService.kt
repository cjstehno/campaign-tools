package com.stehno.dd.campaigntools.service

import com.stehno.dd.campaigntools.model.Encounter
import com.stehno.dd.campaigntools.model.EncounterParticipant
import com.stehno.dd.campaigntools.model.ParticipantType.*
import org.springframework.stereotype.Service
import java.util.*

@Service
class EncounterService {

    private val encounters = mutableMapOf<Long, Encounter>(
        Pair(100, Encounter(100, "Old Roadhouse", TreeSet(setOf(
            EncounterParticipant(100, true, MONSTER, 18, "Bugbear", 10, 14, setOf(), ""),
            EncounterParticipant(200, false, PARTY_MEMBER, 17, "Braak", 12, null, setOf("prone"), ""),
            EncounterParticipant(300, false, DEAD, 0, "Bugbear", 10, 0, setOf(), "")
        ))))
    )

    fun retrieveAllEncounters(): List<Encounter> {
        return encounters.values.toList()
    }

    fun retrieveEncounter(encounterId: Long): Encounter? {
        return encounters[encounterId]
    }

    fun removeParticipant(encounterId: Long, participantId: Long) {
        val encounter: Encounter? = encounters[encounterId]
        if (encounter != null) {
            encounters[encounterId] = Encounter(
                encounter.id,
                encounter.name,
                TreeSet(encounter.participants.filter { it.id != participantId }),
                encounter.finished
            )
        }
    }

    fun addParticipant(encounterId: Long, participant: EncounterParticipant) {
        val encounter: Encounter? = encounters[encounterId]
        if (encounter != null) {
            val nextId = encounter.participants.maxBy { it.id }?.id ?: 1

            val updated = encounter.participants.toMutableSet()
            updated.add(EncounterParticipant(
                nextId,
                participant.active,
                participant.type,
                participant.initiative,
                participant.description,
                participant.armorClass,
                participant.hitPoints,
                participant.conditions,
                participant.notes
            ))

            encounters[encounterId] = Encounter(
                encounter.id,
                encounter.name,
                TreeSet(updated),
                encounter.finished
            )
        }
    }
}



