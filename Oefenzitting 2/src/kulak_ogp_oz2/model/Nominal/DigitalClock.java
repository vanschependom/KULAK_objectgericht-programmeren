package kulak_ogp_oz2.model.Nominal;

/**
 * A class for dealing with digital clocks, displaying time in terms of
 * hours, minutes and seconds.
 * 
 * @invar  The amount of hours stored by the digital clock must be valid.
 *         | isValidAmtOfHours(getHours())
 * @invar  The amount of minutes stored by the digital clock must be valid.
 *         | isValidAmtOfMinutes(getMinutes())
 * @invar  The amount of seconds stored by the digital clock must be valid.
 *         | isValidAmtOfSeconds(getSeconds())
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
	 * Initialize this new digital clock with given hours, minutes and
	 * seconds.
	 *
	 * @param	hours
	 *			The hours for this new digital clock.
	 * @param	minutes
	 *			The minutes for this new digital clock.
	 * @param	seconds
	 *			The seconds for this new digital clock.
	 * @pre     The amount of hours specified is a valid amount of hours.
	 *          | isValidAmtOfHours(hours)
	 * @pre     The amount of minutes specified is a valid amount of minutes.
	 *          | isValidAmtOfMinutes(minutes)
	 * @pre     The amount of seconds specified is a valid amount of seconds.
	 *          | isValidAmtOfSeconds(seconds)
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
	 */
	public DigitalClock(int hours, int minutes, int seconds) {
		setHours(hours);
		setMinutes(minutes);
		setSeconds(seconds);
	}

	/**
	 * Initialize this new digital clock with given hours and minutes.
	 *
	 * @param  hours
	 *         The hours for this new digital clock.
	 * @param  minutes
	 *         The minutes for this new digital clock.
	 * @pre    The amount of hours specified is a valid amount of hours.
	 *         | isValidAmtOfHours(hours)
	 * @pre    The amount of minutes specified is a valid amount of minutes.
	 *         | isValidAmtOfMinutes(minutes)
	 * @effect This new digital clock is initialized with the given hours
	 *         and the given minutes, and with the lowest possible value
	 *         for the seconds of all digital clocks as its seconds.
	 *         | this(hours,minutes,MIN_SECONDS)
	 */
	public DigitalClock(int hours, int minutes) {
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
	 * @note   We could also use getMinHours() in the specification.
	 *         Upon entry to the constructor, this instance characteristic
	 *         - as for all other instance characteristics - is set
	 *         to the default value of its type. 
	 */
	public DigitalClock() {
		// Java does not allow us to invoke instance methods in the
		// argument list. This seems reasonable, because at that time,
		// the object is not guaranteed to have a proper state.
		this(0, DigitalClock.getMinMinutes());	
	}

	/**
	 * Return the hours displayed by this digital clock.
	 */
	public int getHours() {
		return this.hours;
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
	 * @pre     The amount of hours specified is a valid amount of hours.
	 *          | isValidAmtOfHours(hours)
	 * @post	The hours of this digital clock are set to the given hours.
	 *			| new.getHours() == hours
	 */
	public void setHours(int hours) {
		this.hours = hours;
	}

	/**
			* Advance the time displayed by this digital clock with 1 hour.

	 * @effect The hours currently displayed by this digital clock is incremented
	 *         by 1 modulo the maximum hours for this clock plus one and then incremented
	 *         by the minimum hours for this clock, this is set as the new hours displayed by this digital clock.
	 *         | setHours(((getHours() + 1) % (getMaxHours() + 1)) + getMinHours())
	 */
	public void advanceHours() {
		setHours(((getHours() + 1) % (getMaxHours() + 1)) + getMinHours());
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
	 * Checks if the given amount of hours is valid.
	 * 
	 * @param   amount
	 *          The amount of hours to check.
	 * @return  True if the amount of hours is valid,
	 *          otherwise false.
	 */
	public boolean isValidAmtOfHours(int amount) {
		return getMinHours() <= amount && getMaxHours() >= amount;
	}
	

	/**
	 * Return the minutes displayed by this digital clock.
	 */
	public int getMinutes() {
		return this.minutes;
	}

	/**
	 * Return the smallest possible value for the minutes of all digital
	 * clocks.
	 *
	 * @return The smallest possible value for the minutes of all digital
	 *         clocks is non-negative.
	 *			| result >= 0
	 */
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
	public static int getMaxMinutes() {
		return 59;
	}

	/**
	 * Set the minutes of this digital clock to the given minutes.
	 *
	 * @param	minutes
	 *			The new minutes for this digital clock.
	 * @pre     The amount of minutes specified is a valid amount of minutes.
	 *          | isValidAmtOfMinutes(minutes)
	 * @post	The minutes of this digital clock are set to the given minutes.
	 *			| new.getMinutes() == minutes
	 */
	public void setMinutes(int minutes) {
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
	 * Checks if the given amount of minutes is valid.
	 * 
	 * @param   minutes
	 *          The amount of minutes to check.
	 * @return  True if the amount of minutes is valid,
	 *          otherwise false.
	 */
	public boolean isValidAmtOfMinutes(int minutes) {
		return (minutes >= getMinMinutes()) && (minutes <= getMaxMinutes());
	}


	/**
	 * Return the seconds displayed by this digital clock.
	 */
	public int getSeconds() {
		return this.seconds;
	}


	/**
	 * Set the seconds of this digital clock to the given seconds.
	 *
	 * @param	seconds
	 *			The new seconds for this digital clock.
	 * @pre     The amount of seconds specified is a valid amount of seconds.
	 *          | isValidAmtOfSeconds(seconds)
	 * @post	The seconds of this digital clock are set to the given seconds.
	 *			| new.getSeconds() == seconds
	 */
	public void setSeconds(int seconds) {
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
	 * @effect The lowest possible value for the seconds of all digital
	 *         clocks is set as the new seconds displayed by this
	 *         digital clock.
	 *         | setSeconds(MIN_SECONDS)
	 */
	public void resetSeconds() {
		setSeconds(MIN_SECONDS);
	}
	
	/**
	 * Checks if the given amount of seconds is valid.
	 * 
	 * @param   amount
	 *          The amount of seconds to check.
	 * @return  True if the amount of seconds is valid,
	 *          otherwise false.
	 */
	public boolean isValidAmtOfSeconds(int amount) {
		return MIN_SECONDS <= amount && MAX_SECONDS >= amount;
	}


	/**
	 * Check whether the time displayed by this digital clock is earlier
	 * than the time displayed by the other digital clock.
	 * 
	 * @param	other
	 * 			The clock to compare with.
	 * @pre     The given digital clock is effective.
	 *          | other != null
	 * @pre     Both clocks use the same minimal and maximal values 
	 *          for their hours.
	 *          | this.getMinHours() == other.getMinHours() &&
	 *          |   this.getMaxHours() == other.getMaxHours()
	 * @return  If the hours of this digital clock differ from the hours
	 *          of the other digital clock, true if the hours of this 
	 *          digital clock are less than the hours of the other 
	 *          digital clock; false otherwise.
	 *          | if (this.getHours() != other.getHours())
	 *          |   then result == (this.getHours() < other.getHours())
	 * @return  If the hours of this digital clock are equal to the hours of the other
	 *          digital clock and if the minutes of this digital clock differ
	 *          from the minutes of the other digital clock, true if the minutes
	 *          of this digital clock are less than the minutes of the other
	 *          digital clock; false otherwise.
	 *          | if (this.getHours() == other.getHours() &&
	 *          |      this.getMinutes() != other.getMinutes())
	 *          |   then result == (this.getMinutes() < other.getMinutes())
	 * @return  If the hours and the minutes of this digital clock are equal t
	 *          the hours, respectively the minutes of the other digital clock, 
	 *          true if the seconds of this digital clock are less than the seconds
	 *          of the other digital clock; false otherwise.
	 *          | if (this.getHours() == other.getHours() &&
	 *          |      this.getMinutes() == other.getMinutes())
	 *          |   then result == (this.getSeconds() < other.getSeconds())
	 */
	public boolean displaysEarlierTimeThan(DigitalClock other) {
		// Hours of both clocks are different
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
	 * @pre    The given digital clock is effective.
	 *         | other != null
	 * @pre    Both clocks use the same minimal and maximal values 
	 *         for their hours.
	 *         | this.getMinHours() == other.getMinHours() &&
	 *         |   this.getMaxHours() == other.getMaxHours()
	 * @post   The hours displayed by this digital clock are set to 
	 *         the hours displayed by the given digital clock. 
	 *         | new.getHours() == other.getHours()
	 * @post   The minutes displayed by this digital clock are set to 
	 *         the minutes displayed by the given digital clock. 
	 *         | new.getMinutes() == other.getMinutes()
	 * @post   The seconds displayed by this digital clock are set to 
	 *         the seconds displayed by the given digital clock. 
	 *         | new.getSeconds() == other.getSeconds()
	 */
	public void synchronizeWith(DigitalClock other) {
		if ( (other != null) &&
		     (this.getMinHours() == other.getMinHours()) &&
		     (this.getMaxHours() == other.getMaxHours()) ) {
			setHours(other.getHours());
			setMinutes(other.getMinutes());
			setSeconds(other.getSeconds());
		}
	}

}