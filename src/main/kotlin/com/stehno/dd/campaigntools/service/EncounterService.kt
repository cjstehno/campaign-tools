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
package com.stehno.dd.campaigntools.service

import com.stehno.dd.campaigntools.model.DurationTimer
import com.stehno.dd.campaigntools.model.Encounter
import com.stehno.dd.campaigntools.model.EncounterParticipant
import com.stehno.dd.campaigntools.model.ParticipantType.MONSTER
import com.stehno.dd.campaigntools.model.ParticipantType.PARTY_MEMBER
import com.stehno.dd.campaigntools.model.PartyMember
import com.stehno.dd.campaigntools.repository.EncounterRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class EncounterService(@Autowired private val repository: EncounterRepository) {

    fun retrieveAllEncounters(): List<Encounter> {
        return repository.retrieveAll()
    }

    fun retrieveEncounter(encounterId: Long): Encounter? {
        return repository.retrieve(encounterId)
    }

    fun removeParticipant(encounterId: Long, participantId: Long) {
        repository.removeParticipant(encounterId, participantId)
    }

    fun addMonsterParticipant(encounterId: Long, description: String, initiative: Int, armorClass: Int, hitPoints: Int, experiencePoints: Int) {
        repository.addParticipant(
            encounterId,
            EncounterParticipant(null, null, MONSTER, initiative, description, armorClass, hitPoints, sortedSetOf(), experiencePoints)
        )
    }

    fun addPartyParticipant(encounterId: Long, member: PartyMember, initiative: Int) {
        repository.addParticipant(
            encounterId,
            EncounterParticipant(null, member.id, PARTY_MEMBER, initiative, member.displayName, member.armorClass, null, sortedSetOf(), 0)
        )
    }

    fun adjustHp(encounterId: Long, participantId: Long, hitPoints: Int?) {
        repository.updateHitPoints(encounterId, participantId, hitPoints)
    }

    fun updateDescription(encounterId: Long, participantId: Long, description: String) {
        repository.updateDescription(encounterId, participantId, description)
    }

    fun updateConditions(encounterId: Long, participantId: Long, conditions: Array<String>) {
        repository.updateConditions(encounterId, participantId, conditions)
    }

    fun startEncounter(encounterId: Long) {
        repository.start(encounterId)
    }

    fun nextParticipant(encounterId: Long) {
        repository.next(encounterId)
    }

    fun stopEncounter(encounterId: Long) {
        repository.stop(encounterId)
    }

    fun addEncounter(name: String) {
        repository.add(name)
    }

    fun removeEncounter(encounterId: Long) {
        repository.remove(encounterId)
    }

    fun addTimer(encounterId: Long, durationTimer: DurationTimer){
        repository.addTimer(encounterId, durationTimer)
    }

    fun removeTimer(encounterId: Long, timerId: Long) {
        repository.removeTimer(encounterId, timerId)
    }
}
