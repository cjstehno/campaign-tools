package com.stehno.dd.campaigntools.service

import com.stehno.dd.campaigntools.model.*
import com.stehno.dd.campaigntools.model.Condition.PRONE
import org.springframework.stereotype.Service
import java.util.*

@Service
class EncounterService {

    private val encounters = mutableMapOf<Long, Encounter>(
        Pair(
            100,
            Encounter(
                100,
                "Old Roadhouse",
                TreeSet(setOf(
                    MonsterEncounterParticipant(100, 18, "Bugbear", 10, 14, setOf()),
                    MonsterEncounterParticipant(200, 15, "Bugbear", 10, 12, setOf()),
                    MonsterEncounterParticipant(300, 8, "Bugbear", 10, 7, setOf(PRONE))
                )
                ),
                false,
                null,
                null
            )
        )
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
            encounters[encounterId] = encounter.updateParticipants(TreeSet(encounter.participants.filter { it.id != participantId }))
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
        }
    }

    fun addPartyParticipant(encounterId: Long, partyMember: PartyMember, initiative: Int) {
        val encounter: Encounter? = encounters[encounterId]
        if (encounter != null) {
            val nextId = encounter.participants.maxBy { it.id }?.id ?: 1

            val updated = encounter.participants.toMutableSet()
            updated.add(PartyMemberEncounterParticipant(partyMember, nextId, initiative, setOf()))

            encounters[encounterId] = encounter.updateParticipants(TreeSet(updated))
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
        }
    }

    fun startEncounter(encounterId: Long) {
        val encounter: Encounter? = encounters[encounterId]
        if (encounter != null) {
            encounters[encounterId] = encounter.start()
        }
    }

    fun nextParticipant(encounterId: Long) {
        val encounter: Encounter? = encounters[encounterId]
        if (encounter != null) {
            encounters[encounterId] = encounter.next()
        }
    }

    fun stopEncounter(encounterId: Long) {
        val encounter: Encounter? = encounters[encounterId]
        if (encounter != null) {
            encounters[encounterId] = encounter.stop()
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
    }

    fun removeEncounter(encounterId: Long) {
        encounters.remove(encounterId)
    }
}



