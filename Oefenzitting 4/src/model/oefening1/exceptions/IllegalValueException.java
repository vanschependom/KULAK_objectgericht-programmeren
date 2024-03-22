package model.oefening1.exceptions;

import java.math.BigDecimal;

import ownings.Ownable;

/**
 * A class for signaling illegal values for ownables.
 * 
 * @version  2.0
 * @author   Eric Steegmans
 */
public class IllegalValueException extends Exception {

	/**
	 * Variable referencing the value of this illegal value exception.
	 */
	private final BigDecimal value;

	/**
	 * The Java API strongly recommends to explicitly define a version
	 * number for classes that implement the interface Serializable.
	 * At this stage, that aspect is of no concern to us. 
	 */
	private static final long serialVersionUID = 2003001L;

	/**
	 * Variable referencing the ownable of this illegal value exception.
	 */
	private final Ownable ownable;


	/**
	 * Initialize this new illegal value exception with given
	 * ownable and given value.
	 *
	 * @param   ownable
	 *          The ownable for this new illegal value exception.
	 * @param   value
	 *          The value for this new illegal value exception.
	 * @post    The ownable for this new illegal value exception
	 *          is the same as the given ownable.
	 *          | new.getOwnable() == ownable
	 * @post    The value for this new illegal value exception
	 *          is the same as the given value.
	 *          | new.getValue() == value
	 */
	public IllegalValueException(Ownable ownable, BigDecimal value) {
		this.ownable = ownable;
		this.value = value;
	}

	/**
	 * Return the ownable of this illegal value exception
	 */
	public Ownable getOwnable() {
		return this.ownable;
	}
	/**
	 * Return the value of this illegal value exception
	 */
	public BigDecimal getValue() {
		return this.value;
	}

}