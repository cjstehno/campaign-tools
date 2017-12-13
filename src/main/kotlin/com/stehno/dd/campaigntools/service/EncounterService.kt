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

    fun addMonsterParticipant(encounterId: Long, description: String, initiative: Int, armorClass: Int, hitPoints: Int) {
        repository.addParticipant(
            encounterId,
            EncounterParticipant(null, null, MONSTER, initiative, description, armorClass, hitPoints, sortedSetOf())
        )
    }

    fun addPartyParticipant(encounterId: Long, member: PartyMember, initiative: Int) {
        repository.addParticipant(
            encounterId,
            EncounterParticipant(null, member.id, PARTY_MEMBER, initiative, member.displayName, member.armorClass, null, sortedSetOf())
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
