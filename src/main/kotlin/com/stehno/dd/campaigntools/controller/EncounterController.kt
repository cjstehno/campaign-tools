package com.stehno.dd.campaigntools.controller

import com.stehno.dd.campaigntools.model.Condition
import com.stehno.dd.campaigntools.model.EncounterParticipant
import com.stehno.dd.campaigntools.service.EncounterService
import com.stehno.dd.campaigntools.service.MonsterService
import com.stehno.dd.campaigntools.service.PartyService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView

@Controller
class EncounterController {

    // TODO: some error handling would be nice

    companion object {
        private val log = LoggerFactory.getLogger(EncounterController::class.java)
    }

    @Autowired lateinit var partyService: PartyService
    @Autowired lateinit var encounterService: EncounterService
    @Autowired lateinit var monsterService: MonsterService

    @GetMapping(path = ["/"])
    fun index(): String {
        return "index"
    }

    @GetMapping(path = ["/encounter"])
    fun encounters(): ModelAndView {
        val mav = ModelAndView("encounters")

        mav.addObject("party", partyService.retrieveAll())
        mav.addObject("encounters", encounterService.retrieveAllEncounters())

        return mav
    }

    @PostMapping(path = ["/encounter"])
    fun addEncounter(@RequestBody payload: Map<String, String>): ResponseEntity<Unit> {
        val name = payload["name"]
        if (!name.isNullOrEmpty()) {
            encounterService.addEncounter(name!!)
        }

        return ResponseEntity.ok(Unit)
    }

    @DeleteMapping(path = ["/encounter/{encounterId}"])
    fun removeEncounter(@PathVariable("encounterId") encounterId: Long): ResponseEntity<Unit> {
        encounterService.removeEncounter(encounterId)
        return ResponseEntity.ok(Unit)
    }

    @GetMapping(path = ["/encounter/{encounterId}"])
    fun encounter(@PathVariable("encounterId") encounterId: Long): ModelAndView {
        val mav = ModelAndView("encounter")

        val encounter = encounterService.retrieveEncounter(encounterId)

        mav.addObject("party", partyService.retrieveAll())
        mav.addObject("encounter", encounter)
        mav.addObject("monsters", monsterService.retrieveMonsterList())
        mav.addObject("conditions", Condition.values())
        mav.addObject("elapsed", ElapsedTime(encounter?.round))

        return mav
    }

    @DeleteMapping(path = ["/encounter/{encounterId}/{combatantId}"], produces = [APPLICATION_JSON_VALUE])
    fun removeParticipant(@PathVariable("encounterId") encounterId: Long, @PathVariable("combatantId") combatantId: Long): ResponseEntity<Unit> {
        encounterService.removeParticipant(encounterId, combatantId)

        return ResponseEntity.ok(Unit)
    }

    @PostMapping(path = ["/encounter/{encounterId}"], consumes = [APPLICATION_JSON_VALUE], produces = [APPLICATION_JSON_VALUE])
    fun addMonsterParticipant(@PathVariable("encounterId") encounterId: Long, @RequestBody participant: EncounterParticipant): ResponseEntity<Unit> {
        log.info("Adding {} to the encounter", participant.description)

//  FIXME:       encounterService.addMonsterParticipant(encounterId, description: String, initiative: Int, armorClass: Int, hitPoints: Int)

        return ResponseEntity.ok(Unit)
    }

    @PostMapping(path = ["/encounter/{encounterId}/party"], consumes = [APPLICATION_JSON_VALUE], produces = [APPLICATION_JSON_VALUE])
    fun addPartyMember(@PathVariable("encounterId") encounterId: Long,
                       @RequestBody info: AddedPartyMember): ResponseEntity<Unit> {
        log.info("Adding {} to the encounter", info)

        encounterService.addPartyParticipant(encounterId, partyService.retrieveMember(info.memberId), info.initiative)

        return ResponseEntity.ok(Unit)
    }

    @PostMapping(path = ["/encounter/{encounterId}/{participantId}/hp"], consumes = [APPLICATION_JSON_VALUE])
    fun adjustHp(@PathVariable("encounterId") encounterId: Long,
                 @PathVariable("participantId") participantId: Long,
                 @RequestBody payload: Map<String, Int>): ResponseEntity<Unit> {

        encounterService.adjustHp(encounterId, participantId, payload["hp"])

        return ResponseEntity.ok(Unit)
    }

    @PostMapping(path = ["/encounter/{encounterId}/{participantId}/description"], consumes = [APPLICATION_JSON_VALUE])
    fun editDescription(@PathVariable("encounterId") encounterId: Long,
                        @PathVariable("participantId") participantId: Long,
                        @RequestBody payload: Map<String, String>): ResponseEntity<Unit> {

        val description: String? = payload["description"]
        if (!description.isNullOrEmpty()) {
            encounterService.updateDescription(encounterId, participantId, description!!)
        }

        return ResponseEntity.ok(Unit)
    }

    @PostMapping(path = ["/encounter/{encounterId}/{participantId}/conditions"], consumes = [APPLICATION_JSON_VALUE])
    fun editConditions(@PathVariable("encounterId") encounterId: Long,
                       @PathVariable("participantId") participantId: Long,
                       @RequestBody payload: Map<String, Array<String>>): ResponseEntity<Unit> {

        val conditions = payload["conditions"]
        if (conditions != null) {
            encounterService.updateConditions(encounterId, participantId, conditions)
        }

        return ResponseEntity.ok(Unit)
    }

    @PostMapping(path = ["/encounter/{encounterId}/start"])
    fun startEncounter(@PathVariable("encounterId") encounterId: Long): ResponseEntity<Unit> {
        encounterService.startEncounter(encounterId)

        return ResponseEntity.ok(Unit)
    }

    @PostMapping(path = ["/encounter/{encounterId}/next"])
    fun nextParticipant(@PathVariable("encounterId") encounterId: Long): ResponseEntity<Unit> {
        encounterService.nextParticipant(encounterId)

        return ResponseEntity.ok(Unit)
    }

    @PostMapping(path = ["/encounter/{encounterId}/stop"])
    fun stopEncounter(@PathVariable("encounterId") encounterId: Long): ResponseEntity<Unit> {
        encounterService.stopEncounter(encounterId)

        return ResponseEntity.ok(Unit)
    }
}

data class AddedPartyMember(val memberId: Long, val initiative: Int)

data class ElapsedTime(private val round: Int?) {

    val time: String?

    init {
        if (round == null) {
            time = null
        } else {
            val rawSeconds = (round - 1) * 6
            var minutes = 0
            var seconds = rawSeconds

            if (rawSeconds >= 60) {
                minutes = (rawSeconds / 60)
                seconds = rawSeconds - (minutes * 60)
            }

            time = "${minutes}m ${seconds.toString().padStart(2, '0')}s"
        }
    }
}