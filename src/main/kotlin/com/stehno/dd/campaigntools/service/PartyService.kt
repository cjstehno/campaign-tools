package com.stehno.dd.campaigntools.service

import com.stehno.dd.campaigntools.model.PartyMember
import com.stehno.dd.campaigntools.repository.PartyRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PartyService(@Autowired private val repository: PartyRepository) {

    fun retrieveAll(): List<PartyMember> {
        return repository.retrieveAll()
    }

    fun retrieveMember(memberId: Long): PartyMember {
        return repository.retrieve(memberId)
    }

    fun addMember(member: PartyMember) {
        repository.add(member)
    }

    fun removeMember(memberId: Long) {
        repository.remove(memberId)
    }

    fun updateMember(member: PartyMember) {
        repository.update(member)
    }
}
