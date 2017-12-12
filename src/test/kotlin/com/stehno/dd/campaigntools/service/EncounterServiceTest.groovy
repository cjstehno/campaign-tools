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
import com.stehno.dd.campaigntools.model.Encounter
import com.stehno.dd.campaigntools.model.EncounterParticipant
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

class EncounterServiceTest extends Specification {

    @Rule TemporaryFolder folder = new TemporaryFolder()
    @Rule TestingDatabase database = new TestingDatabase()

    private final EncounterRepository repository = new EncounterRepository(database.template)
    private final EncounterService service = new EncounterService(repository)

    def 'retrieve all (empty)'() {
        expect:
        service.retrieveAllEncounters().empty
    }

    def 'add and retrieve (encounter with no content)'(){
        setup:
        service.addEncounter('Unit Test of Horrors')

        when:
        def encounters = service.retrieveAllEncounters()

        then:
        encounters.size() == 1

        and:
        encounters[0] == new Encounter(1, 'Unit Test of Horrors', new TreeSet<EncounterParticipant>(), false, null, null)
    }

    def 'add and retrieve (encounter with content)'(){
        setup:
        service.addEncounter('Unit Test of Horrors')
        service.addMonsterParticipant(1, "Bug", 18, 17, 123)

        when:
        def encounters = service.retrieveAllEncounters()

        then:
        encounters.size() == 1

        and:
        encounters[0] == new Encounter(1, 'Unit Test of Horrors', new TreeSet<EncounterParticipant>(), false, null, null)
    }
}
