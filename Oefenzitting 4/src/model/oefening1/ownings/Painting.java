package model.oefening1.ownings;

import java.math.BigDecimal;

import be.kuleuven.cs.som.annotate.Raw;

import model.oefening1.persons.Person;
import model.oefening1.exceptions.*;

/**
 * A class of paintings as a special kind of ownable. In addition to
 * a value and an owner, paintings have a title and a painter.
 * 
 * @invar   The title of each painting must be a valid title for
 *          a painting.
 *          | isValidTitle(getTitle())
 * @note    For purposes of illustration, all additional properties
 *          ascribed to paintings are worked out in a nominal way.
 */
public class Painting extends Ownable {

	/**
	 * Variable referencing the title of this painting.
	 *
	 * @invar   The referenced title must be a valid title for
	 *          a painting.
	 *          | canHaveAsTitle(title)
	 */
	private String title = "Untitled";

	/**
	 * Variable referencing the painter of this painting.
	 */
	private Person painter = null;

	
	/**
	 * Initialize this new painting with given owner, given value,
	 * given title and given painter.
	 *
	 * @param   owner
	 *          The owner of this new painting.
	 * @param   value
	 *          The value of this new painting.
	 * @param   title
	 *          The title of this new painting.
	 * @param   painter
	 *          The painter of this new painting.
	 * @effect 	This new painting is initialized as a new ownable with
	 *          given owner and given value.
	 *          | super(owner,value)
	 * @effect  The title of this new painting is set to the given title.
	 *          | setTitle(title)
	 * @effect  The painter of this new painting is set to the given painter.
	 *			| setPainter(painter)
	 */
	public Painting(Person owner, BigDecimal value, String title, Person painter)
			throws IllegalOwnerException, IllegalValueException {
		super(owner, value);
		setTitle(title);
		setPainter(painter);
	}


	/**
	 * Initialize a new painting with the given value, title and painter.
	 * The owner of this new painting will be null.
	 * @param value The value of the painting
	 * @param title The title of the painting.
	 * @param painter The painter who painted the painting.
	 * @effect This new painting is initialized with no owner, the given value,
	 *         the given title and the given painter.
	 *         | this(null, value, title, painter)
	 */
	public Painting(BigDecimal value, String title, Person painter) throws IllegalValueException {
		this(title);
		this.setValue(value);
		this.setPainter(painter);
	}

	/**
	 * Initialize this new painting with given title, having no owner,
	 * a value of 0, and no painter.
	 *
	 * @param   title
	 *          The title of this new painting.
	 * @effect  This new painting is initalized with no owner, a value
	 *          of 0, given title, and no painter.
	 *          | this(null,BigDecimal.ZERO,title,null)
	 */
	public Painting(String title) {
		setTitle(title);
	}

	/**
	 * Return the title of this painting.
	 */
	@Raw
	public String getTitle() {
		return this.title;
	}

	/**
	 * Check whether this painting can have the given title
	 * as its title.
	 *
	 * @param   title
	 *          The title to check.
	 * @return  True if the given title is effective and not empty;
	 *          false otherwise.
	 *          | result == (title != null) && (title.length() > 0)
	 */
	public static boolean isValidTitle(String title) {
		try {
			return (title.length() > 0);
		} catch (NullPointerException exc) {
			assert (title == null);
			return false;
		}
	}

	/**
	 * Set the title of this painting to the given title.
	 *
	 * @param   title
	 *          The new title for this painting.
	 * @pre     This painting may not have been terminated.
	 *          | ! isTerminated()
	 * @pre     This painting must can have the given title as
	 *          its title.
	 *          | canHaveAsTitle(title)
	 * @post    The title of this painting is equal to the
	 *          given title.
	 *          | new.getTitle().equals(title)
	 */
	@Raw
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Return the painter of this painting.
	 *   A null reference is returned if the painter of this
	 *   painting is not known.
	 */
	@Raw
	public Person getPainter() {
		return this.painter;
	}

	/**
	 * Set the painter of this painting to the given painter.
	 *
	 * @param   painter
	 *          The new painter for this painting.
	 * @pre     This painting may not have been terminated yet.
	 *          | ! isTerminated()
	 * @post    The painter of this painting is the same as the
	 *          given painter.
	 *          | new.getPainter() == painter
	 */
	@Raw
	public void setPainter(Person painter) {
		this.painter = painter;
	}

}