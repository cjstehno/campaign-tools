/*
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
import spock.lang.Specification
import spock.lang.Unroll

class DiceTest extends Specification {

    @Unroll 'parse: #expr'() {
        when:
        Dice dice = Dice.parse(expr)

        then:
        dice.toString() == string

        where:
        expr      || string
        '4d6+20'  || '4d6+20'
        '2d6'     || '2d6'
        '1d6'     || 'd6'
        'd6'      || 'd6'
        ' 3d6+2 ' || '3d6+2'
        '2d100'   || '2d100'
        '10d10'   || '10d10'
    }
}
