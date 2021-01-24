package ru.silvmike.interview.resident.average.service.h2;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

class H2CleanUpHelper {

    private final JdbcTemplate jt;
    private final H2SQLProvider sqlProvider;

    public H2CleanUpHelper(DataSource dataSource, H2SQLProvider sqlProvider) {
        this.jt = new JdbcTemplate(dataSource);
        this.sqlProvider = sqlProvider;
    }

    public void cleanUp() {
        jt.update(sqlProvider.getClean());
    }
}
