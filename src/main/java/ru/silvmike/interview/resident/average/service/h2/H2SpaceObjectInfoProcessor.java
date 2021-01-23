package ru.silvmike.interview.resident.average.service.h2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import ru.silvmike.interview.resident.average.service.processor.SpaceObjectInfoProcessor;
import ru.silvmike.interview.resident.average.service.processor.dto.SpaceObjectInfo;

import javax.sql.DataSource;

/**
 * H2-based {@link SpaceObjectInfoProcessor}.
 */
@Slf4j
public class H2SpaceObjectInfoProcessor implements SpaceObjectInfoProcessor {

    private final NamedParameterJdbcTemplate jt;
    private final H2SQLProvider sqlProvider;

    public H2SpaceObjectInfoProcessor(DataSource dataSource, H2SQLProvider sqlProvider) {
        this.jt = new NamedParameterJdbcTemplate(dataSource);
        this.sqlProvider = sqlProvider;
    }

    @Transactional
    @Override
    public void accept(SpaceObjectInfo spaceObjectInfo) {

        MapSqlParameterSource parameterSource =
            new MapSqlParameterSource()
                .addValue("type", spaceObjectInfo.getType())
                .addValue("timestamp", spaceObjectInfo.getTimestamp())
                .addValue("value", spaceObjectInfo.getValue());

        if (update(parameterSource) == 0) {

            try {
                insert(parameterSource);
            } catch (DataIntegrityViolationException e) {
                // someone else inserted this record
                log.debug("Concurrent database usage detected. Retrying update...");
                update(parameterSource);
            }
        }
    }

    private void insert(MapSqlParameterSource parameterSource) {

        jt.update(sqlProvider.getInsert(), parameterSource);
    }

    private int update(MapSqlParameterSource parameterSource) {

        return jt.update(sqlProvider.getIncrement(), parameterSource);
    }
}
