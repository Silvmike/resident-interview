package ru.silvmike.interview.resident.average.service.h2;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 * SQL-query holder.
 */
@Data
public class H2SQLProvider {

    @Value("${sql.increment}")
    private String increment;

    @Value("${sql.summary}")
    private String summary;

    @Value("${sql.insert}")
    private String insert;

    @Value("${sql.clean}")
    private String clean;

}
