package com.stehno.dd.campaigntools.service

import com.stehno.dd.campaigntools.model.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.ResultSetExtractor
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import java.sql.ResultSet

@Service
class EncounterService(@Autowired private val repository: EncounterRepository) {

    fun retrieveAllEncounters(): List<Encounter> {
        return repository.retrieveAll()
    }

    fun retrieveEncounter(encounterId: Long): Encounter? {
        return null //repository.retrieve(encounterId)
    }

    fun removeParticipant(encounterId: Long, participantId: Long) {
//        val encounter: Encounter? = encounters[encounterId]
//        if (encounter != null) {
//            encounters[encounterId] = encounter.updateParticipants(TreeSet(encounter.participants.filter { it.id != participantId }))
//        }
    }

    fun addMonsterParticipant(encounterId: Long, description: String, initiative: Int, armorClass: Int, hitPoints: Int) {
        repository.addParticipant(
            encounterId,
            EncounterParticipant(null, null, ParticipantType.MONSTER, initiative, description, armorClass, hitPoints, sortedSetOf())
        )
    }

    fun addPartyParticipant(encounterId: Long, partyMember: PartyMember, initiative: Int) {
//        val encounter: Encounter? = encounters[encounterId]
//        if (encounter != null) {
//            val nextId = encounter.participants.maxBy { it.id }?.id ?: 1
//
//            val updated = encounter.participants.toMutableSet()
//            updated.add(PartyMemberEncounterParticipant(partyMember, nextId, initiative, setOf()))
//
//            encounters[encounterId] = encounter.updateParticipants(TreeSet(updated))
//        }
    }

    fun adjustHp(encounterId: Long, participantId: Long, hitPoints: Int?) {
//        val encounter: Encounter? = encounters[encounterId]
//        if (encounter != null) {
//            val participants = TreeSet<EncounterParticipant>(encounter.participants.map { p ->
//                MonsterEncounterParticipant(
//                    p.id,
//                    p.initiative,
//                    p.description,
//                    p.armorClass,
//                    if (p.id == participantId) {
//                        hitPoints
//                    } else {
//                        p.hitPoints
//                    },
//                    p.conditions
//                )
//            })
//
//            encounters[encounterId] = encounter.updateParticipants(participants)
//        }
    }

    fun updateDescription(encounterId: Long, participantId: Long, description: String?) {
//        val encounter: Encounter? = encounters[encounterId]
//        if (encounter != null) {
//            val participants = TreeSet<EncounterParticipant>(encounter.participants.map { p ->
//                MonsterEncounterParticipant(
//                    p.id,
//                    p.initiative,
//                    if (!description.isNullOrBlank() && p.id == participantId) {
//                        description!!
//                    } else {
//                        p.description
//                    },
//                    p.armorClass,
//                    p.hitPoints,
//                    p.conditions
//                )
//            })
//
//            encounters[encounterId] = encounter.updateParticipants(participants)
//        }
    }

    fun updateConditions(encounterId: Long, participantId: Long, conditions: Array<String>?) {
//        val encounter: Encounter? = encounters[encounterId]
//        if (encounter != null) {
//            val participants = TreeSet<EncounterParticipant>(encounter.participants.map { p ->
//                MonsterEncounterParticipant(
//                    p.id,
//                    p.initiative,
//                    p.description,
//                    p.armorClass,
//                    p.hitPoints,
//                    if (conditions != null && p.id == participantId) {
//                        TreeSet(conditions.map { c -> Condition.valueOf(c) })
//                    } else {
//                        p.conditions
//                    }
//                )
//            })
//
//            encounters[encounterId] = encounter.updateParticipants(participants)
//        }
    }

    fun startEncounter(encounterId: Long) {
//        val encounter: Encounter? = encounters[encounterId]
//        if (encounter != null) {
//            encounters[encounterId] = encounter.start()
//        }
    }

    fun nextParticipant(encounterId: Long) {
//        val encounter: Encounter? = encounters[encounterId]
//        if (encounter != null) {
//            encounters[encounterId] = encounter.next()
//        }
    }

    fun stopEncounter(encounterId: Long) {
//        val encounter: Encounter? = encounters[encounterId]
//        if (encounter != null) {
//            encounters[encounterId] = encounter.stop()
//        }
    }

    fun addEncounter(name: String) {
        repository.add(name)
    }

    fun removeEncounter(encounterId: Long) {
//        encounters.remove(encounterId)
    }
}

@Repository
class EncounterRepository(@Autowired private val jdbcTemplate: JdbcTemplate) {

    companion object {
        private const val INSERT_SQL = "INSERT INTO encounter (name) VALUES (?)"
        private const val SELECT_SQL = """SELECT
                e.id AS id,e.name AS name,e.finished AS finished,e.round AS round,e.active_id AS active_id,
                p.id AS participant_id, p.ref_id as participant_ref_id, p.type AS participant_type,p.initiative AS participant_initiative,p.description AS participant_description,
                p.armor_class AS participant_armor_class ,p.hit_points AS participant_hit_points,p.conditions AS participant_conditions
                FROM encounter e LEFT OUTER JOIN encounter_participants p ON e.id = p.encounter_id
            """
    }

    init {
        // create the table if not existing
        jdbcTemplate.execute("""
            CREATE TABLE IF NOT EXISTS encounter (
                id BIGINT PRIMARY KEY AUTO_INCREMENT,
                name VARCHAR(30) NOT NULL ,
                finished BOOLEAN NOT NULL DEFAULT FALSE ,
                round INT DEFAULT NULL,
                active_id BIGINT DEFAULT NULL
            )
        """)

        jdbcTemplate.execute("""
            CREATE TABLE IF NOT EXISTS encounter_participants (
                encounter_id BIGINT REFERENCES encounter (id),
                id BIGINT PRIMARY KEY AUTO_INCREMENT,
                ref_id BIGINT,
                type VARCHAR(20) NOT NULL,
                initiative INT NOT NULL,
                description VARCHAR(30) NOT NULL,
                armor_class INT NOT NULL ,
                hit_points INT NOT NULL ,
                conditions ARRAY NOT NULL
            )
        """)
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

    fun update(encounter: Encounter) {}

    fun remove(encounterId: Long) {}

    fun addParticipant(encounterId: Long, participant: EncounterParticipant) {
        jdbcTemplate.update(
            "insert into encounter_participants (encounter_id, ref_id, type, initiative, description, armor_class, hit_points, conditions) values (?, ?, ?, ?, ?, ?, ?, ?)",
            encounterId,
            participant.refId,
            participant.type.name,
            participant.initiative,
            participant.description,
            participant.armorClass,
            participant.hitPoints,
            participant.conditions.map { it.name }
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
                (rs.getArray("${FIELD_PREFIX}conditions")?.array as Array<*>).map {
                    Condition.valueOf(it as String)
                    this needs more love
                }.toSet()
            )
            else -> null
        }
    }
}

class EncounterResultSetExtractor : ResultSetExtractor<Encounter> {

    companion object {
        val INSTANCE = EncounterResultSetExtractor()
    }

    override fun extractData(rs: ResultSet?): Encounter {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

class EncounterListResultSetExtractor : ResultSetExtractor<List<Encounter>> {

    companion object {
        val INSTANCE = EncounterListResultSetExtractor()
    }

    override fun extractData(rs: ResultSet?): List<Encounter> {
        val encounters = mutableMapOf<Long, Encounter>()
        val participants = sortedSetOf<EncounterParticipant>() // separate by encounter id too (map)

        while (rs!!.next()) {
            val encounterId = rs.getLong("id")
            if (!encounters.containsKey(encounterId)) {
                encounters[encounterId] = EncounterRowMapper.INSTANCE.mapRow(rs, 0)
            }

            val participant = EncounterParticipantRowMapper.INSTANCE.mapRow(rs, 0)
            if (participant != null) {
                participants.add(participant)
            }
        }

        return encounters.values.toList()
    }
}