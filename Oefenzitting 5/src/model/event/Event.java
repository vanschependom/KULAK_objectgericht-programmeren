package model.event;

import java.util.ArrayList;
import java.util.List;


/**
 * An abstract class that represents events that can be used in an Agenda.
 */
public abstract class Event {
    // The category of this event.
    private final EventCategory eventCategory;

    // The notes stored for this event.
    private final List<String> notes;

    // The location of this event.
    private String location;

    // The duration of this event, expressed in number of minutes.
    private int duration;


    /**
     * Initialize a new event with given category, location and duration.
     * @param category  The category of the event.
     * @param location  The location of the event.
     * @param duration  The duration of the event, expressed in minutes.
     */
    protected Event(EventCategory category, String location, int duration) {
        this.eventCategory = category;
        this.notes = new ArrayList<>();
        this.setLocation(location);
        this.setDuration(duration);
    }

    /**
     * Initialize a new event with given category, location and duration.
     * Additionally, add the given note to the notes of this event.
     * @param category  The category of the event.
     * @param location  The location of the event.
     * @param duration  The duration of the event, expressed in minutes.
     * @param note      The note that should be added to the event.
     */
    protected Event(EventCategory category, String location, int duration, String note) {
        this(category, location, duration);
        this.addNote(note);
    }


    /**
     * Add a note to this event.
     * @param message The note to be added.
     */
    public void addNote(String message) {
        this.notes.add(message);
    }

    /**
     * Get all the notes concerning this event.
     * @return A list of strings representing individual notes.
     */
    public List<String> getNotes() {
        return this.notes;
    }

    /**
     * Get the location of this event.
     */
    public String getLocation() {
        return this.location;
    }

    /**
     * Get the duration of this event (expressed in minutes).
     */
    public int getDuration() {
        return this.duration;
    }

    /**
     * Change the duration of this event.
     * @param timeInMinutes The new duration expressed in number of minutes.
     */
    public void changeDuration(int timeInMinutes) {
        this.setDuration(timeInMinutes);
    }

    /**
     * Set the duration of this event (expressed in minutes).
     */
    private void setDuration(int duration) {
        this.duration = duration;
    }


    /**
     * Change the location of this event.
     * @param location The new location.
     */
    public void changeLocation(String location) {
        this.setLocation(location);
    }

    /**
     * Set the location of this event.
     */
    private void setLocation(String location) {
        this.location = location;
    }

    /**
     * Get a summary of this event.
     * @return A string containing the summary of this event.
     */
    public String getSummary() {
        return String.format("event at location %s, duration %d (in minutes) and category %s",
                this.getLocation(), this.getDuration(), this.getCategory().toString().toLowerCase());
    }

    /**
     * Send a notification to the user for this event.
     */
    public abstract void sendNotification();


    /**
     * Get the category of this event.
     */
    public EventCategory getCategory() {
        return this.eventCategory;
    }
}
