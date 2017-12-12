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
