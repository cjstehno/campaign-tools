package com.stehno.dd.campaigntools.model

import java.util.*

data class Encounter(val id: Long,
                     val name: String,
                     val participants: TreeSet<EncounterParticipant>,
                     val finished: Boolean = false) {

    fun containsPartyMember(memberId: Long): Boolean {
        return participants.find { p -> p.id == memberId } != null
    }
}

data class EncounterParticipant(val id: Long,
                                val active: Boolean,
                                val type: ParticipantType,
                                val initiative: Int,
                                val description: String,
                                val armorClass: Int,
                                val hitPoints: Int?,
                                val conditions: Set<String>,
                                val notes: String) : Comparable<EncounterParticipant> {

    override fun compareTo(other: EncounterParticipant): Int = when {
        other.initiative > initiative -> 1
        other.initiative < initiative -> -1
        else -> 0
    }
}

enum class ParticipantType(val id: String) {
    MONSTER("monster"),
    PARTY_MEMBER("party"),
    DEAD("dead")
}