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
import com.stehno.dd.campaigntools.TestingDatabase
import com.stehno.dd.campaigntools.model.Monster
import com.stehno.dd.campaigntools.repository.MonsterRepository
import org.junit.Rule
import spock.lang.Specification

class MonsterServiceTest extends Specification {

    @Rule TestingDatabase database = new TestingDatabase()

    private final MonsterRepository repository = new MonsterRepository(database.template)
    private final MonsterService service = new MonsterService(repository, new ObjectMapper().with { registerModule(new KotlinModule()) })

    def 'retrieveAll with none'() {
        expect:
        service.retrieveAll().empty
    }

    def 'add and retrieve and remove'() {
        setup:
        Monster expectedMonster = new Monster(1, 'Bunny', 'MPHG', 10, '2d6+1', 1000)

        service.addMonster(new Monster(null, 'Bunny', 'MPHG', 10, '2d6+1', 1000))

        when:
        def monsters = service.retrieveAll()

        then:
        monsters.size() == 1

        and:
        monsters[0] == expectedMonster

        when:
        def member = service.retrieveMonster(1)

        then:
        member == expectedMonster

        when:
        service.removeMonster(1)

        then:
        service.retrieveAll().empty
    }
}
