package com.stehno.dd.campaigntools.controller

import com.stehno.dd.campaigntools.model.PartyMember
import com.stehno.dd.campaigntools.service.PartyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView

@Controller
class PartyController(@Autowired private val partyService: PartyService) {

    @GetMapping(path = ["/party"])
    fun encounters(): ModelAndView {
        val mav = ModelAndView("party")

        mav.addObject("members", partyService.retrieveAll())

        return mav
    }

    @PostMapping(path = ["/party"])
    fun addMember(@RequestBody member: PartyMember): ResponseEntity<Unit> {
        partyService.addMember(member)
        return ResponseEntity.ok(Unit)
    }

    @PostMapping(path = ["/party/{memberId}"])
    fun addMember(@PathVariable("memberId") memberId: Long, @RequestBody member: PartyMember): ResponseEntity<Unit> {
        if (memberId == member.id) {
            partyService.updateMember(member)
        }
        return ResponseEntity.ok(Unit)
    }

    @DeleteMapping(path = ["/party/{memberId}"])
    fun removeMember(@PathVariable("memberId") memberId: Long): ResponseEntity<Unit> {
        partyService.removeMember(memberId)
        return ResponseEntity.ok(Unit)
    }
}
