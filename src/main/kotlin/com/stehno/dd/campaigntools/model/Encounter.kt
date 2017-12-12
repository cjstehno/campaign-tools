package com.stehno.dd.campaigntools.model

import java.util.*

data class Encounter(val id: Long,
                     val name: String,
                     val participants: TreeSet<EncounterParticipant>,
                     val finished: Boolean,
                     val round: Int?,
                     val activeId: Long?) {

    fun updateParticipants(participants: TreeSet<EncounterParticipant>) = Encounter(id, name, participants, finished, round, activeId)

    fun start(): Encounter {
        val first = participants.first()

        return Encounter(
            id,
            name,
            participants,
            false,
            1,
            first.id
        )
    }

    fun next(): Encounter {
        if (finished) {
            throw IllegalStateException("The encounter is already finished.")
        } else if (round == null || round == 0) {
            throw IllegalStateException("The encounter has not been started.")
        }

        val list = participants.toList()
        var index = list.indexOfFirst { p -> p.id == activeId } + 1
        var nextRound = round
        if (index >= list.size) {
            index = 0
            nextRound += 1
        }
        val nextParticipant = list[index]

        return Encounter(
            id,
            name,
            participants,
            false,
            nextRound,
            nextParticipant.id
        )
    }

    fun stop(): Encounter {
        return Encounter(
            id,
            name,
            participants,
            true,
            round,
            null
        )
    }

    fun containsPartyMember(memberId: Long): Boolean {
        return participants.find { p -> p is PartyMemberEncounterParticipant && p.memberId == memberId } != null
    }
}

enum class ParticipantType(val id: String) {
    MONSTER("monster"),
    PARTY_MEMBER("party")
}

enum class Condition {
    BLINDED,
    CHARMED,
    DEAFENED,
    FATIGUED,
    FRIGHTENED,
    GRAPPLED,
    INCAPACITATED,
    INVISIBLE,
    PARALYSED,
    PETRIFIED,
    POISONED,
    PRONE,
    RESTRAINED,
    STUNNED,
    UNCONSCIOUS,
    EXHAUSTION;

    var label: String = name.toLowerCase().capitalize()
}

interface EncounterParticipant : Comparable<EncounterParticipant> {

    val id: Long?
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
                                           override val id: Long?,
                                           override val initiative: Int,
                                           override val conditions: Set<Condition>) : EncounterParticipant {
    override val type: ParticipantType
        get() = ParticipantType.PARTY_MEMBER

    override val description: String
        get() = member.displayName

    override val armorClass: Int
        get() = member.armorClass

    override val hitPoints: Int?
        get() = null

    val memberId: Long
        get() = member.id!!
}

// TODO: this will pull from created monster list
data class MonsterEncounterParticipant(override val id: Long?,
                                       override val initiative: Int,
                                       override val description: String,
                                       override val armorClass: Int,
                                       override val hitPoints: Int?,
                                       override val conditions: Set<Condition>) : EncounterParticipant {
    override val type: ParticipantType
        get() = ParticipantType.MONSTER

}

// TODO: another type will be used for the ad-hoc particpants (not party and not listed monsters)