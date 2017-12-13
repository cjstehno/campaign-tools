package com.stehno.dd.campaigntools.model

import java.util.*

data class Encounter(val id: Long,
                     val name: String,
                     val participants: TreeSet<EncounterParticipant>,
                     val finished: Boolean,
                     val round: Int?,
                     val activeId: Long?,
                     val timers: List<DurationTimer>?) {

    fun containsPartyMember(memberId: Long): Boolean {
        return participants.find { p -> p.type == ParticipantType.PARTY_MEMBER && p.refId == memberId } != null
    }
}

enum class ParticipantType(val id: String) {
    MONSTER("monster"),
    PARTY_MEMBER("party")
}

enum class Condition {
    BLINDED, CHARMED, DEAFENED, FATIGUED, FRIGHTENED, GRAPPLED,
    INCAPACITATED, INVISIBLE, PARALYSED, PETRIFIED, POISONED,
    PRONE, RESTRAINED, STUNNED, UNCONSCIOUS, EXHAUSTION;

    var label: String = name.toLowerCase().capitalize()
}

data class EncounterParticipant(val id: Long?,
                                val refId: Long?,
                                val type: ParticipantType,
                                val initiative: Int,
                                val description: String,
                                val armorClass: Int,
                                val hitPoints: Int?,
                                val conditions: Set<Condition>) : Comparable<EncounterParticipant> {

    override fun compareTo(other: EncounterParticipant): Int = when {
        other.initiative > initiative -> 1
        other.initiative < initiative -> -1
        else -> 0
    }
}
