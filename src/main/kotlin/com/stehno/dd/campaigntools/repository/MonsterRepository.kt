package com.stehno.dd.campaigntools.repository

import com.stehno.dd.campaigntools.model.Monster
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import java.sql.ResultSet

@Repository
class MonsterRepository(@Autowired private val jdbcTemplate: JdbcTemplate) {

    companion object {
        private const val RETRIEVE_ALL_SQL = "SELECT id,name,page,armor_class,hit_dice,experience_points FROM monsters order by name asc"
        private const val ADD_SQL = "INSERT INTO monsters (name,page,armor_class,hit_dice,experience_points) VALUES (?,?,?,?,?)"
        private const val REMOVE_SQL = "DELETE FROM monsters WHERE id=?"
        private const val UPDATE_SQL = "UPDATE monsters SET name=?,page=?,armor_class=?,hit_dice=?,experience_points=? WHERE id=?"
    }

    fun retrieveAll() = jdbcTemplate.query(RETRIEVE_ALL_SQL, MonsterRowMapper.INSTANCE)

    fun retrieve(monsterId: Long) = jdbcTemplate.queryForObject("$RETRIEVE_ALL_SQL where id=?", MonsterRowMapper.INSTANCE, monsterId)

    fun add(monster: Monster) = jdbcTemplate.update(ADD_SQL, monster.name, monster.page, monster.armorClass, monster.hitDice, monster.experiencePoints)

    fun remove(monsterId: Long) = jdbcTemplate.update(REMOVE_SQL, monsterId)

    fun update(monster: Monster) = jdbcTemplate.update(
        UPDATE_SQL,
        monster.name, monster.page, monster.armorClass, monster.hitDice, monster.experiencePoints, monster.id
    )
}

class MonsterRowMapper : RowMapper<Monster> {

    companion object {
        val INSTANCE = MonsterRowMapper()
    }

    override fun mapRow(rs: ResultSet, rowNum: Int) = Monster(
        rs.getLong("id"),
        rs.getString("name"),
        rs.getString("page"),
        rs.getInt("armor_class"),
        rs.getString("hit_dice"),
        rs.getInt("experience_points")
    )
}