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
package com.stehno.dd.campaigntools.model

import java.lang.Integer.parseInt
import java.security.SecureRandom
import java.util.regex.Pattern

data class Dice(private val rolls: Int, private val die: Int, private val modifier: Int) {

    companion object {
        private val rng = SecureRandom()
        private val PATTERN = Pattern.compile("[d+]")

        @JvmStatic
        fun parse(expression: String): Dice {
            val (r, d, m) = when (expression.contains('+')) {
                true -> expression
                else -> "$expression+0"
            }.split(PATTERN)

            return Dice(
                when {
                    r.isBlank() -> 0
                    else -> parseInt(r.trim())
                },
                parseInt(d.trim()),
                parseInt(m.trim())
            )
        }

        private fun toi(str: String?, defval: String = "0"): Int {
            return parseInt(when {
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
