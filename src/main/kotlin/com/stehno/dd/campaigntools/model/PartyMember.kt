package com.stehno.dd.campaigntools.model


data class PartyMember(val id: Long?,
                       val characterName: String,
                       val playerName: String,
                       val classLevel: String,
                       val race: String,
                       val alignment: String,
                       val armorClass: Int,
                       val perception: Int) {

    val displayName: String
        get() = "$characterName ($playerName)"
}

