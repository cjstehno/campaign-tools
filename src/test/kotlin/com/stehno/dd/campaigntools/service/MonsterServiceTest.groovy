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
