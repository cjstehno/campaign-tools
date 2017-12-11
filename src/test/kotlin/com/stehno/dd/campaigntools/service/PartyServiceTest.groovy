package com.stehno.dd.campaigntools.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.stehno.dd.campaigntools.model.ClassLevel
import com.stehno.dd.campaigntools.model.PartyMember
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification

class PartyServiceTest extends Specification {

    @Rule TemporaryFolder folder = new TemporaryFolder()

    private final ObjectMapper mapper = new ObjectMapper().with {
        registerModule(new KotlinModule())
    }

    def 'loading party: no existing files'() {
        setup:
        PartyService service = new PartyService(folder.root, mapper)

        expect:
        new File(folder.root, "party-members.json").exists()

        and:
        service.retrieveAll().isEmpty()
    }

    def 'loading party: with existing files'() {
        setup:
        def file = new File(folder.root, 'party-members.json')
        file.text = '[{"id":123,"characterName":"Braak","playerName":"Chris","classes":[{"name":"Barbarian","level":8}],"race":"Half-orc","alignment":"Chaotic good","armorClass":16,"perception":10}]'

        PartyService service = new PartyService(folder.root, mapper)

        when:
        def members = service.retrieveAll()

        then:
        members.size() == 1
        members[0] == new PartyMember(123, 'Braak', 'Chris', [new ClassLevel('Barbarian', 8)], 'Half-orc', 'Chaotic good', 16, 10)
    }
}
