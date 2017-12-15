/**
 * Copyright (C) 2017 Christopher J. Stehno <chris@stehno.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
        private const val RETRIEVE_ALL_SQL = "SELECT id,name,page,armor_class,hit_dice,experience_points FROM monsters"
        private const val ADD_SQL = "INSERT INTO monsters (name,page,armor_class,hit_dice,experience_points) VALUES (?,?,?,?,?)"
        private const val REMOVE_SQL = "DELETE FROM monsters WHERE id=?"
        private const val UPDATE_SQL = "UPDATE monsters SET name=?,page=?,armor_class=?,hit_dice=?,experience_points=? WHERE id=?"
    }

    fun retrieveAll(): List<Monster> = jdbcTemplate.query("$RETRIEVE_ALL_SQL ORDER BY name ASC", MonsterRowMapper.INSTANCE)

    fun retrieve(monsterId: Long): Monster = jdbcTemplate.queryForObject("$RETRIEVE_ALL_SQL where id=?", MonsterRowMapper.INSTANCE, monsterId)

    fun add(monster: Monster) = jdbcTemplate.update(ADD_SQL, monster.name, monster.page, monster.armorClass, monster.hitDice.toString(), monster.experiencePoints)

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