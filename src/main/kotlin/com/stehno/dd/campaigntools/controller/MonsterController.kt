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

import com.stehno.dd.campaigntools.model.Monster
import com.stehno.dd.campaigntools.service.MonsterService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.util.FileCopyUtils
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletResponse

@Controller
class MonsterController {

    @Autowired private lateinit var monsterService: MonsterService

    @GetMapping(path = ["/monster"])
    fun list() = ModelAndView("monsters").addObject("monsters", monsterService.retrieveAll())

    @PostMapping(path = ["/monster"])
    fun addMonster(@RequestBody monster: Monster): ResponseEntity<Unit> {
        monsterService.addMonster(monster)
        return ResponseEntity.ok(Unit)
    }

    @PostMapping(path = ["/monster/{monsterId}"])
    fun updateMonster(@PathVariable("monsterId") monsterId: Long, @RequestBody monster: Monster): ResponseEntity<Unit> {
        if (monsterId == monster.id) {
            monsterService.updateMonster(monster)
        }
        return ResponseEntity.ok(Unit)
    }

    @DeleteMapping(path = ["/monster/{monsterId}"])
    fun removeMonster(@PathVariable("monsterId") monsterId: Long): ResponseEntity<Unit> {
        monsterService.removeMonster(monsterId)
        return ResponseEntity.ok(Unit)
    }

    @GetMapping(path = ["/monster/export"], produces = [(MediaType.APPLICATION_JSON_VALUE)])
    @ResponseBody
    fun exportMonsters(response: HttpServletResponse) {
        val exportFile = monsterService.exportMonsters()

        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.setHeader("Content-Disposition", "attachment; filename=monster-export.json")
        response.setHeader("Content-Length", exportFile.length().toString())

        FileCopyUtils.copy(exportFile.inputStream(), response.outputStream)
    }

    @PostMapping(path = ["/monster/import"], consumes = [(MediaType.MULTIPART_FORM_DATA_VALUE)])
    fun importMonsters(@RequestParam("file") file: MultipartFile): String {
        file.inputStream.use {
            monsterService.importMonsters(it)
        }

        return "redirect:/monster"
    }
}