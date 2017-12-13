package com.stehno.dd.campaigntools.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.ModelAndView
import java.security.SecureRandom
import java.util.regex.Pattern

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

class Dice(private val rolls: Int, private val die: Int, private val modifier: Int) {

    companion object {
        private val pattern = Pattern.compile("([1-9]*)d([1-9]?[0-9]?[0-9])[+]?([0-9]?[0-9]?)")
        private val rng = SecureRandom()

        @JvmStatic
        fun parse(expression: String): Dice {
            val matcher = pattern.matcher(expression.trim())
            if (matcher.matches()) {
                return Dice(toi(matcher.group(1), "1"), Integer.parseInt(matcher.group(2)), toi(matcher.group(3)))

            } else {
                throw RuntimeException("Invalid dice expression: $expression")
            }
        }

        private fun toi(str: String?, defval: String = "0"): Int {
            return Integer.parseInt(when {
                str.isNullOrBlank() -> defval.trim()
                else -> str!!.trim()
            })
        }
    }

    fun roll() = (0 until rolls).map { rng.nextInt(die) + 1 }.sum() + modifier

    override fun toString(): String {
        val rstr = when {
            rolls > 1 -> rolls.toString()
            else -> ""
        }

        val mod = when {
            modifier > 0 -> "+$modifier"
            else -> ""
        }

        return "${rstr}d$die$mod"
    }
}
