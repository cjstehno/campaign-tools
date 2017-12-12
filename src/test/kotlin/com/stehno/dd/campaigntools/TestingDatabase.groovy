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

import org.h2.jdbcx.JdbcConnectionPool
import org.junit.rules.ExternalResource
import org.springframework.jdbc.core.JdbcTemplate

class TestingDatabase extends ExternalResource {

    private final JdbcConnectionPool pool = JdbcConnectionPool.create("jdbc:h2:mem:test", "sa", "sa")
    final JdbcTemplate template = new JdbcTemplate(pool)

    @Override
    protected void after() {
        pool.dispose()
    }
}