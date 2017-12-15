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

import com.stehno.dd.campaigntools.model.Dice
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.ModelAndView

@Controller
class DiceController {

    @GetMapping(path = ["/dice"])
    fun index(): ModelAndView {
        val mav = ModelAndView("dice")
        mav.addObject("rolled", "")
        mav.addObject("d", "")
        mav.addObject("result", "")
        return mav
    }

    @GetMapping(path = ["/dice/roll"])
    fun roll(@RequestParam("d") d: String): ModelAndView {
        val mav = ModelAndView("dice")
        mav.addObject("d", d)

        val dice = Dice.parse(d)
        mav.addObject("rolled", dice)
        mav.addObject("result", dice.roll())
        return mav
    }
}
