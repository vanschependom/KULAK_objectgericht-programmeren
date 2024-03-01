package kulak_ogp_oz2.model;

/**
 * A class for dealing with digital clocks, displaying time in terms of
 * hours, minutes and seconds.
 */
public class DigitalClockExtra {

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
	 */
	public DigitalClockExtra(int hours, int minutes, int seconds, boolean displayIn24Hour) {
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
	 * @effect  This new digital clock is initialized with the given hours,
	 *          given minutes and the given seconds, and with a 12-hour
	 *          representation of the clock (AM/PM notation).
	 *          | this(hours,minutes,seconds,Mode.AMPM)
	 */
	public DigitalClockExtra(int hours, int minutes, int seconds) {
		this(hours, minutes, seconds, false);
	}

	/**
	 * Initialize this new digital clock with given hours and minutes.
	 *
	 * @param  hours
	 *         The hours for this new digital clock.
	 * @param  minutes
	 *         The minutes for this new digital clock.
	 * @effect This new digital clock is initialized with the given hours
	 *         and the given minutes, and with the lowest possible value
	 *         for the seconds of all digital clocks as its seconds.
	 *         | this(hours,minutes,MIN_SECONDS)
	 */
	public DigitalClockExtra(int hours, int minutes) {
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
	public DigitalClockExtra() {
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
	 * @note   Because the minimal hours and the maximal hours are offered
	 *         as instance methods, meaning that different clocks can have
	 *         different values for their smallest hour, and because the
	 *         documentation has not revealed the actual value, it is
	 *         possible to change these values without having to notify
	 *         any clients of the class.
	 * @note   Later on, restrictions imposed on basic inspectors such
	 *         as getMinHours() and getMaxHours() will be worked out as
	 *         class invariants.
	 */
	public int getMaxHours() {
		return 23;
	}

	/**
	 * Set the hours for this digital clock to the given hours.
	 *
	 * @param	hours
	 *			The new hours for this digital clock.
	 * @post	If the given hours are in the range of the hours for this
	 * 			digital clock, the hours of this digital clock are set
	 * 			to the given hours.
	 *			| if ( (hours >= getMinHours()) && (hours <= getMaxHours()) )
	 *			|	then new.getHours() == hours
	 * @post	If the given hours exceed the largest possible value for the
	 * 			hours of this digital clock, the hours of this digital clock
	 * 			are set to the given hours, modulo the range of the hours
	 * 			for this digital clock.
	 *			| if (hours > getMaxHours())
	 *			|	then new.getHours() ==
	 *			|			(hours % (getMaxHours()+1)) + getMinHours()
	 * @post	If the given hours are below the smallest possible value for
	 * 			the hours of this digital clock, the hours of this digital
	 * 			clock remain unchanged.
	 * 			| if (hours < getMinHours())
	 * 			|	then new.getHours() = getHours()
	 * @note   This method, as well as others in this class, illustrates the
	 *         principles of total programming. Exceptional cases, i.e.
	 *         exceptional values for the hours of a clock are handled in
	 *         postconditions. Obviously, other options are open in handling
	 *         these exceptional cases. As an example, we could have chosen
	 *         to leave the hours untouched in case the given value exceeded
	 *         the largest possible value for the hours. The important thing
	 *         is that the documentation of the method reveals what happens
	 *         in those exceptional cases.
	 * @note   Later on, we will see that the last postcondition must not be
	 *         stated explicitly, because it is implied by the so-called
	 *         inertia axiom.
	 */
	public void setHours(int hours) {
		if (hours > getMaxHours()) {
			this.hours = (hours % (getMaxHours() + 1)) + getMinHours();
		} else if (hours >= getMinHours()) {
			this.hours = hours;
		}
	}

	/**
	 * Advance the time displayed by this digital clock with 1 hour.
	 *
	 * @effect The hours currently displayed by this digital clock incremented
	 *         by 1 are set as the new hours displayed by this digital
	 *         clock.
	 *         | setHours(getHours()+1)
	 * @note   The effect of mutators can be specified in terms of other
	 *         mutators. In this case, we specify that advancing the hours
	 *         of a digital clock is at all times equivalent with setting
	 *         the hours to their old value incremented by 1. Expanding the
	 *         invocation of the method setHours with its postconditions, yields
	 *         postconditions for the method advanceHours.  
	 */
	public void advanceHours() {
		setHours(getHours() + 1);
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
	 * @note   This method reflects that all digital clocks use the same
	 *         smallest possible value for their minutes. In taking that
	 *         decision, we have chosen not to make our class adaptable
	 *         towards different smallest possible values for the minutes
	 *         of different digital clocks. In that case, the method must
	 *         be changed into an instance method, and all our clients
	 *         must change their code at points where they used the
	 *         method.
	 *         Notice that the documentation does not reveal the effective
	 *         value returned by this inspector. This means that we can
	 *         change the smallest possible value for the minutes of all
	 *         digital clocks into another value, without having to notify
	 *         all the clients of the class. If we would reveal that the
	 *         smallest possible value for the minutes of all digital clocks
	 *         is 0, we had better used a symbolic constant instead of a
	 *         static method. Indeed, in that case, the only thing we can
	 *         still change without having to notify the clients is the
	 *         implementation of the method. As a silly example, we could
	 *         change the body of the method to "return 1-1;". In the end,
	 *         we must always return the value 0, otherwise we would break
	 *         the contract.
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
	 * @post	If the given minutes are in the range of the minutes for all
	 * 			digital clocks, the minutes of this digital clock are set
	 * 			to the given minutes.
	 *			| if ( (minutes >= getMinMinutes()) && (minutes <= getMaxMinutes()) )
	 *			|	then new.getMinutes() == minutes
	 * @post	If the given minutes exceed the largest possible value for the
	 * 			minutes of all digital clocks, or the given minutes are below
	 *          the smallest possible value for the minutes of all digital clocks,
	 *          the minutes of this digital clock remain unchanged.
	 * 			| if ( (minutes > getMaxMinutes() || (minutes < getMinMinutes()) )
	 * 			|	then new.getMinutes() = getMinutes()
	 */
	public void setMinutes(int minutes) {
		if ((minutes >= getMinMinutes()) && (minutes <= getMaxMinutes())) {
			this.minutes = minutes;
		}
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
	public int getSeconds() {
		return this.seconds;
	}


	/**
	 * Set the seconds of this digital clock to the given seconds.
	 *
	 * @param	seconds
	 *			The new seconds for this digital clock.
	 * @post	If the given seconds are in the range of the seconds for a
	 * 			digital clock, the seconds of this digital clock are set
	 * 			to the given seconds.
	 *			| if ( (seconds >= MIN_SECONDS) && (seconds <= MAX_SECONDS) )
	 *			|	then new.getSeconds() == seconds
	 * @post	If the given seconds exceed the largest possible value for the
	 * 			seconds of all digital clocks, the seconds of this digital clock
	 * 			are set to the largest possible value for the seconds
	 *          of all digital clocks.
	 *			| if (seconds > MAX_SECONDS)
	 *			|	then new.getSeconds() == MAX_SECONDS
	 * @post	If the given seconds are below the smallest possible value for
	 * 			the seconds of all digital clocks, the seconds of this digital
	 * 			clock are set to the lowest possible value for the seconds
	 *          of all digital clocks.
	 * 			| if (seconds < MIN_SECONDS)
	 * 			|	then new.getSeconds() == MIN_SECONDS
	 */
	public void setSeconds(int seconds) {
		if (seconds > MAX_SECONDS) {
			this.seconds = MAX_SECONDS;
		} else if (seconds < MIN_SECONDS) {
			this.seconds = MIN_SECONDS;
		} else {
			this.seconds = seconds;
		}
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
		// Changing the indicator is synonymous to advancing the clock
		// 12 hours
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
	 * @return  False if the other clock is not effective.
	 * 			| if (other == null)
	 * 			|	then result == false
	 * @return  False, if the given digital clock is effective and both clocks
	 *          use different minimal or maximal values for their hours.
	 *          | if ( (other != null) &&
	 *          |      ( (this.getMinHours() != other.getMinHours()) ||
	 *          |        (this.getMaxHours() != other.getMaxHours()) )
	 *          |   then result == false
	 * @return  If the other clock is effective, and both clocks use the same
	 *          minimal or maximal values for their hours, and if the hours
	 *          of this digital clock differ from the hours of the other digital
	 *          clock, true if the hours of this digital clock are less
	 *          than the hours of the other digital clock; false otherwise.
	 *          | if ( (other != null) &&
	 *          |      (this.getMinHours() == other.getMinHours()) &&
	 *          |      (this.getMaxHours() == other.getMaxHours()) &&
	 *          |      (this.getHours() != other.getHours() )
	 *          |   then result == (this.getHours() < other.getHours())
	 * @return  If the other clock is effective, and both clocks use the same
	 *          minimal or maximal values for their hours, and if the hours
	 *          of this digital clock are equal to the hours of the other
	 *          digital clock and if the minutes of this digital clock differ
	 *          from the minutes of the other digital clock, true if the minutes
	 *          of this digital clock are less than the minutes of the other
	 *          digital clock; false otherwise.
	 *          | if ( (other != null) &&
	 *          |      (this.getMinHours() == other.getMinHours()) &&
	 *          |      (this.getMaxHours() == other.getMaxHours()) &&
	 *          |      (this.getHours() == other.getHours() &&
	 *          |      (this.getMinutes() != other.getMinutes() )
	 *          |   then result == (this.getMinutes() < other.getMinutes())
	 * @return  If the other clock is effective, and both clocks use the same
	 *          minimal or maximal values for their hours, and if the hours and
	 *          the minutes of this digital clock are equal to the hours,
	 *          respectively the minutes of the other digital clock, true if
	 *          the seconds of this digital clock are less than the seconds
	 *          of the other digital clock; false otherwise.
	 *          | if ( (other != null) &&
	 *          |      (this.getMinHours() == other.getMinHours()) &&
	 *          |      (this.getMaxHours() == other.getMaxHours()) &&
	 *          |      (this.getHours() == other.getHours() &&
	 *          |      (this.getMinutes() == other.getMinutes() )
	 *          |   then result == (this.getSeconds() < other.getSeconds())
	 */
	public boolean displaysEarlierTimeThan(DigitalClock other) {
		if (other == null)
			return false;
		// Both clocks are effective.
		if ( (this.getMinHours() != other.getMinHours()) ||
		     (this.getMaxHours() != other.getMaxHours()) )
			return false;
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
	 * @post   If the given digital clock is effective and both clocks use
	 *         the same minimal and maximal values for their hours, the hours
	 *         displayed by this digital clock are set to the hours displayed
	 *         by the given digital clock. Otherwise, the hours of this digital
	 *         clock are left untouched.
	 *         | if ( (other != null) &&
	 *         |      (this.getMinHours() == other.getMinHours()) &&
	 *         |      (this.getMaxHours() == other.getMaxHours()) )
	 *         |   then new.getHours() == other.getHours()
	 *         |   else new.getHours() == this.getHours()
	 * @post   If the given digital clock is effective and both clocks use
	 *         the same minimal and maximal values for their hours, the
	 *         minutes displayed by this digital clock are set to the minutes
	 *         displayed by the given digital clock. Otherwise, the minutes of
	 *         this digital clock are left untouched.
	 *         | if ( (other != null) &&
	 *         |      (this.getMinHours() == other.getMinHours()) &&
	 *         |      (this.getMaxHours() == other.getMaxHours()) )
	 *         |   then new.getMinutes() == other.getMinutes()
	 *         |   else new.getMinutes() == this.getMinutes()
	 * @post   If the given digital clock is effective and both clocks use
	 *         the same minimal and maximal values for their hours, the seconds
	 *         displayed by this digital clock are set to the seconds displayed
	 *         by the given digital clock. Otherwise, the seconds of this digital
	 *         clock are left untouched.
	 *         | if ( (other != null) &&
	 *         |      (this.getMinHours() == other.getMinHours()) &&
	 *         |      (this.getMaxHours() == other.getMaxHours()) )
	 *         |   then new.getSeconds() == other.getSeconds()
	 *         |   else new.getSeconds() == this.getSeconds()
	 * @post   If the given digital clock is effective and both clocks use
	 *         the same minimal and maximal values for their hours, the mode of
	 *         representation of this digital clock is set to the mode of representation
	 *         of the given digital clock. Otherwise, the mode of representation
	 *         of this digital clock is left untouched.
	 *         | if ( (other != null) &&
	 *         |      (this.getMinHours() == other.getMinHours()) &&
	 *         |      (this.getMaxHours() == other.getMaxHours()) )
	 *         |   then new.getMode() == other.getMode()
	 *         |   else new.getMode() == this.getMode()
	 * @note   For this method, we prefer not to use effect clauses involving
	 *         mutators such as setHours and setMinutes. We feel no tight
	 *         bond between this method and the methods for setting the
	 *         hours, minutes and seconds.
	 */
	public void synchronizeWith(DigitalClockExtra other) {
		if ( (other != null) &&
		     (this.getMinHours() == other.getMinHours()) &&
		     (this.getMaxHours() == other.getMaxHours()) ) {
			setHours(other.getHours());
			setMinutes(other.getMinutes());
			setSeconds(other.getSeconds());
			setDisplayIn24Hour(other.getDisplayIn24Hour());
		}
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