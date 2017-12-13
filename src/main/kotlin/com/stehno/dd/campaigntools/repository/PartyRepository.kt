package com.stehno.dd.campaigntools.repository

import com.stehno.dd.campaigntools.model.PartyMember
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import java.sql.ResultSet

@Repository
class PartyRepository(@Autowired private val jdbcTemplate: JdbcTemplate) {

    companion object {
        private const val RETRIEVE_ALL_SQL = "SELECT id,character_name,player_name,class_level,race,alignment,armor_class,perception FROM party_member"
        private const val RETRIEVE_ONE_SUFFIX = "where id=?"
        private const val REMOVE_SQL = "DELETE FROM party_member WHERE id=?"
        private const val INSERT_SQL = "INSERT INTO party_member (character_name,player_name,class_level,race,alignment,armor_class,perception) VALUES (?,?,?,?,?,?,?)"
    }

    fun add(member: PartyMember) {
        jdbcTemplate.update(
            INSERT_SQL,
            member.characterName,
            member.playerName,
            member.classLevel,
            member.race,
            member.alignment,
            member.armorClass,
            member.perception
        )
    }

    fun remove(memberId: Long) {
        jdbcTemplate.update(REMOVE_SQL, memberId)
    }

    fun retrieveAll(): List<PartyMember> {
        return jdbcTemplate.query(RETRIEVE_ALL_SQL, PartyMemberRowMapper.INSTANCE)
    }

    fun retrieve(memberId: Long): PartyMember {
        return jdbcTemplate.queryForObject("$RETRIEVE_ALL_SQL $RETRIEVE_ONE_SUFFIX", PartyMemberRowMapper.INSTANCE, memberId)
    }
}

class PartyMemberRowMapper : RowMapper<PartyMember> {

    companion object {
        val INSTANCE = PartyMemberRowMapper()
    }

    override fun mapRow(rs: ResultSet?, rowNum: Int): PartyMember = PartyMember(
        rs!!.getLong("id"),
        rs.getString("character_name"),
        rs.getString("player_name"),
        rs.getString("class_level"),
        rs.getString("race"),
        rs.getString("alignment"),
        rs.getInt("armor_class"),
        rs.getInt("perception")
    )
}