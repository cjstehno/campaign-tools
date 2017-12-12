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