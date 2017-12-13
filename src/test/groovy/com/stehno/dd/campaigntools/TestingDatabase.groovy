/*
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
package com.stehno.dd.campaigntools

import groovy.util.logging.Slf4j
import org.flywaydb.core.Flyway
import org.h2.jdbcx.JdbcConnectionPool
import org.junit.rules.ExternalResource
import org.springframework.jdbc.core.JdbcTemplate

@Slf4j
class TestingDatabase extends ExternalResource {

    private final JdbcConnectionPool pool = JdbcConnectionPool.create("jdbc:h2:mem:test", "sa", "sa")
    final JdbcTemplate template = new JdbcTemplate(pool)
    final Flyway flyway = new Flyway(dataSource: pool)

    @Override
    protected void before() throws Throwable {
        rebuildDatabase()
    }

    @Override
    protected void after() {
        pool.dispose()
    }

    private void rebuildDatabase() {
        log.debug 'Rebuilding the database...'
        flyway.clean()
        flyway.migrate()
    }
}