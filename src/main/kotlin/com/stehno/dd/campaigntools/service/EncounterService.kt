package com.stehno.dd.campaigntools.service

import com.stehno.dd.campaigntools.model.Encounter
import com.stehno.dd.campaigntools.model.MonsterEncounterParticipant
import com.stehno.dd.campaigntools.model.PartyMember
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.ResultSetExtractor
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Component
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

    fun addMonsterParticipant(encounterId: Long, participant: MonsterEncounterParticipant) {
//        val encounter: Encounter? = encounters[encounterId]
//        if (encounter != null) {
//            val nextId = encounter.participants.maxBy { it.id }?.id ?: 1
//
//            val updated = encounter.participants.toMutableSet()
//            updated.add(MonsterEncounterParticipant(
//                nextId,
//                participant.initiative,
//                participant.description,
//                participant.armorClass,
//                participant.hitPoints,
//                participant.conditions
//            ))
//
//            encounters[encounterId] = encounter.updateParticipants(TreeSet(updated))
//        }
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
    }

    init {
        // create the table if not existing
        jdbcTemplate.execute("""
            CREATE TABLE IF NOT EXISTS encounter (
                id BIGINT PRIMARY KEY AUTO_INCREMENT,
                name VARCHAR(30) NOT NULL ,
                finished BOOLEAN NOT NULL DEFAULT FALSE ,
                round INT,
                active_id BIGINT
            )
        """)

        jdbcTemplate.execute("""
            CREATE TABLE IF NOT EXISTS encounter_participants (
                encounter_id BIGINT REFERENCES encounter (id),
                id BIGINT PRIMARY KEY AUTO_INCREMENT,
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
            """SELECT
                e.id AS id,e.name AS name,e.finished AS finished,e.round AS round,e.active_id AS active_id,
                p.id AS participant_id,p.type AS participant_type,p.initiative AS participant_initiative,p.description AS participant_description,
                p.armor_class AS participant_ac ,p.hit_points AS participant_hp,p.conditions AS participant_conditions
                FROM encounter e LEFT OUTER JOIN encounter_participants p ON e.id = p.encounter_id
            """,
            EncounterResultSetExtractor.INSTANCE
        )
    }

//    fun retrieve(encounterId: Long): Encounter {
//
//    }

    fun add(name: String) {
        jdbcTemplate.update(INSERT_SQL, name)
    }

    fun update(encounter: Encounter) {}

    fun remove(encounterId: Long) {}
}

@Component
class EncounterRowMapper : RowMapper<Encounter> {

    companion object {
        val INSTANCE = EncounterRowMapper()
    }

    override fun mapRow(rs: ResultSet?, rowNum: Int): Encounter {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

@Component
class EncounterResultSetExtractor : ResultSetExtractor<List<Encounter>> {

    companion object {
        val INSTANCE = EncounterResultSetExtractor()

        private val log = LoggerFactory.getLogger(EncounterResultSetExtractor::class.java)
    }

    override fun extractData(rs: ResultSet?): List<Encounter> {
        var count = 0
        while (rs!!.next()) {
            count++
        }

        log.debug("Count is {}", count)

        return listOf<Encounter>()
    }
}