package eigen;

import java.util.*;

/**
 * An abstract class for an event
 *
 * @invar 	The location must always be valid.
 * 			| isValidLocation()
 * @invar	The duration must be positive.
 * 			| duration >= 0
 * @invar	The notes must be valid
 * 			| hasProperNotes(getNotes())
 * @invar	The category must be effective
 * 			| category != null
 *
 * @author 	Vincent Van Schependom
 */
public abstract class Event {

	/**
	 * A variable referencing the location.
	 */
	private String location = null;
	/**
	 * A variable referencing the duration.
	 */
	private int duration = 0; // in minutes
	/**
	 * A variable referencing the notes
	 */
	List<String> notes = new ArrayList<>();
	/**
	 * A variable referencing the category.
	 */
	private EventCategory category = null;

	public Event(EventCategory cat, String location, int duration) {
		setCategory(cat);
		setLocation(location);
		setDuration(duration);
	}

	public String getSummary() {
		String string =
			getCategory() + " event"
			+ " at location " + getLocation()
			+ " with a duration of " + getDuration() + " minutes.";
		return string;
	}

	public abstract void sendNotification();

	public String getLocation() {
		return location;
	}

	private void setLocation(String location) {
		this.location = location;
	}

	public int getDuration() {
		return duration;
	}

	private void setDuration(int duration) {
		this.duration = duration;
	}

	public List<String> getNotes() {
		return notes;
	}

	public EventCategory getCategory() {
		return category;
	}

	private void setCategory(EventCategory category) {
		this.category = category;
	}

}
