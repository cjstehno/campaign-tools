package com.stehno.dd.campaigntools.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

class EncounterServiceTest extends Specification {

    @Rule TemporaryFolder folder = new TemporaryFolder()

    private final ObjectMapper mapper = new ObjectMapper().with {
        registerModule(new KotlinModule())
    }

    def 'persisting with no files'() {
        setup:
        EncounterService service = new EncounterService(folder.root, mapper)

        when:
        service.addEncounter('Testing')

        then:
        def file = new File(folder.root, "encounters.json")
        file.exists()
    }

    def 'persisting to existing file'(){
        setup:
        folder.newFile("encounters.json").text = '{"1":{"id":1,"name":"Testing","participants":[],"finished":false,"round":null,"activeId":null}}'

        EncounterService service = new EncounterService(folder.root, mapper)

        when:
        service.addEncounter('Tomb of Horrors')

        then:
        def file = new File(folder.root, "encounters.json")
        def map = mapper.readValue(file, Map)
        map.size() == 2

        map["1"].name == 'Testing'
        map["2"].name == 'Tomb of Horrors'

        and:
        service.retrieveAllEncounters()[1].name == 'Tomb of Horrors'
    }
}
