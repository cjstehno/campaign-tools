package com.stehno.dd.campaigntools.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod.GET

@Controller
class EncounterController {

    @RequestMapping(path = ["/"], method = [GET])
    fun index(): String {
        return "index"
    }

    @RequestMapping(path = ["/encounter"], method = [GET])
    fun encounter(): String {
        return "encounter"
    }
}