package com.stehno.dd.campaigntools.service

import com.stehno.dd.campaigntools.TestingDatabase
import com.stehno.dd.campaigntools.model.Condition
import com.stehno.dd.campaigntools.model.MonsterEncounterParticipant
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
//        service.addMonsterParticipant(1, new MonsterEncounterParticipant(null, 10, "Bugs", 10, 12, [] as Set<Condition>))

        when:
        def encounters = service.retrieveAllEncounters()

        then:
        encounters.size() == 1
    }
}
