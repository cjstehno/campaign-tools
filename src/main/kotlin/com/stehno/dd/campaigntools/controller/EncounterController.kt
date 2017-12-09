package com.stehno.dd.campaigntools.controller

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

    @GetMapping(path = ["/encounter/{encounterId}"])
    fun encounter(@PathVariable("encounterId") encounterId: Long): ModelAndView {
        val mav = ModelAndView("encounter")

        mav.addObject("party", partyService.retrieveAll())
        mav.addObject("encounter", encounterService.retrieveEncounter(encounterId))
        mav.addObject("monsters", monsterService.retrieveMonsterList())

        return mav
    }

    @DeleteMapping(path = ["/encounter/{encounterId}/{combatantId}"], produces = [APPLICATION_JSON_VALUE])
    fun removeParticipant(@PathVariable("encounterId") encounterId: Long, @PathVariable("combatantId") combatantId: Long): ResponseEntity<Unit> {
        encounterService.removeParticipant(encounterId, combatantId)

        return ResponseEntity.ok(Unit)
    }

    @PostMapping(path = ["/encounter/{encounterId}"], consumes = [APPLICATION_JSON_VALUE], produces = [APPLICATION_JSON_VALUE])
    fun addParticipant(@PathVariable("encounterId") encounterId: Long, @RequestBody participant: EncounterParticipant): ResponseEntity<Unit> {
        log.info("Adding {} to the encounter", participant.description)

        encounterService.addParticipant(encounterId, participant)

        return ResponseEntity.ok(Unit)
    }

    @PostMapping(path = ["/encounter/{encounterId}/party"], consumes = [APPLICATION_JSON_VALUE], produces = [APPLICATION_JSON_VALUE])
    fun addPartyMember(@PathVariable("encounterId") encounterId: Long,
                       @RequestBody info: AddedPartyMember): ResponseEntity<Unit> {
        log.info("Adding {} to the encounter", info)

        encounterService.addParticipantFromParty(encounterId, partyService.retrieveMember(info.memberId), info.initiative)

        return ResponseEntity.ok(Unit)
    }
}

data class AddedPartyMember(val memberId: Long, val initiative: Int)