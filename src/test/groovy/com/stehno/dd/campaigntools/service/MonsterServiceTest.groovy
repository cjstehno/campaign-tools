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
