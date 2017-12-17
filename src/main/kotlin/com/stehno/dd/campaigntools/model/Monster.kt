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


data class Monster(val id: Long?,
                   val name: String,
                   val page: String,
                   val armorClass: Int,
                   val hitDice: Dice,
                   val experiencePoints: Int) {

    constructor(id: Long?,
                name: String,
                page: String,
                armorClass: Int,
                diceExpr: String,
                experiencePoints: Int) : this(id, name, page, armorClass, Dice(diceExpr), experiencePoints)
}