package Chapter5.state;

import be.kuleuven.cs.som.annotate.*;
import java.util.*;

/**
 * A class of persons involving a birth date.
 *
 * @invar   The birth date of each person must be a valid
 *          birth date for a person.
 *        | isValidBirthDate(getBirthDate())
 * 
 * @version 2.0
 * @author  Eric Steegmans
 */
public class Person {

	/**
	 * Initialize this new person with given birth date.
	 *
	 * @param  birthDate
	 *         The birth date for this new person.
	 * @post   The birth date of this new person is equal to
	 *         the given birth date.
	 *       | new.getBirthDate().equals(birthDate)
	 * @throws IllegalArgumentException
	 *         The given birth date is not a valid birth date
	 *         for any person.
	 *        | (! isValidBirthDate(birthDate))
	 */
	@Raw
	public Person(Date birthDate)
			throws IllegalArgumentException {
		if (!isValidBirthDate(birthDate))
			throw new IllegalArgumentException();
		this.birthDate = birthDate;
	}

	/**
	 * Return the birth date of this person.
	 */
	@Basic
	@Immutable
	public Date getBirthDate() {
		return (Date) birthDate.clone();
	}
	
	/**
	 * Check whether this person is adult.
	 * 
	 * @return  True if and only if this person is at least
	 *          18 years old.
	 *          | result ==
	 *          |   getBirthDate().before(new Date(System.currentTimeMillis()-18*365*24*60*60*1000))
	 */
	public boolean isAdult() {
		theCalendar.setTimeInMillis(System.currentTimeMillis());
		theCalendar.add(Calendar.YEAR, -18);
		return getBirthDate().before(theCalendar.getTime());
	}

	/**
	 * Check whether the given date is a valid birth date for a
	 * person.
	 *
	 * @param   date
	 *          The date to check.
	 * @return  True if and only if the given date is effective
	 *          and not in the future.
	 *          | result ==
	 *          |   (date != null) &&
	 *          |   (! date.after(new Date()))
	 */
	public static boolean isValidBirthDate(Date date) {
		theDate.setTime(System.currentTimeMillis());
		return (date != null) && (!date.after(theDate));
	}

	/**
	 * Variable referencing the birth date of this person.
	 */
	private final Date birthDate;
	
	/**
	 * Variable referencing some effective Gregorian calendar to
	 * work with.
	 */
	private static final GregorianCalendar theCalendar = new GregorianCalendar(); 

	/**
	 * Variable referencing some effective date to
	 * work with.
	 */
	private static final Date theDate = new Date(); 

	
}
