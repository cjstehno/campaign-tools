package com.stehno.dd.campaigntools.service

import com.stehno.dd.campaigntools.model.ClassLevel
import com.stehno.dd.campaigntools.model.PartyMember
import org.springframework.stereotype.Service

@Service
class PartyService {

    fun retrieveAll(): List<PartyMember> {
        return arrayListOf(
            PartyMember(100, "Braak", "Chris", arrayListOf(ClassLevel("Barbarian", 8)), "Half-Orc", "Chaotic Good", 12, 11),
            PartyMember(200, "Venturan", "Derek", arrayListOf(ClassLevel("Fighter", 8)), "Human", "Lawful Good", 15, 9)
        )
    }
}