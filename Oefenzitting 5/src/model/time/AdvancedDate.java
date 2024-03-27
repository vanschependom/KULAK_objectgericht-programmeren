package model.time;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Date;


/**
 * An advanced date class that uses the Java Date class to represent timestamps in an Agenda.
 */
public final class AdvancedDate implements ComparableDate<AdvancedDate> {
    // The timestamp itself
    private final Date date;

    /**
     * Initialize an advanced date object with a given ISO compliant date string.
     * Examples of ISO compliant date strings:
     *   - "2022-03-21T12:00:00.000Z"
     *   - "2020-01-01T01:01:01.500Z"
     *   - "2023-09-07T18:30:00.000Z"
     *   - ...
     * @param isoDate  The ISO compliant date string.
     */
    public AdvancedDate(String isoDate) {
        this.date = Date.from(Instant.from(DateTimeFormatter.ISO_INSTANT.parse(isoDate)));
    }

    /**
     * Initialize an advanced date object with a given date.
     * @param date The date this advanced date should contain.
     */
    public AdvancedDate(Date date) {
        this.date = date;
    }


    /**
     * Get the date object of this advanced date.
     */
    public Date getDate() {
        return this.date;
    }

    @Override
    public boolean comesBefore(AdvancedDate date) {
        return this.getDate().before(date.getDate());
    }
}
