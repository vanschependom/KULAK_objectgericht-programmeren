package kulak_ogp_oz2.model.Defensive;

/**
* A class of exceptions signaling an invalid amount
* of a given time unit.
*/
public class InvalidTimeException extends RuntimeException {
	/**
	 * Variable registering the amount that was invalid.
	 */
	private final int amount;
	
	/**
	 * Variable registering the time unit associated with the
	 * invalid amount.
	 */
	private final String timeUnit;
	
	/**
	* Initialize this new invalid time exception with given
	* amount and given time unit.
	*
	* @param  amount
	*         The amount for this new invalid time exception.
	* @param  timeUnit
	*         The time unit for this new invalid time exception.
	* @post   The value of this new invalid time exception
	*         is equal to the given value.
	*         | new.getAmount() == amount
	* @post   The time unit of this new invalid time exception
	*         is equal to the given time unit.
	*         | new.getTimeUnit() == timeUnit
	* @effect This new invalid time exception is further
	*         initialized as a new runtime exception involving
	*         no diagnostic message and no cause.
	*         | super()
	*/
	public InvalidTimeException(int amount, String timeUnit) {
		this.amount = amount;
		this.timeUnit = timeUnit;
	}

	/**
	* Return the amount of this invalid time exception.
	*/
	public int getAmount() {
		return this.amount;
	}
	
	/**
	* Return the time unit of this invalid time exception.
	*/
	public String getTimeUnit() {
		return this.timeUnit;
	}
}
