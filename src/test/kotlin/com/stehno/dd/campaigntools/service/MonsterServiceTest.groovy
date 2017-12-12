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
package com.stehno.dd.campaigntools.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import spock.lang.Specification

class MonsterServiceTest extends Specification {

    def 'cached monster list'() {
        setup:
        ObjectMapper mapper = new ObjectMapper()
        mapper.registerModule(new KotlinModule())

        MonsterService service = new MonsterService(mapper)

        when:
        List<MonsterUrl> monsters = service.retrieveMonsterList()

        then:
        monsters.size() == 325
        monsters[3] == new MonsterUrl('Adult Blue Dracolich', new URL('http://www.dnd5eapi.co/api/monsters/4'))
    }
}
