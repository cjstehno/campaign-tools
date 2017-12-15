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
package com.stehno.dd.campaigntools.controller

import com.stehno.dd.campaigntools.model.PartyMember
import com.stehno.dd.campaigntools.service.PartyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.util.FileCopyUtils.copy
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletResponse

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
    fun updateMember(@PathVariable("memberId") memberId: Long, @RequestBody member: PartyMember): ResponseEntity<Unit> {
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

    @GetMapping(path = ["/party/export"], produces = [APPLICATION_JSON_VALUE])
    @ResponseBody
    fun exportMembers(response: HttpServletResponse) {
        val exportFile = partyService.exportMembers()

        response.contentType = APPLICATION_JSON_VALUE
        response.setHeader("Content-Disposition", "attachment; filename=party-export.json")
        response.setHeader("Content-Length", exportFile.length().toString())

        copy(exportFile.inputStream(), response.outputStream)
    }

    @PostMapping(path = ["/party/import"], consumes = [MULTIPART_FORM_DATA_VALUE])
    fun importMembers(@RequestParam("file") file: MultipartFile): String {
        file.inputStream.use {
            partyService.importMembers(it)
        }

        return "redirect:/party"
    }
}