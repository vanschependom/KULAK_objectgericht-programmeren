package kulak_ogp_oz2.eigen;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class for dealing with digital clocks, displaying time in terms of
 * hours, minutes and seconds.
 *
 * @author  Vincent Van Schependom
 * @version 3.0
 * @invar   The amount of seconds must be legal.
 *          | isValidSeconds(getSeconds());
 * @invar   The amount of minutes must be legal.
 *          | canHaveAsMinutes(getMinutes());
 * @invar   The amount of hours must be legal.
 *          | canHaveAsHours(getHours());
 */
public class DigitalClock {

    /**
     * Constants reflecting the smallest possible value, respectively the
     * largest possible value for the seconds of a digital clock.
     *   The smallest possible value for the second  of all digital clocks
     *   is set to 0.
     *   The largest possible value for the seconds of all digital clocks
     *   is set to 59.
     */
    public static final int MIN_SECONDS = 0;
    public static final int MAX_SECONDS = 59;


    /**
     * Variable registering the seconds of this digital clock.
     */
    private int seconds = MIN_SECONDS;

    /**
     * Variable registering the minutes of this digital clock.
     */
    private int minutes = getMinMinutes();

    /**
     * Variable registering the hours of this digital clock.
     */
    private int hours = getMinHours();

    /**
     * Variable specifying the mode of the clock (24-hour clock or 12-hour clock)
     */
    private boolean displayIn24Hour = true;

    /**
     * Initialize this new digital clock with given hours, minutes and
     * seconds.
     *
     * @param	hours
     *			The hours for this new digital clock.
     * @param	minutes
     *			The minutes for this new digital clock.
     * @param	seconds
     *			The seconds for this new digital clock.
     * @param   displayIn24Hour
     *          The representation of the clock (either in a 24-hour
     *          representation (i.e. value 'true') or a 12-hour
     *          representation (i.e. value 'false')).
     * @pre     The amount of seconds must be legal.
     *          | isValidSeconds(getSeconds());
     * @pre     The amount of minutes must be legal.
     *          | canHaveAsMinutes(getMinutes());
     * @pre     The amount of hours must be legal.
     *          | canHaveAsHours(getHours());
     * @post	The smallest possible value for the hours of this new
     * 			digital clock is set to some non-negative value.
     *			| new.getMinHours() >= 0
     * @post	The largest possible value for the hours of this new
     * 			digital clock is set to some value exceeding the
     * 			smallest possible value for the hours of this new
     * 			digital clock.
     *			| new.getMaxHours() > new.getMinHours()
     * @effect  The given hours are set as the hours of this
     *          new digital clock.
     *          | setHours(hours)
     * @effect  The given minutes are set as the minutes of this
     *          new digital clock.
     *          | setMinutes(minutes)
     * @effect  The given seconds are set as the seconds of this
     *          new digital clock.
     *          | setSeconds(seconds)
     * @effect	The given mode is set as the mode of this new digital
     *          clock.
     *          | setMode(mode)
     * @throws  IllegalAmountException
     *          The given amount of seconds, minutes or hours is not legal.
     */
    public DigitalClock(int hours, int minutes, int seconds, boolean displayIn24Hour) throws IllegalAmountException {
        if (!isValidSeconds(seconds))
            throw new IllegalAmountException("seconds");
        if (!canHaveAsMinutes(minutes))
            throw new IllegalAmountException("minutes");
        if (!canHaveAsHours(hours))
            throw new IllegalAmountException("hours");
        setHours(hours);
        setMinutes(minutes);
        setSeconds(seconds);
        setDisplayIn24Hour(displayIn24Hour);
    }

    /**
     * Initialize this new digital clock with given hours, minutes and
     * seconds.
     *
     * @param	hours
     *			The hours for this new digital clock.
     * @param	minutes
     *			The minutes for this new digital clock.
     * @param	seconds
     *			The seconds for this new digital clock.
     * @pre     The amount of seconds must be legal.
     *          | isValidSeconds(getSeconds());
     * @pre     The amount of minutes must be legal.
     *          | canHaveAsMinutes(getMinutes());
     * @pre     The amount of hours must be legal.
     *          | canHaveAsHours(getHours());
     * @effect  This new digital clock is initialized with the given hours,
     *          given minutes and the given seconds, and with a 12-hour
     *          representation of the clock (AM/PM notation).
     *          | this(hours,minutes,seconds,Mode.AMPM)
     * @throws  IllegalAmountException
     *          The given amount of seconds, minutes or hours is not legal.
     */
    public DigitalClock(int hours, int minutes, int seconds) throws IllegalAmountException {
        this(hours, minutes, seconds, false);
    }

    /**
     * Initialize this new digital clock with given hours and minutes.
     *
     * @param  hours
     *         The hours for this new digital clock.
     * @param  minutes
     *         The minutes for this new digital clock.
     * @pre    The amount of minutes must be legal.
     *         | canHaveAsMinutes(getMinutes());
     * @pre    The amount of hours must be legal.
     *         | canHaveAsHours(getHours());
     * @effect This new digital clock is initialized with the given hours
     *         and the given minutes, and with the lowest possible value
     *         for the seconds of all digital clocks as its seconds.
     *         | this(hours,minutes,MIN_SECONDS)
     * @throws IllegalAmountException
     *         The given amount of minutes or hours is not legal.
     */
    public DigitalClock(int hours, int minutes) throws IllegalAmountException {
        this(hours, minutes, MIN_SECONDS);
    }

    /**
     * Initialize a new digital clock with hours, minutes and seconds
     * set to their smallest possible values.
     *
     * @effect This new digital clock is initialized with 0 as its
     *         hours, with the lowest possible value for the minutes
     *         of all digital clocks as its minutes.
     *         | this(0,DigitalClock.getMinMinutes())
     * @throws  IllegalAmountException
     *          The given amount of  minutes is not legal.
     */
    public DigitalClock() throws IllegalAmountException {
        this(0, DigitalClock.getMinMinutes());
    }

    /**
     * Return the hours displayed by this digital clock.
     */
    @Basic
    public int getHours() {
        return this.hours;
    }

    /**
     * Checker for the hours
     * @param   hours
     * @return  True if and only if hours is greater than or equal to minimum amount of hours
     *          and if hours is less than or equal to the max amount of hours
     *          | result == (this.getMinHours() <= hours)
     *                 && (hours <= this.getMaxHours())
     */
    public boolean canHaveAsHours(int hours){
        return (this.getMinHours() <= hours)
                && (hours <= this.getMaxHours());
    }

    /**
     * Return the smallest possible value for the hours of this
     * digital clock.
     *
     * @return The smallest possible value for the hours of this
     *         digital clock is a non-negative value.
     *         | result >= 0
     */
    public int getMinHours() {
        return 0;
    }

    /**
     * Return the largest possible value for the hours of this digital
     * clock.
     *
     * @return The largest possible value for the hours of this
     *         digital clock is not below the smallest possible value
     *         for the hours of this digital clock.
     *         | result >= getMinHours()
     */
    public int getMaxHours() {
        return 23;
    }

    /**
     * Set the hours for this digital clock to the given hours.
     *
     * @param	hours
     *			The new hours for this digital clock.
     * @pre     The parameter hours should be legal
     *          | canHaveAsHour(hours)
     * @post	The new hours of this clock are equal to the parameter hours
     *          | new.getHours() = hours
     * @throws  IllegalAmountException
     *          The given amount of hours is not legal.
     */
    public void setHours(int hours) throws IllegalAmountException {
        if (!canHaveAsHours(hours))
            throw new IllegalAmountException("hours");
        this.hours = hours;
    }

    /**
     * Advance the time displayed by this digital clock with 1 hour.
     * @effect The hours currently displayed by this digital clock incremented
     *         by 1 are set as the new hours displayed by this digital
     *         clock, if the incremented amount of hours is legal.
     *         Otherwise, the amount of hours is reset to the minimum.
     *         | if (canHaveAsHours(incremented))
     *         |    then setHours( getHours() + 1 )
     *         | else
     *         |    setHours((incremented % (getMaxHours() + 1)) + getMinHours());
     */
    public void advanceHours() {
        int incremented = getHours() + 1;
        if (canHaveAsHours(incremented))
            setHours(incremented);
        else
            setHours((incremented % (getMaxHours() + 1)) + getMinHours());
    }

    /**
     * Reset the hours of this digital clock to the smallest
     * possible value for the hours of this digital clock.
     *
     * @effect The lowest possible value for the hours of this digital
     *         clock are set as the new hours displayed by this
     *         digital clock.
     *         | setHours(getMinHours())
     */
    public void resetHours() {
        setHours(getMinHours());
    }

    /**
     * Return the minutes displayed by this digital clock.
     */
    @Basic
    public int getMinutes() {
        return this.minutes;
    }

    /**
     * Checker for the minutes
     * @param   minutes
     * @return  | result == (this.getMinMinutes() <= minutes)
     *                 && (minutes <= this.getMaxMinutes())
     */
    public boolean canHaveAsMinutes(int minutes){
        return (getMinMinutes() <= minutes)
                && (minutes <= getMaxMinutes());
    }

    /**
     * Return the smallest possible value for the minutes of all digital
     * clocks.
     *
     * @return The smallest possible value for the minutes of all digital
     *         clocks is non-negative.
     *		   | result >= 0
     */
    @Basic
    public static int getMinMinutes() {
        return 0;
    }

    /**
     * Return the largest possible value for the minutes of all digital
     * clocks.
     *
     * @return The largest possible value for the minutes of all
     *         digital clocks is not below the smallest possible value
     *         for the minutes of all digital clocks.
     *         | result >= getMinMinutes()
     */
    @Basic
    public static int getMaxMinutes() {
        return 59;
    }

    /**
     * Set the minutes of this digital clock to the given minutes.
     *
     * @param	minutes
     *			The new minutes for this digital clock.
     * @pre	    The minutes must be legal
     *          | isValidMinutes(minutes)
     * @post	The new amount of minutes equals the parameter limits.
     *          | new.getMinutes = minutes
     * @throws  IllegalAmountException
     *          The given amount of minutes is not legal.
     */
    public void setMinutes(int minutes) throws IllegalAmountException {
        if (!canHaveAsMinutes(minutes))
            throw new IllegalAmountException("minutes");
        this.minutes = minutes;
    }

    /**
     * Advance the time displayed by this digital clock with 1 minute.
     *
     * @effect  If the minutes of this digital clock have not reached their highest
     *		    possible value, the minutes currently displayed by this digital
     *          clock incremented by 1 are set as the new minutes displayed
     *          by this digital clock.
     *			| if (getMinutes() < getMaxMinutes())
     *			|   then setMinutes(getMinutes()+1)
     * @effect  If the minutes of this digital clock have reached their highest
     *		    possible value, the hours of this digital clock are advanced by
     *		    1 and the minutes of this digital clock are reset to their
     *          lowest possible value.
     *			| if (getMinutes() == getMaxMinutes())
     *			|   then (advanceHours() && resetMinutes())
     */
    public void advanceMinutes() {
        if (getMinutes() < getMaxMinutes()) {
            setMinutes(getMinutes() + 1);
        } else {
            resetMinutes();
            advanceHours();
        }
    }

    /**
     * Reset the minutes of this digital clock to the smallest
     * possible value for the minutes of a digital clock.
     *
     * @effect The lowest possible value for the minutes of all digital
     *         clocks are set as the new minutes displayed by this
     *         digital clock.
     *         | setMinutes(getMinMinutes())
     */
    public void resetMinutes() {
        setMinutes(getMinMinutes());
    }


    /**
     * Return the seconds displayed by this digital clock.
     */
    @Basic
    public int getSeconds() {
        return this.seconds;
    }

    /**
     * Checker for the seconds
     * @param   seconds
     * @return  | result == MIN_SECONDS <= seconds
     *              && seconds <= MAX_SECONDS
     */
    public boolean isValidSeconds(int seconds) {
        return MIN_SECONDS <= seconds
                && seconds <= MAX_SECONDS;
    }

    /**
     * Set the seconds of this digital clock to the given seconds.
     *
     * @param	seconds
     *			The new seconds for this digital clock.
     * @pre     The amount of seconds must be legal
     *          | isValidSeconds(seconds)
     * @post	The seconds of this digital clock are set
     * 			to the given seconds.
     *			| new.getSeconds() == seconds
     * @throws  IllegalAmountException
     *          The given amount of seconds is not legal.
     */
    public void setSeconds(int seconds) throws IllegalAmountException {
        if (!isValidSeconds(seconds))
            throw new IllegalAmountException("seconds");
        this.seconds = seconds;
    }

    /**
     * Advance the time displayed by this digital clock with 1 second.
     *
     * @effect  If the seconds of this digital clock have not reached their highest
     *			possible value, the seconds currently displayed by this digital
     *          clock incremented by 1 are set as the new seconds displayed
     *          by this digital clock.
     *			| if (getSeconds() < MAX_SECONDS)
     *			|   then setSeconds(getSeconds()+1)
     * @effect  If the seconds of this digital clock have reached their highest
     *			possible value, the minutes of this digital clock are advanced by
     *			1 and the seconds of this digital clock are reset to their
     *          lowest possible value.
     *			| if (getSeconds() == MAX_SECONDS)
     *			|   then (advanceMinutes() && resetSeconds())
     */
    public void advanceSeconds() {
        if (getSeconds() < MAX_SECONDS) {
            setSeconds(getSeconds() + 1);
        } else {
            resetSeconds();
            advanceMinutes();
        }
    }

    /**
     * Reset the seconds of this digital clock to the smallest
     * possible value for the seconds of a digital clock.
     *
     * @effect  The lowest possible value for the seconds of all digital
     *          clocks is set as the new seconds displayed by this
     *          digital clock.
     *          | setSeconds(MIN_SECONDS)
     */
    public void resetSeconds() {
        setSeconds(MIN_SECONDS);
    }



    /**
     * Set the mode of representation of the digital clock.
     *
     * @param	displayIn24Hour
     *			The new mode for this digital clock.
     * @post	The mode of this digital clock is set to the given mode.
     *			| new.getMode() == mode
     */
    public void setDisplayIn24Hour(boolean displayIn24Hour) {
        this.displayIn24Hour = displayIn24Hour;
    }

    /**
     * Return the mode this digital clock is running in.
     */
    @Basic
    public boolean getDisplayIn24Hour() {
        return this.displayIn24Hour;
    }

    /**
     * Check if the time displayed on the digital clock is before
     * 12:00:00.
     *
     * @return  True if the time on the clock is before or equal to 12:00:00.
     */
    public boolean isBeforeMidday() {
        return getHours() < 12;
    }

    /**
     * Changes the indicator of the digital clock from AM to PM or PM to AM.
     *
     * @post  Advances the digital clock by 12 hours, resulting in either
     *        the clock changing from AM to PM or from PM to AM.
     */
    public void changeAMPMIndicator() {
        for (int i = 0; i < 12; i++) {
            advanceHours();
        }
    }


    /**
     * Check whether the time displayed by this digital clock is earlier
     * than the time displayed by the other digital clock.
     *
     * @param	other
     * 			The clock to compare with.
     * @pre     The other clock must be effective
     *          | other != null
     * @pre     Both clocks must have equivalent minHours and maxHours
     *          | (this.getMinHours() == other.getMinHours()) &&
     *          |        (this.getMaxHours() == other.getMaxHours())
     * @return  If the hours
     *          of this digital clock differ from the hours of the other digital
     *          clock, true if the hours of this digital clock are less
     *          than the hours of the other digital clock; false otherwise.
     *          | if ( this.getHours() != other.getHours() )
     *          |   then result == (this.getHours() < other.getHours())
     * @return  If the hours
     *          of this digital clock are equal to the hours of the other
     *          digital clock and if the minutes of this digital clock differ
     *          from the minutes of the other digital clock, true if the minutes
     *          of this digital clock are less than the minutes of the other
     *          digital clock; false otherwise.
     *          | if ( (this.getHours() == other.getHours() &&
     *          |      (this.getMinutes() != other.getMinutes() )
     *          |   then result == (this.getMinutes() < other.getMinutes())
     * @return  If the hours and
     *          the minutes of this digital clock are equal to the hours,
     *          respectively the minutes of the other digital clock, true if
     *          the seconds of this digital clock are less than the seconds
     *          of the other digital clock; false otherwise.
     *          | if ( (this.getHours() == other.getHours() &&
     *          |      (this.getMinutes() == other.getMinutes() )
     *          |   then result == (this.getSeconds() < other.getSeconds())
     * @throws  InequivalentClocksException
     *          The given clock is not equivalent to this clock (i.e. min and max hours are not equal).
     *          | (this.getMinHours() != other.getMinHours()) ||
     *          |        (this.getMaxHours() != other.getMaxHours())
     * @throws  IllegalArgumentException
     *          The other clock is not effective
     *          | other == null
     */
    public boolean displaysEarlierTimeThan(DigitalClock other) throws IllegalArgumentException, InequivalentClocksException {
        if (other == null)
            throw new IllegalArgumentException("The other clock must be effective");
        if (this.getMinHours() != other.getMinHours() || this.getMaxHours() != other.getMaxHours())
            throw new InequivalentClocksException();
        // Both clocks use the same time display.
        if (this.getHours() != other.getHours())
            return this.getHours() < other.getHours();
        // Hours of both clocks are the same.
        if (this.getMinutes() != other.getMinutes())
            return this.getMinutes() < other.getMinutes();
        // Hours and minutes of both clocks are the same.
        return this.getSeconds() < other.getSeconds();
    }

    /**
     * Synchronize the time displayed by this digital clock with the
     * time displayed by the given digital clock.
     *
     * @param  other
     *         The digital clock to synchronize with.
     * @pre    The other clock must be effective
     *         | other != null
     * @pre    Both clocks must have equivalent minHours and maxHours
     *         | (this.getMinHours() == other.getMinHours()) &&
     *         |        (this.getMaxHours() == other.getMaxHours())
     * @post   The hours displayed by this digital clock are set to the hours displayed
     *         by the given digital clock.
     *         | new.getHours() == other.getHours()
     * @post   The minutes displayed by this digital clock are set to the minutes
     *         displayed by the given digital clock. Otherwise, the minutes of
     *         this digital clock are left untouched.
     *         | new.getMinutes() == other.getMinutes()
     * @post   The seconds displayed by this digital clock are set to the seconds displayed
     *         by the given digital clock. Otherwise, the seconds of this digital
     *         clock are left untouched.
     *         | new.getSeconds() == other.getSeconds()
     * @post   The mode of representation of this digital clock is set to the mode of representation
     *         of the given digital clock. Otherwise, the mode of representation
     *         of this digital clock is left untouched.
     *         | new.getMode() == other.getMode()
     * @throws  InequivalentClocksException
     *          The given clock is not equivalent to this clock (i.e. min and max hours are not equal).
     *          | (this.getMinHours() != other.getMinHours()) ||
     *          |        (this.getMaxHours() != other.getMaxHours())
     * @throws  IllegalArgumentException
     *          The other clock is not effective
     *          | other == null
     */
    public void synchronizeWith(DigitalClock other) throws IllegalArgumentException, InequivalentClocksException {
        if (other == null)
            throw new IllegalArgumentException("The other clock must be effective");
        if (this.getMinHours() != other.getMinHours() || this.getMaxHours() != other.getMaxHours())
            throw new InequivalentClocksException();
        setHours(other.getHours());
        setMinutes(other.getMinutes());
        setSeconds(other.getSeconds());
        setDisplayIn24Hour(other.getDisplayIn24Hour());
    }

    /**
     * Return the time displayed by this digital clock.
     *
     * @return  If the mode of representation of this digital clock is set
     *          to a 24-hour representation, then the returned string follows
     *          the following format: 'HH:MM:SS'.
     * @return  If the mode of representation of this digital clock is set
     *          to a 12-hour representation, then the returned string follows
     *          the following format: 'HH:MM:SS XX', where XX is either
     *          filled in as AM or PM.
     */
    public String getTimeAsString() {
        if (this.displayIn24Hour) {
            return String.format("%02d:%02d:%02d", this.hours, this.minutes, this.seconds);
        } else {
            int hourToDisplay = this.hours % 12;
            // AM/PM notation does not display hour 0, but uses hour 12 instead
            hourToDisplay = hourToDisplay == 0 ? 12 : hourToDisplay;

            String notation = isBeforeMidday() ? "AM" : "PM";

            return String.format("%02d:%02d:%02d %s",
                    hourToDisplay, this.minutes, this.seconds, notation);
        }
    }

}