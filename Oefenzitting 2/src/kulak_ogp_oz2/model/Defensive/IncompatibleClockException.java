package kulak_ogp_oz2.model.Defensive;

/**
* A class of exceptions signaling an operation between
* two incompatible digital clocks.
*/
public class IncompatibleClockException extends RuntimeException {
	/**
	 * Variable registering the first digital clock.
	 */
	private final DigitalClock clock1;
	
	/**
	 * Variable registering the second digital clock.
	 */
	private final DigitalClock clock2;
	
	/**
	* Initialize this new invalid time exception with given
	* amount and given time unit.
	*
	* @param  clock1
	*         The first digital clock for this new invalid time exception.
	* @param  clock2
	*         The second digital clock for this new invalid time exception.
	* @post   The first digital clock of this new invalid time exception
	*         is equal to the first given clock.
	*         | new.getFirstClock() == clock1
	* @post   The second digital clock of this new invalid time exception
	*         is equal to the second given clock.
	*         | new.getSecondClock() == clock2
	* @effect This new invalid time exception is further
	*         initialized as a new runtime exception involving
	*         no diagnostic message and no cause.
	*         | super()
	*/
	public IncompatibleClockException(DigitalClock clock1, DigitalClock clock2) {
		this.clock1 = clock1;
		this.clock2 = clock2;
	}

	/**
	* Return the first digital clock of this invalid time exception.
	*/
	public DigitalClock getFirstClock() {
		return this.clock1;
	}
	
	/**
	* Return the second digital clock of this invalid time exception.
	*/
	public DigitalClock getSecondClock() {
		return this.clock2;
	}
}
