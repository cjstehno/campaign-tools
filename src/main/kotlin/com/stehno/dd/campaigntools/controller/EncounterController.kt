package com.stehno.dd.campaigntools.controller

import com.stehno.dd.campaigntools.service.EncounterService
import com.stehno.dd.campaigntools.service.PartyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.servlet.ModelAndView

@Controller
class EncounterController {

    @Autowired lateinit var partyService: PartyService
    @Autowired lateinit var encounterService: EncounterService

    @GetMapping(path = ["/"])
    fun index(): String {
        return "index"
    }

    @GetMapping(path = ["/encounter"])
    fun encounter(): ModelAndView {
        val mav = ModelAndView("encounter")

        mav.addObject("party", partyService.retrieveAll())
        mav.addObject("encounter", encounterService.retrieveEncounter())

        return mav
    }

    @DeleteMapping(path = ["/encounter/{combatantId}"], produces = [APPLICATION_JSON_VALUE])
    fun removeCombatant(@PathVariable("combatantId") combatantId: Long): ResponseEntity<Unit> {
        encounterService.removeCombatant(combatantId)

        return ResponseEntity.ok(Unit)
    }
}