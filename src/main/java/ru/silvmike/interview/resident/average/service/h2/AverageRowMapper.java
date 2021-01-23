package ru.silvmike.interview.resident.average.service.h2;

import org.springframework.jdbc.core.RowMapper;
import ru.silvmike.interview.resident.average.service.average.dto.Average;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;

class AverageRowMapper implements RowMapper<Average> {

    static final AverageRowMapper INSTANCE = new AverageRowMapper();

    @Override
    public Average mapRow(ResultSet resultSet, int i) throws SQLException {

        String name = resultSet.getString("EVENT_TYPE");
        BigDecimal value = resultSet.getBigDecimal("VALUE_SUM");
        long count = resultSet.getLong("COUNT");

        BigDecimal avg = calculateAverage(value, count);

        return new Average(name, avg, count);
    }

    private BigDecimal calculateAverage(BigDecimal value, long count) {

        return value.divide(BigDecimal.valueOf(count), 4, RoundingMode.UP);
    }
}
