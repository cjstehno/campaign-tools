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

data class Dice(private val expression: String) {

    private val rolls: Int
    private val die: Int
    private val modifier: Int

    init {
        val (r, d, m) = when (expression.contains('+')) {
            true -> expression
            else -> "$expression+0"
        }.split(PATTERN)


        rolls = when {
            r.isBlank() -> 1
            else -> parseInt(r.trim())
        }
        die = parseInt(d.trim())
        modifier = parseInt(m.trim())
    }

    companion object {
        private val rng = SecureRandom()
        private val PATTERN = Pattern.compile("[d+]")

        val D20 = Dice("d20")
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
