package model.time;


/**
 * A basic date class which manually keeps track of the timestamp of the date.
 * The timestamp consists of a specific year, month, day, hour, minute and second.
 */
public record BasicDate(int year, int month, int day, int hour, int minute, int second) implements ComparableDate<BasicDate> {




    // A record is a special type of class in Java where you can specify
    // the fields of the class directly in brackets. The record will then
    // automatically generate a private field for each variable,
    // a matching constructor to initialize the class, and getters for
    // each field (note that the auto-generated getter methods follow
    // a different naming convention)
    @Override
    public boolean comesBefore(BasicDate other) {
        return this.year() < other.year() ||
                (this.year() == other.year() && (this.month() < other.month() ||
                        (this.month() == other.month() && (this.day() < other.day() ||
                                (this.day() == other.day() && (this.hour() < other.hour() ||
                                        (this.hour() == other.hour() && (this.minute() < other.minute() ||
                                                (this.minute() == other.minute() && (this.second() < other.second())
                                                )))))))));
    }


        public boolean comesBeforeAlternative(BasicDate other) {
            // More verbose option
            if (this.year() < other.year()) {
                return true;
            } else if (this.year() > other.year()) {
                return false;
            }

            // In this case the years are equal, check the next field in line
            if (this.month() < other.month()) {
                return true;
            } else if (this.month() > other.month()) {
                return false;
            }

            // Months are also equal, etc.
            if (this.day() < other.day()) {
                return true;
            } else if (this.day() > other.day()) {
                return false;
            }


            if (this.hour() < other.hour()) {
                return true;
            } else if (this.hour() > other.hour()) {
                return false;
            }


            if (this.minute() < other.minute()) {
                return true;
            } else if (this.minute() > other.minute()) {
                return false;
            }


            // Everything but the seconds is equal --> check if the number of seconds in this timestamp
            // is lower than the number of seconds in the other timestamp
            return this.second() < other.second();
        }
    }

