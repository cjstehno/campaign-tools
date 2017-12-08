package com.stehno.dd.campaigntools.service

import com.stehno.dd.campaigntools.model.EncounterParticipant
import com.stehno.dd.campaigntools.model.ParticipantType.*
import org.springframework.stereotype.Service

@Service
class EncounterService {

    private val combatants = mutableMapOf<Long, EncounterParticipant>(
        Pair(100, EncounterParticipant(100, true, MONSTER, 18, "Bugbear", 10, 14, setOf(), "")),
        Pair(200, EncounterParticipant(200, false, PARTY_MEMBER, 17, "Braak", 12, null, setOf("prone"), "")),
        Pair(300, EncounterParticipant(300, false, DEAD, 0, "Bugbear", 10, 0, setOf(), ""))
    )

    fun retrieveEncounter(/* some id */): List<EncounterParticipant> {
        return combatants.values.toList()
    }

    fun removeCombatant(combatantId: Long /* some encounter id*/) {
        combatants.remove(combatantId)
    }
}



