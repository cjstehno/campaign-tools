package com.stehno.dd.campaigntools.model

import java.util.*

data class Encounter(val id: Long,
                     val name: String,
                     val participants: TreeSet<EncounterParticipant>,
                     val finished: Boolean = false) {

    fun containsPartyMember(memberId: Long): Boolean {
        return participants.find { p -> p is PartyMemberEncounterParticipant && p.memberId == memberId } != null
    }
}

enum class ParticipantType(val id: String) {
    MONSTER("monster"),
    PARTY_MEMBER("party")
}

enum class Condition {
    BLINDED, CHARMED, DEAFENED, FATIGUED, FRIGHTENED, GRAPPLED, INCAPACITATED, INVISIBLE, PARALYSED,
    PETRIFIED, POISONED, PRONE, RESTRAINED, STUNNED, UNCONSCIOUS, EXHAUSTION
}

interface EncounterParticipant : Comparable<EncounterParticipant> {

    val id: Long
    val active: Boolean
    val type: ParticipantType
    val initiative: Int
    val description: String
    val armorClass: Int
    val hitPoints: Int?
    val conditions: Set<Condition>

    override fun compareTo(other: EncounterParticipant): Int = when {
        other.initiative > initiative -> 1
        other.initiative < initiative -> -1
        else -> 0
    }
}

data class PartyMemberEncounterParticipant(private val member: PartyMember,
                                           override val id: Long,
                                           override val initiative: Int,
                                           override val conditions: Set<Condition>,
                                           override val active: Boolean) : EncounterParticipant {
    override val type: ParticipantType
        get() = ParticipantType.PARTY_MEMBER

    override val description: String
        get() = member.getDisplayName()

    override val armorClass: Int
        get() = member.armorClass

    override val hitPoints: Int?
        get() = null

    val memberId: Long
        get() = member.id
}

// TODO: this will pull from created monster list
data class MonsterEncounterParticipant(override val id: Long,
                                       override val initiative: Int,
                                       override val description: String,
                                       override val armorClass: Int,
                                       override val hitPoints: Int?,
                                       override val conditions: Set<Condition>,
                                       override val active: Boolean) : EncounterParticipant {
    override val type: ParticipantType
        get() = ParticipantType.MONSTER

}

// TODO: another type will be used for the ad-hoc particpants (not party and not listed monsters)