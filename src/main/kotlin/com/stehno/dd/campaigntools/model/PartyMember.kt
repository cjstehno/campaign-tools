package com.stehno.dd.campaigntools.model


data class PartyMember(val id: Long,
                       val characterName: String,
                       val playerName: String,
                       val classes: List<ClassLevel>,
                       val race: String,
                       val alignment: String,
                       val armorClass: Int,
                       val perception: Int)

data class ClassLevel(val name: String, val level: Int)