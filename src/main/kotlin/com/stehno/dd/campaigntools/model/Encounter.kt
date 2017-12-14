/**
 * Copyright (C) 2017 Christopher J. Stehno <chris@stehno.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stehno.dd.campaigntools.model

import java.util.*

data class Encounter(val id: Long,
                     val name: String,
                     val participants: TreeSet<EncounterParticipant>,
                     val finished: Boolean,
                     val round: Int?,
                     val activeId: Long?,
                     val timers: List<DurationTimer>?) {


    val totalExperience: Int
        get() = participants.sumBy { it.experiencePoints }

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
                                val conditions: Set<Condition>,
                                val experiencePoints: Int = 0) : Comparable<EncounterParticipant> {

    override fun compareTo(other: EncounterParticipant): Int = when {
        other.initiative > initiative -> 1
        other.initiative < initiative -> -1
        else -> 0
    }
}
