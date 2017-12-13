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

import com.stehno.dd.campaigntools.TestingDatabase
import com.stehno.dd.campaigntools.model.PartyMember
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

class PartyServiceTest extends Specification {

    @Rule TemporaryFolder folder = new TemporaryFolder()
    @Rule TestingDatabase database = new TestingDatabase()

    private final PartyRepository repository = new PartyRepository(database.template)
    private final PartyService service = new PartyService(repository)

    def 'retrieveAll with none'() {
        expect:
        service.retrieveAll().empty
    }

    def 'add and retrieve and remove'() {
        setup:
        PartyMember expectedMember = new PartyMember(1, 'Rogar', 'Bob', 'Fighter (5)', 'Human', 'Chaotic-good', 12, 8)

        service.addMember(new PartyMember(null, 'Rogar', 'Bob', 'Fighter (5)', 'Human', 'Chaotic-good', 12, 8))

        when:
        def members = service.retrieveAll()

        then:
        members.size() == 1

        and:
        members[0] == expectedMember

        when:
        def member = service.retrieveMember(1)

        then:
        member == expectedMember

        when:
        service.removeMember(1)

        then:
        service.retrieveAll().empty
    }
}
