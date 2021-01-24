package ru.silvmike.interview.resident.average.app;

/**
 * Contains existing app profiles.
 */
public interface Profiles {

    /**
     * Enables debug logging.
     */
    String DEBUG = "DEBUG";

    /**
     * Enables sample file event dispatcher.
     */
    String SAMPLE_FILE = "SAMPLE_FILE";

    /**
     * Enables printing event processor.
     */
    String STD_OUT_PROCESSOR = "STD_OUT_PROCESSOR";

    /**
     * Enables stub average provider.
     */
    String STUB_AVERAGE_PROVIDER = "STUB_AVERAGE_PROVIDER";

    /**
     * Enables H2-based implementation.
     */
    String H2 = "H2";

    /**
     * Enables MongoDB-based implementation.
     */
    String MONGO = "MONGO";

    /**
     * Enables local single-instance replica set.
     */
    String LOCAL_MONGO = "LOCAL_MONGO";

}
