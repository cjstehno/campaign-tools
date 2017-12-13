package com.stehno.dd.campaigntools.service

import com.stehno.dd.campaigntools.model.*
import com.stehno.dd.campaigntools.model.ParticipantType.MONSTER
import com.stehno.dd.campaigntools.model.ParticipantType.PARTY_MEMBER
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.ResultSetExtractor
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.SingleColumnRowMapper
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import java.sql.ResultSet
import java.util.*

@Service
class EncounterService(@Autowired private val repository: EncounterRepository) {

    fun retrieveAllEncounters(): List<Encounter> {
        return repository.retrieveAll()
    }

    fun retrieveEncounter(encounterId: Long): Encounter? {
        return repository.retrieve(encounterId)
    }

    fun removeParticipant(encounterId: Long, participantId: Long) {
        repository.removeParticipant(encounterId, participantId)
    }

    fun addMonsterParticipant(encounterId: Long, description: String, initiative: Int, armorClass: Int, hitPoints: Int) {
        repository.addParticipant(
            encounterId,
            EncounterParticipant(null, null, MONSTER, initiative, description, armorClass, hitPoints, sortedSetOf())
        )
    }

    fun addPartyParticipant(encounterId: Long, member: PartyMember, initiative: Int) {
        repository.addParticipant(
            encounterId,
            EncounterParticipant(null, member.id, PARTY_MEMBER, initiative, member.displayName, member.armorClass, null, sortedSetOf())
        )
    }

    fun adjustHp(encounterId: Long, participantId: Long, hitPoints: Int?) {
        repository.updateHitPoints(encounterId, participantId, hitPoints)
    }

    fun updateDescription(encounterId: Long, participantId: Long, description: String) {
        repository.updateDescription(encounterId, participantId, description)
    }

    fun updateConditions(encounterId: Long, participantId: Long, conditions: Array<String>) {
        repository.updateConditions(encounterId, participantId, conditions)
    }

    fun startEncounter(encounterId: Long) {
        repository.start(encounterId)
    }

    fun nextParticipant(encounterId: Long) {
        repository.next(encounterId)
    }

    fun stopEncounter(encounterId: Long) {
        repository.stop(encounterId)
    }

    fun addEncounter(name: String) {
        repository.add(name)
    }

    fun removeEncounter(encounterId: Long) {
        repository.remove(encounterId)
    }
}

@Repository
class EncounterRepository(@Autowired private val jdbcTemplate: JdbcTemplate) {

    companion object {
        private const val INSERT_SQL = "INSERT INTO encounter (name) VALUES (?)"
        private const val SELECT_SQL = """SELECT
                e.id AS id,e.name AS name,e.finished AS finished,e.round AS round,e.active_id AS active_id,
                p.id AS participant_id, p.ref_id AS participant_ref_id, p.type AS participant_type,p.initiative AS participant_initiative,p.description AS participant_description,
                p.armor_class AS participant_armor_class ,p.hit_points AS participant_hit_points,p.conditions AS participant_conditions
                FROM encounter e LEFT OUTER JOIN encounter_participants p ON e.id = p.encounter_id
            """
        private const val INSERT_PARTICIPANT_SQL = "INSERT INTO encounter_participants (encounter_id, ref_id, type, initiative, description, armor_class, hit_points, conditions) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
        private const val REMOVE_PARTICIPANT_SQL = "DELETE FROM encounter_participants WHERE encounter_id=? AND id=?"
        private const val REMOVE_ENCOUNTER_SQL = "DELETE FROM encounter_participants WHERE encounter_id=?"
        private const val REMOVE_PARTICIPANTS_SQL = "DELETE FROM encounter WHERE id=?"
        private const val UPDATE_HP_SQL = "UPDATE encounter_participants SET hit_points=? WHERE encounter_id=? AND id=?"
        private const val UPDATE_DESC_SQL = "UPDATE encounter_participants SET description=? WHERE encounter_id=? AND id=?"
        private const val UPDATE_CONDITIONS_SQL = "UPDATE encounter_participants SET conditions=? WHERE encounter_id=? AND id=?"
        private const val START_ENCOUNTER_SQL = "UPDATE encounter SET round=1, active_id=(SELECT id FROM encounter_participants WHERE encounter_id=? ORDER BY initiative DESC LIMIT 1) WHERE id=?"
        private const val SELECT_ENCOUNTER_IDS_SQL = "SELECT id FROM encounter_participants WHERE encounter_id=? ORDER BY initiative DESC"
        private const val SELECT_ACTIVE_ID_SQL = "SELECT active_id FROM encounter WHERE id=?"
        private const val NEXT_ENCOUNTER_SQL = "UPDATE encounter SET round=round+?, active_id=? WHERE id=?"
        private const val STOP_ENCOUNTER_SQL = "UPDATE encounter SET finished=TRUE, active_id=NULL WHERE id=?"
    }

    fun retrieveAll(): List<Encounter> {
        return jdbcTemplate.query(
            SELECT_SQL,
            EncounterListResultSetExtractor.INSTANCE
        )
    }

    fun retrieve(encounterId: Long): Encounter {
        return jdbcTemplate.query("$SELECT_SQL where e.id = ?", EncounterResultSetExtractor.INSTANCE, encounterId)
    }

    fun add(name: String) {
        jdbcTemplate.update(INSERT_SQL, name)
    }

    fun addParticipant(encounterId: Long, participant: EncounterParticipant) {
        jdbcTemplate.update(
            INSERT_PARTICIPANT_SQL,
            encounterId,
            participant.refId,
            participant.type.name,
            participant.initiative,
            participant.description,
            participant.armorClass,
            participant.hitPoints,
            participant.conditions.map { it.name }.toTypedArray()
        )
    }

    fun removeParticipant(encounterId: Long, participantId: Long) {
        jdbcTemplate.update(REMOVE_PARTICIPANT_SQL, encounterId, participantId)
    }

    fun remove(encounterId: Long) {
        if (jdbcTemplate.update(REMOVE_ENCOUNTER_SQL, encounterId) > 0) {
            jdbcTemplate.update(REMOVE_PARTICIPANTS_SQL, encounterId)
        }
    }

    fun updateHitPoints(encounterId: Long, participantId: Long, hitPoints: Int?) {
        jdbcTemplate.update(UPDATE_HP_SQL, hitPoints, encounterId, participantId)
    }

    fun updateDescription(encounterId: Long, participantId: Long, description: String) {
        jdbcTemplate.update(UPDATE_DESC_SQL, description, encounterId, participantId)
    }

    fun updateConditions(encounterId: Long, participantId: Long, conditions: Array<String>) {
        jdbcTemplate.update(UPDATE_CONDITIONS_SQL, conditions, encounterId, participantId)
    }

    fun start(encounterId: Long) {
        jdbcTemplate.update(
            START_ENCOUNTER_SQL,
            encounterId, encounterId
        )
    }

    fun next(encounterId: Long) {
        val participants = jdbcTemplate.query(SELECT_ENCOUNTER_IDS_SQL, SingleColumnRowMapper<Long>(Long::class.java), encounterId)
        val currentActiveId = jdbcTemplate.queryForObject(SELECT_ACTIVE_ID_SQL, SingleColumnRowMapper<Long>(Long::class.java), encounterId)

        var index = participants.indexOfFirst { it == currentActiveId } + 1
        var roundAdjustment = 0
        if (index >= participants.size) {
            index = 0
            roundAdjustment = 1
        }
        val activeId = participants[index]

        jdbcTemplate.update(NEXT_ENCOUNTER_SQL, roundAdjustment, activeId, encounterId)
    }

    fun stop(encounterId: Long) {
        jdbcTemplate.update(
            STOP_ENCOUNTER_SQL,
            encounterId
        )
    }
}

class EncounterRowMapper : RowMapper<Encounter> {

    companion object {
        val INSTANCE = EncounterRowMapper()
    }

    override fun mapRow(rs: ResultSet, rowNum: Int): Encounter {
        return Encounter(
            rs.getLong("id"),
            rs.getString("name"),
            sortedSetOf(),
            rs.getBoolean("finished"),
            rs.getObject("round") as Int?,
            rs.getObject("active_id") as Long?
        )
    }
}

class EncounterParticipantRowMapper : RowMapper<EncounterParticipant> {

    companion object {
        val INSTANCE = EncounterParticipantRowMapper()

        private const val FIELD_PREFIX = "participant_"

        @Suppress("UNCHECKED_CAST")
        private fun extractArray(rs: ResultSet, fieldName: String): Set<Condition> {
            return (rs.getArray(fieldName)?.array as Array<String>).map { Condition.valueOf(it) }.toSet()
        }
    }

    override fun mapRow(rs: ResultSet, rowNum: Int): EncounterParticipant? {
        val id: Long? = rs.getLong("${FIELD_PREFIX}id")

        @Suppress("UNCHECKED_CAST")
        return when {
            id != null && id > 0 -> EncounterParticipant(
                id,
                rs.getLong("${FIELD_PREFIX}ref_id"),
                ParticipantType.valueOf(rs.getString("${FIELD_PREFIX}type")),
                rs.getInt("${FIELD_PREFIX}initiative"),
                rs.getString("${FIELD_PREFIX}description"),
                rs.getInt("${FIELD_PREFIX}armor_class"),
                rs.getInt("${FIELD_PREFIX}hit_points"),
                extractArray(rs, "${FIELD_PREFIX}conditions")
            )
            else -> null
        }
    }
}

class EncounterResultSetExtractor : ResultSetExtractor<Encounter> {

    companion object {
        val INSTANCE = EncounterResultSetExtractor()
    }

    override fun extractData(rs: ResultSet): Encounter? {
        var encounter: Encounter? = null
        val participants = mutableMapOf<Long, MutableSet<EncounterParticipant>>()

        while (rs.next()) {
            val encounterId = rs.getLong("id")

            if (encounter == null) {
                encounter = EncounterRowMapper.INSTANCE.mapRow(rs, 0)
            }

            val participant = EncounterParticipantRowMapper.INSTANCE.mapRow(rs, 0)
            if (participant != null) {
                val encounterParticipants = participants.computeIfAbsent(encounterId) { _ -> mutableSetOf() }
                encounterParticipants.add(participant)
            }
        }

        return encounter?.updateParticipants(TreeSet(participants[encounter.id]))
    }
}

class EncounterListResultSetExtractor : ResultSetExtractor<List<Encounter>> {

    companion object {
        val INSTANCE = EncounterListResultSetExtractor()
    }

    override fun extractData(rs: ResultSet): List<Encounter> {
        val encounters = mutableMapOf<Long, Encounter>()
        val participants = mutableMapOf<Long, MutableSet<EncounterParticipant>>()

        while (rs.next()) {
            val encounterId = rs.getLong("id")
            if (!encounters.containsKey(encounterId)) {
                encounters[encounterId] = EncounterRowMapper.INSTANCE.mapRow(rs, 0)
            }

            val participant = EncounterParticipantRowMapper.INSTANCE.mapRow(rs, 0)
            if (participant != null) {
                val encounterParticipants = participants.computeIfAbsent(encounterId) { _ -> mutableSetOf() }
                encounterParticipants.add(participant)
            }
        }

        return encounters.map { entry ->
            when {
                participants.isNotEmpty() -> entry.value.updateParticipants(TreeSet(participants[entry.key]))
                else -> entry.value
            }
        }
    }
}