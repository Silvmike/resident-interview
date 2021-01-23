package ru.silvmike.interview.resident.average.service.h2;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.silvmike.interview.resident.average.service.average.AverageProvider;
import ru.silvmike.interview.resident.average.service.average.dto.Average;
import ru.silvmike.interview.resident.average.service.average.dto.AverageRequest;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.List;

/**
 * H2-based {@link AverageProvider} implementation.
 */
public class H2AverageProvider implements AverageProvider {

    private final NamedParameterJdbcTemplate jt;
    private final H2SQLProvider sqlProvider;

    public H2AverageProvider(DataSource dataSource, H2SQLProvider sqlProvider) {
        this.jt = new NamedParameterJdbcTemplate(dataSource);
        this.sqlProvider = sqlProvider;
    }

    @Override
    public Average getAverage(AverageRequest averageRequest) {

        List<Average> averageList =
            jt.query(

                sqlProvider.getSummary(),

                new MapSqlParameterSource()
                    .addValue("type", averageRequest.getEventType())
                    .addValue("left", averageRequest.getFrom())
                    .addValue("right", averageRequest.getTo()),

                AverageRowMapper.INSTANCE
            );

        if (!averageList.isEmpty()) return averageList.get(0);
        return new Average(averageRequest.getEventType(), BigDecimal.ZERO, 0);
    }
}
