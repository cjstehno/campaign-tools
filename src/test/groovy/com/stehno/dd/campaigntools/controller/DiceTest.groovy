package com.stehno.dd.campaigntools.controller

import spock.lang.Specification

class DiceTest extends Specification {

    def '4d6+20'() {
        when:
        Dice dice = Dice.parse(expr)

        then:
        dice.toString() == string

        where:
        expr      || string
        '4d6+20'  || '4d6+20'
        '2d6'     || '2d6'
        '1d6'     || 'd6'
        'd6'      || 'd6'
        ' 3d6+2 ' || '3d6+2'
        '2d100'   || '2d100'
    }
}
