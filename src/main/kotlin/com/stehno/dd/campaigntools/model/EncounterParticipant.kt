package com.stehno.dd.campaigntools.model

data class EncounterParticipant(val id: Long,
                                val active: Boolean,
                                val type: ParticipantType,
                                val initiative: Int,
                                val description: String,
                                val armorClass: Int,
                                val hitPoints: Int?,
                                val conditions: Set<String>,
                                val notes: String)

enum class ParticipantType(val id: String) {
    MONSTER("monster"),
    PARTY_MEMBER("party"),
    DEAD("dead")
}