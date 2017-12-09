package com.stehno.dd.campaigntools.model


data class PartyMember(val id: Long,
                       val characterName: String,
                       val playerName: String,
                       val classes: List<ClassLevel>,
                       val race: String,
                       val alignment: String,
                       val armorClass: Int,
                       val perception: Int) {

    fun getDisplayName(): String {
        return "$characterName ($playerName)"
    }
}

data class ClassLevel(val name: String, val level: Int)